package com.crudapplication.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;


@Service
@Slf4j
public class StudentService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, Object>> getExternalApiData() {
        String resourceUrl = "https://dummyjson.com/carts";
        try {
        ResponseEntity<Map> response = restTemplate.getForEntity(
            resourceUrl, 
            Map.class);
            Map<String, List<Map<String, Object>>> externalApiResponse = response.getBody();
            return externalApiResponse.get("carts");
        } catch (RestClientException e) {
            // Handle the exception (e.g., log, return empty list, etc.)
            log.error(resourceUrl, e);
            return new ArrayList<>(); // Return empty list in case of an error
        }
    }
}
