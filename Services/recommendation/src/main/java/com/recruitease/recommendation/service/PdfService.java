package com.recruitease.recommendation.service;

import com.amazonaws.AmazonClientException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.InputStream;

@Service
public class PdfService {

    private final Tika tikaParser = new Tika();

    private final AwsService awsService;

    @Autowired
    public PdfService(AwsService awsService) {
        this.awsService = awsService;
    }

    /**
     * Downloads a PDF from AWS S3 using the given URL and extracts text from it.
     * 
     * @param url The full URL of the PDF file in the S3 bucket.
     * @return The extracted text from the PDF file.
     * @throws IOException If an error occurs in downloading or reading the PDF.
     * @throws TikaException If an error occurs during PDF text extraction.
     * @throws URISyntaxException If the URL is malformed.
     */
    public String downloadAndExtractText(String url) throws IOException, TikaException, URISyntaxException {
        String keyName = extractKeyNameFromUrl(url);
        byte[] pdfBytes = downloadPdfFromS3(keyName);
        return extractTextFromPdf(pdfBytes);
    }

    private String extractKeyNameFromUrl(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String path = uri.getPath(); 
        return path.startsWith("/") ? path.substring(1) : path; 
    }

    private byte[] downloadPdfFromS3(String keyName) throws IOException {
        try (ByteArrayOutputStream outputStream = awsService.downloadFile(keyName)) {
            return outputStream.toByteArray();
        } catch (AmazonClientException e) {
            throw new IOException("Error occurred while downloading the file from S3", e);
        }
    }

    private String extractTextFromPdf(byte[] pdfBytes) throws IOException, TikaException {
        try (InputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            return tikaParser.parseToString(inputStream);
        }
    }
}
