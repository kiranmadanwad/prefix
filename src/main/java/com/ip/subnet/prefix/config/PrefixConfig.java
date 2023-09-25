package com.ip.subnet.prefix.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ip.subnet.prefix.api.v1.dtos.CloudProviders;
import com.ip.subnet.prefix.api.v1.dtos.PrefixGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Configuration
public class PrefixConfig {
    @Bean
    public CloudProviders cloudProviders() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON into a Map where keys are provider names and values are lists of PrefixGroup
        Map<String, List<PrefixGroup>> providerMap = objectMapper.readValue(
                getClass().getResourceAsStream("/prefixes.json"),
                new TypeReference<Map<String, List<PrefixGroup>>>() {
                }
        );
        return new CloudProviders(providerMap);
    }
}
