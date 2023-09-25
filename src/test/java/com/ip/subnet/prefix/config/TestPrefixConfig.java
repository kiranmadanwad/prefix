package com.ip.subnet.prefix.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ip.subnet.prefix.api.v1.dtos.CloudProviders;
import com.ip.subnet.prefix.api.v1.dtos.PrefixGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@TestConfiguration
public class TestPrefixConfig {

    @Value("classpath:prefixes.json") // Path to the JSON file in test resources
    private Resource prefixesJsonResource;

    @Bean
    public CloudProviders cloudProviders(ObjectMapper objectMapper) throws IOException {
        // Parse the JSON file into a Map where keys are provider names and values are lists of PrefixGroup
        Map<String, List<PrefixGroup>> providerMap = objectMapper.readValue(
                prefixesJsonResource.getInputStream(),
                new TypeReference<Map<String, List<PrefixGroup>>>() {}
        );
        return new CloudProviders(providerMap);
    }
}
