package com.recruitease.user_detail_service.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
