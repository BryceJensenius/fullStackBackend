package com.BryceJensenius.MediaOrganizer.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.BryceJensenius.MediaOrganizer.model.BoardGame;

@Service
public class XMLApiService {
    /*
     * Given a Name of board game, returns the objectId as a String
     * Returns null if no exact match is found
     */
    public String fetchBoardGameIdByName(String name) {
        try {
            String apiUrl = "https://boardgamegeek.com/xmlapi/search?search=" + name;
            String encodedUrl = encodeUrl(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
            conn.setRequestMethod("GET");

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
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
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
            doc.getDocumentElement().normalize();

            NodeList boardgames = doc.getElementsByTagName("boardgame");
            if (boardgames.getLength() > 0) {
                Element boardgame = (Element) boardgames.item(0);
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

                int yearPublished = parseIntSafe(boardgame, "yearpublished");
                int minPlayers = parseIntSafe(boardgame, "minplayers");
                int maxPlayers = parseIntSafe(boardgame, "maxplayers");
                int averagePlaytime = parseIntSafe(boardgame, "playingtime");
                int minAge = parseIntSafe(boardgame, "age");
                String description = getTextSafe(boardgame, "description");
                String imageUrl = getTextSafe(boardgame, "image");
                String thumbnailUrl = getTextSafe(boardgame, "thumbnail");

                return new BoardGame(
                    objectId,
                    gameName,
                    yearPublished,
                    minPlayers,
                    maxPlayers,
                    averagePlaytime,
                    minAge,
                    description,
                    imageUrl,
                    thumbnailUrl
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int parseIntSafe(Element element, String tag) {
        try {
            NodeList nodes = element.getElementsByTagName(tag);
            if (nodes.getLength() > 0 && nodes.item(0) != null) {
                String val = nodes.item(0).getTextContent();
                return Integer.parseInt(val);
            }
        } catch (Exception ignored) {}
        return 0;
    }

    private String getTextSafe(Element element, String tag) {
        NodeList nodes = element.getElementsByTagName(tag);
        if (nodes.getLength() > 0 && nodes.item(0) != null) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private String encodeUrl(String url) {
        // Only encode the search parameter
        int idx = url.indexOf("search=");
        if (idx != -1) {
            String prefix = url.substring(0, idx + 7);
            String searchTerm = url.substring(idx + 7);
            return prefix + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        }
        return url;
    }
}