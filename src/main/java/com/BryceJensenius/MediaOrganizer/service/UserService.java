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
            System.out.println("Auth request is null or missing fields");
            return null;
        }
        System.out.println("Attempting login for user: " + authRequest.getUsername());
        User user = userRepository.findByUsername(authRequest.getUsername());
        if(user == null){
            System.out.println("No user found with username: " + authRequest.getUsername());
            return null;
        }
        // Verify password using BCrypt matches
        if(!encoder.matches(authRequest.getPassword(), user.getEncPassword())){
            System.out.println("Invalid password for user: " + authRequest.getUsername());
            return null;
        }
        System.out.println("User found: " + user.getUsername());
        Token userToken = user.getToken();
        if(userToken == null){
            System.out.println("No token found for user, generating new token");
            String tokenString = generateToken();
            Instant expiration = Instant.now().plus(Duration.ofDays(1)); // Token valid for 1 day
            userToken = new Token(tokenString, expiration);
            userToken.setUser(user);
            user.setToken(userToken);
            System.out.println("Saving user and token");
            userRepository.save(user);
            tokenRepository.save(userToken);
        }
        if(userToken.getExpirationDate().isBefore(Instant.now())){
            System.out.println("Token expired, generating new token");
            String tokenString = generateToken();
            Instant expiration = Instant.now().plus(Duration.ofDays(1)); // Token valid for 1 day
            userToken.setTokenValue(tokenString);
            userToken.setExpirationDate(expiration);
            userRepository.save(user);
            tokenRepository.save(userToken);
        }
        System.out.println("Login successful, returning token");
        return userToken.getTokenValue();
    }

    public String signup(AuthorizationRequest authRequest){
        if(authRequest == null || authRequest.getUsername() == null || authRequest.getPassword() == null){
            return null;
        }
        // Check if username already exists
        User existingUser = userRepository.findByUsername(authRequest.getUsername());
        if(existingUser != null){
            return null;
        }
        String encPassword = encoder.encode(authRequest.getPassword());
        String tokenString = generateToken();
        Instant expiration = Instant.now().plus(Duration.ofDays(1)); // Token valid for 1 day
        Token userToken = new Token(tokenString, expiration);
        User newUser = new User(authRequest.getUsername(), encPassword, userToken);
        userToken.setUser(newUser);
        userRepository.save(newUser);
        tokenRepository.save(userToken);
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
