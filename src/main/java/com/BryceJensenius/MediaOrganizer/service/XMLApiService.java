package com.BryceJensenius.MediaOrganizer.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.BryceJensenius.MediaOrganizer.model.BoardGame;

@Service
public class XMLApiService {

    private final String bggBearerToken;

    public XMLApiService(@Value("${BGG_BEARER_TOKEN}") String token) { // Load token from Environment variable
        bggBearerToken = token;
    }
    /*
     * Given a Name of board game, returns the objectId as a String
     * Returns null if no exact match is found
     */
    public String fetchBoardGameIdByName(String name) {
        try {
            // Encode the query parameter safely
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            String apiUrl = "https://boardgamegeek.com/xmlapi/search?search=" + encodedName;

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .header("Authorization", "Bearer " + bggBearerToken)
                    .header("Accept", "application/xml")
                    .build();

            // Send request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch board game: HTTP " + response.statusCode());
            }

            // Parse XML directly from InputStream
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(response.body());

            doc.getDocumentElement().normalize();

            NodeList boardgames = doc.getElementsByTagName("boardgame");
            for (int i = 0; i < boardgames.getLength(); i++) {
                Element boardgame = (Element) boardgames.item(i);
                String gameName = boardgame.getElementsByTagName("name").item(0).getTextContent();
                if (gameName.equalsIgnoreCase(name)) {
                    return boardgame.getAttribute("objectid");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BoardGame fetchBoardGameDetailsById(String objectId) {
        try {
            String apiUrl = "https://boardgamegeek.com/xmlapi/boardgame/" + objectId;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .header("Authorization", "Bearer " + bggBearerToken)
                    .header("Accept", "application/xml")
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<java.io.InputStream> response =
                    client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch board game: HTTP " + response.statusCode());
            }

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(response.body());

            doc.getDocumentElement().normalize();

            NodeList boardgames = doc.getElementsByTagName("boardgame");
            if (boardgames.getLength() > 0) {
                Element boardgame = (Element) boardgames.item(0);

                // Get primary name or fallback to first name
                String gameName = "";
                NodeList nameNodes = boardgame.getElementsByTagName("name");
                for (int i = 0; i < nameNodes.getLength(); i++) {
                    Element nameElem = (Element) nameNodes.item(i);
                    if ("true".equalsIgnoreCase(nameElem.getAttribute("primary"))) {
                        gameName = nameElem.getTextContent();
                        break;
                    }
                }
                if (gameName.isEmpty() && nameNodes.getLength() > 0) {
                    gameName = nameNodes.item(0).getTextContent();
                }

                return new BoardGame(
                        objectId,
                        gameName,
                        parseIntSafe(boardgame, "yearpublished"),
                        parseIntSafe(boardgame, "minplayers"),
                        parseIntSafe(boardgame, "maxplayers"),
                        parseIntSafe(boardgame, "playingtime"),
                        parseIntSafe(boardgame, "age"),
                        getTextSafe(boardgame, "description"),
                        getTextSafe(boardgame, "image"),
                        getTextSafe(boardgame, "thumbnail")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private int parseIntSafe(Element elem, String tagName) {
        try {
            String text = getTextSafe(elem, tagName);
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getTextSafe(Element elem, String tagName) {
        NodeList nodes = elem.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }
}