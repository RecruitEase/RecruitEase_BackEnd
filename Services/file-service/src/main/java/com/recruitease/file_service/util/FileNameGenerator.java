package com.recruitease.file_service.util;
import java.util.UUID;

public class FileNameGenerator {

    public static String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        return UUID.randomUUID().toString() + fileExtension;
    }
}
