package com.recruitease.recommendation.service;

import com.amazonaws.AmazonClientException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

// Interface for AWS service operations
public interface AwsService {
    


    // Method to download a file from an S3 bucket
    ByteArrayOutputStream downloadFile(
  
        final String keyName
    ) throws IOException, AmazonClientException;


}