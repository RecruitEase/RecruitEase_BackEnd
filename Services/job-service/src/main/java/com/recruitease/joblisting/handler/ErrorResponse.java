package com.recruitease.joblisting.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
