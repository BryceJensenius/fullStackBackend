package com.BryceJensenius.MediaOrganizer.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.BryceJensenius.MediaOrganizer.model.AuthorizationRequest;
import com.BryceJensenius.MediaOrganizer.model.Token;
import com.BryceJensenius.MediaOrganizer.model.User;
import com.BryceJensenius.MediaOrganizer.repository.TokenRepository;
import com.BryceJensenius.MediaOrganizer.repository.UserRepository;

@Service
public class UserService {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String login(AuthorizationRequest authRequest){
        if(authRequest == null || authRequest.getUsername() == null || authRequest.getPassword() == null){
            return null;
        }
        String encPassword = encoder.encode(authRequest.getPassword());
        User user = userRepository.findByUsernameAndEncPassword(authRequest.getUsername(), encPassword);
        if(user == null){
            return null;
        }
        Token userToken = user.getToken();
        if(userToken == null){
            String tokenString = generateToken();
            Instant expiration = Instant.now().plus(Duration.ofDays(1)); // Token valid for 1 day
            userToken = new Token(tokenString, expiration);
            userToken.setUser(user);
            user.setToken(userToken);
            tokenRepository.save(userToken);
            userRepository.save(user);
        }
        if(userToken.getExpirationDate().isBefore(Instant.now())){
            String tokenString = generateToken();
            Instant expiration = Instant.now().plus(Duration.ofDays(1)); // Token valid for 1 day
            userToken.setTokenValue(tokenString);
            userToken.setExpirationDate(expiration);
            tokenRepository.save(userToken);
            userRepository.save(user);
        }
        return userToken.getTokenValue();
    }

    public String signup(AuthorizationRequest authRequest){
        if(authRequest == null || authRequest.getUsername() == null || authRequest.getPassword() == null){
            return null;
        }
        String encPassword = encoder.encode(authRequest.getPassword());
        User existingUser = userRepository.findByUsernameAndEncPassword(authRequest.getUsername(), encPassword);
        if(existingUser != null){
            return null;
        }
        String tokenString = generateToken();
        Instant expiration = Instant.now().plus(Duration.ofDays(1)); // Token valid for 1 day
        Token userToken = new Token(tokenString, expiration);
        User newUser = new User(authRequest.getUsername(), encPassword, userToken);
        userToken.setUser(newUser);
        tokenRepository.save(userToken);
        userRepository.save(newUser);
        return userToken.getTokenValue();
    }

    /*
        Get a user from a valid token header
        Returns null if token is invalid or expired
    */
    public User getUserFromAuthorizationHeader(String authHeader){
        String tokenValue = extractTokenFromHeader(authHeader);
        Token token = tokenRepository.findByTokenValue(tokenValue);
        if(token == null){
            return null;
        }
        if(token.getExpirationDate().isBefore(Instant.now())){
            return null;
        }
        return token.getUser();
    }

    /*
        Extract the token string from the Authorization header
    */
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    /*
        Generate a secure random token
    */
    public static String generateToken() {
        byte[] randomBytes = new byte[32]; // 256-bit token
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
