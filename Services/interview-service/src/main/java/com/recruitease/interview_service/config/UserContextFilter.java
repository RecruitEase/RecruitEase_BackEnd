package com.recruitease.interview_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitease.application_service.config.LoggedUserHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
public class UserContextFilter extends OncePerRequestFilter {

    private final ModelMapper modelMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //example of mapping user object in header to a DTO
        ObjectMapper objectMapper = new ObjectMapper();
        String loggedInUser = request.getHeader("loggedInUser");

        if(loggedInUser!=null){

            LoggedUserHeader user= objectMapper.readValue(loggedInUser, LoggedUserHeader.class);

            String roleAuthority;
            if (user.role().equals("moderator")){
                roleAuthority = "ROLE_MODERATOR";
            }else if(user.role().equals("recruiter")) {
                roleAuthority = "ROLE_RECRUITER";
            }else if(user.role().equals("admin")) {
                roleAuthority = "ROLE_ADMIN";
            } else{
                roleAuthority = "ROLE_CANDIDATE";
            }

            // Convert role to a collection of GrantedAuthority
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roleAuthority);


            CustomUserDetails userDetails=new CustomUserDetails(
                    user.id(),
                    user.email(),
                    user.role(),
                    user.isActive(),
                    user.createdAt(),
                    user.roleDetails(),
                    authorities,
                    modelMapper
            );

            // Create an authentication token
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


            // Set the authentication in the context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}
