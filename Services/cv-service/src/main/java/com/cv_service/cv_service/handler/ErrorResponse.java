package com.cv_service.cv_service.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
