package com.recruitease.api_gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/eureka",
            "/user/detail-list",
            "/user/recruiter-list",
            "/user/admin-list",
            "/user/moderator-list",
            "/user/candidate-list",
            "/user/candidate",
            "/user/recruiter",
            "/user/admin",
            "/user/moderator"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}