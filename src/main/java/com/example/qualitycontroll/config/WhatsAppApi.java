package com.example.qualitycontroll.config;

import com.example.qualitycontroll.dal.entity.AwsDocument;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;

@Service
public class WhatsAppApi {

    @Value("${whatsapp.instance.chat-url}")
    private String chatUrl;
    @Value("${whatsapp.instance.token}")
    private String token;

    @Value("${whatsapp.instance.document-url}")
    private String documentUrl;

    WebClient webClient = WebClient.create();

    public ResponseEntity<String> sendMessage(String to, String message) {
        String url = String.format("%s?token=%s&to=%s&body=%s", chatUrl, token, to, message);
        return webClient.post()
                .uri(url)
                .header("content-type", "application/x-www-form-urlencoded")
                .retrieve()
                .toEntity(String.class)
                .block();

    };

    public ResponseEntity<String> sendDocument(String to, AwsDocument document, String caption) {
        String url = String.format("%s?token=%s&to=%s&filename=%s&document=%s&caption=%s",
                documentUrl, token, to, document.fileName(), document.fileUrl(), caption);
        return webClient.post()
                .uri(url)
                .header("content-type", "application/x-www-form-urlencoded")
                .retrieve()
                .toEntity(String.class)
                .block();

    };

}
