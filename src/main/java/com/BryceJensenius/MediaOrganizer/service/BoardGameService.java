package com.BryceJensenius.MediaOrganizer.service;

import com.BryceJensenius.MediaOrganizer.model.BoardGame;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.w3c.dom.*;

@Service
public class BoardGameService {
    public BoardGame getBoardGameByName(String name) {
        try {
            String apiUrl = "https://boardgamegeek.com/xmlapi/search?search=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(conn.getInputStream());
            doc.getDocumentElement().normalize();

            NodeList boardgames = doc.getElementsByTagName("boardgame");
            for (int i = 0; i < boardgames.getLength(); i++) {
                Element boardgame = (Element) boardgames.item(i);
                String gameName = boardgame.getElementsByTagName("name").item(0).getTextContent();
                String yearStr = boardgame.getElementsByTagName("yearpublished").item(0) != null
                        ? boardgame.getElementsByTagName("yearpublished").item(0).getTextContent()
                        : "0";
                int yearPublished = 0;
                try {
                    yearPublished = Integer.parseInt(yearStr);
                } catch (NumberFormatException ignored) {}

                // Return the first match
                return new BoardGame(gameName, yearPublished);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}