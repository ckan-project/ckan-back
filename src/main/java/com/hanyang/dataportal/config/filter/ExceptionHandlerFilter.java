package com.hanyang.dataportal.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.dataportal.exception.CustomException.JwtException;
import com.hanyang.dataportal.exception.ErrorApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

import static com.hanyang.dataportal.utill.ApiResponse.errorResponse;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request,response);
        }catch (JwtException e){
            jwtExceptionHandler(response,e.getErrorApiResponse());
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, ErrorApiResponse errorApiResponse) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(errorResponse(errorApiResponse));

        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
    }
}
