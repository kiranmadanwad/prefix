package com.ip.subnet.prefix.integration.api.v1;

import com.ip.subnet.prefix.api.v1.dtos.LookupBatchRequest;
import com.ip.subnet.prefix.api.v1.dtos.PrefixSearchResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrefixControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetPrefixById() {
        String url = "http://localhost:" + port + "/v1/prefixes/{id}";
        ResponseEntity<PrefixSearchResponse> responseEntity =
                restTemplate.getForEntity(url, PrefixSearchResponse.class, "23.79.237.1");

        Assertions.assertEquals(1, responseEntity.getBody().getResult().size());
        Assertions.assertEquals("Akamai", responseEntity.getBody().getResult().get(0).getProvider());
        Assertions.assertEquals("23.79.237.0/24", responseEntity.getBody().getResult().get(0).getSubnet());
        Assertions.assertEquals(List.of("Cloud", "CDN/WAF"), responseEntity.getBody().getResult().get(0).getTags());
    }

    @Test
    public void testGetPrefixByIdShouldFail() {
        String url = "http://localhost:" + port + "/v1/prefixes/{id}";
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class, "23.79.237.0");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
    }

    @Test
    public void testBatchLookup() {
        String url = "http://localhost:" + port + "/v1/prefixes";
        LookupBatchRequest requestBody = new LookupBatchRequest();
        requestBody.setIps(List.of("23.79.237.1", "23.79.237.2"));

        ResponseEntity<PrefixSearchResponse> responseEntity =
                restTemplate.postForEntity(url, requestBody, PrefixSearchResponse.class);

        Assertions.assertEquals(2, responseEntity.getBody().getResult().size());
        Assertions.assertEquals("Akamai", responseEntity.getBody().getResult().get(0).getProvider());
        Assertions.assertEquals("23.79.237.0/24", responseEntity.getBody().getResult().get(0).getSubnet());
        Assertions.assertEquals(List.of("Cloud", "CDN/WAF"), responseEntity.getBody().getResult().get(0).getTags());
    }
}
