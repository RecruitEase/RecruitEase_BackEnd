package com.recruitease.api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitease.api_gateway.util.CodeList;
import com.recruitease.api_gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    @Autowired
    private RouteValidator routeValidator;
//    @Autowired
//    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;


    public AuthenticationFilter() {
        super(Config.class);
    }

    //to send error response
    private Mono<Void> onError(ServerWebExchange exchange,String errCode, String err, HttpStatus httpStatus) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> errorResponse = Map.of(
                "code", errCode,
                "message", "",
                "content", "",
                "errors", err
        );
        byte[] bytes = new ObjectMapper().writeValueAsBytes(errorResponse);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {

            // Get the original URL
            String originalUrl = exchange.getRequest().getURI().toString();

            ServerHttpRequest request = null;

            // check for the  correct headers
            if(routeValidator.isSecured.test(exchange.getRequest())){
                // check for the correct headers
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    try {
                        return onError(exchange, CodeList.RSP_ERROR, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

//                    throw new RuntimeException("Authorization header is missing");
                }

                String authHeaders=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
                System.out.println("raw: "+authHeaders);

                //checking header token validity
                if(authHeaders!=null && authHeaders.startsWith("Bearer ")){
                    System.out.println("Token with bearer: "+authHeaders);
                    //removing empty spaces and checking the token
                    authHeaders=authHeaders.substring(7);
                    System.out.println("Token without bearer: "+authHeaders);

                }

                try{
                    //call to auth service to validate the token
//                    template.getForObject("http://AUTH-SERVICE/auth/validate?token="+authHeaders,String.class);

                    //rather than doing a rest call, using jwt validator inside the gateway itself
                    var res=jwtUtil.validateToken(authHeaders);

                    if(!res){
                        System.out.println("Invalid token");
                        try {
                            return onError(exchange, CodeList.RSP_TOKEN_INVALID, "Invalid Token!", HttpStatus.UNAUTHORIZED);
                        } catch (JsonProcessingException exp) {
                            throw new RuntimeException(exp);
                        }
//                        throw new RuntimeException("Invalid Token");
                    }

                    request= exchange.getRequest()
                            .mutate()
                            .header("loggedInUser", jwtUtil.extractUser(authHeaders))
                            .build();

                }catch (Exception e){
                    System.out.println("Error while validating token");
                    try {
                        return onError(exchange, CodeList.RSP_ERROR, "Unauthorized Access!", HttpStatus.UNAUTHORIZED);
                    } catch (JsonProcessingException exp) {
                        throw new RuntimeException(exp);
                    }
//                    throw new RuntimeException("Unauthorized access");
                }
            }

            System.out.println("Original URL: " + originalUrl);
            return chain.filter(exchange.mutate().request(request).build());

        }));
    }

    public  static class Config{
        // Put configuration properties here
    }
}
