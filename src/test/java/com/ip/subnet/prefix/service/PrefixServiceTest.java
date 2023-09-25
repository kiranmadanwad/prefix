package com.ip.subnet.prefix.service;

import com.ip.subnet.prefix.api.v1.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PrefixServiceTest {

    @Mock
    private CloudProviders cloudProviders;

    @InjectMocks
    private PrefixService prefixService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void search() {
        String ip = "192.168.1.1";
        PrefixSearchResponse expectedResponse =
                new PrefixSearchResponse(List.of(
                        new PrefixResult("192.168.1.0/24", "Provider1", ip, List.of("Tag1", "Tag2"))));
        when(cloudProviders.getCloudProviders()).thenReturn(getSampleCloudProviders());

        PrefixSearchResponse result = prefixService.search(ip);
        verify(cloudProviders, times(1)).getCloudProviders();
        assertEquals(expectedResponse, result);
    }

    @Test
    void batchLookup() {
        LookupBatchRequest lookupBatchRequest = new LookupBatchRequest();
        lookupBatchRequest.setIps(Arrays.asList("192.168.1.1", "10.0.0.1"));

        PrefixResult prefixResult =
                new PrefixResult("192.168.1.0/24", "Provider1", "192.168.1.1", List.of("Tag1", "Tag2"));
        PrefixResult prefixResult1 =
                new PrefixResult("10.0.0.0/8", "Provider1", "10.0.0.1", List.of("Tag1", "Tag2"));
        PrefixSearchResponse expectedResponse = new PrefixSearchResponse(List.of(prefixResult, prefixResult1));

        when(cloudProviders.getCloudProviders()).thenReturn(getSampleCloudProviders());

        PrefixSearchResponse result = prefixService.batchLookup(lookupBatchRequest);

        verify(cloudProviders, times(1)).getCloudProviders();
        assertEquals(expectedResponse, result);
    }

    @Test
    void getPrefixSearchResponse() {
        List<String> ips = Arrays.asList("192.168.1.1", "10.0.0.1");
        when(cloudProviders.getCloudProviders()).thenReturn(getSampleCloudProviders());

        PrefixSearchResponse result = prefixService.getPrefixSearchResponse(ips);

        verify(cloudProviders, times(1)).getCloudProviders();
        assertEquals(2, result.getResult().size());
    }

    @Test
    void getPrefixResultList() {
        String ip = "192.168.1.1";
        String providerName = "Provider1";
        PrefixGroup group = new PrefixGroup();
        group.setPrefixes(Arrays.asList("192.168.1.0/24", "10.0.0.0/8"));
        group.setTags(Arrays.asList("Tag1", "Tag2"));

        List<PrefixResult> result = prefixService.getPrefixResultList(ip, providerName, group);

        assertEquals(1, result.size());
    }

    private Map<String, List<PrefixGroup>> getSampleCloudProviders() {
        Map<String, List<PrefixGroup>> cloudProvidersMap = new HashMap<>();
        PrefixGroup group = new PrefixGroup();
        group.setPrefixes(Arrays.asList("192.168.1.0/24", "10.0.0.0/8"));
        group.setTags(Arrays.asList("Tag1", "Tag2"));
        cloudProvidersMap.put("Provider1", Arrays.asList(group));
        return cloudProvidersMap;
    }
}
