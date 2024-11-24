package com.recruitease.recommendation.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class PdfService {

    private final Tika tikaParser = new Tika();

    /**
     * Downloads a PDF from the given URL and extracts text from it.
     * 
     * @param url The URL of the PDF file.
     * @return The extracted text from the PDF file.
     * @throws IOException If an error occurs in downloading or reading the PDF.
     * @throws TikaException If an error occurs during PDF text extraction.
     */
    public String downloadAndExtractText(String url) throws IOException, TikaException, URISyntaxException {
        byte[] pdfBytes = downloadPdf(url);
        return extractTextFromPdf(pdfBytes);
    }

    private byte[] downloadPdf(String url) throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(url);
        
        return restTemplate.getForObject(uri, byte[].class);
    }

    private String extractTextFromPdf(byte[] pdfBytes) throws IOException, TikaException {
        try (InputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            return tikaParser.parseToString(inputStream);
        }
    }
}
