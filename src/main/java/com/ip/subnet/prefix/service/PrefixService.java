package com.ip.subnet.prefix.service;

import com.ip.subnet.prefix.api.v1.dtos.*;
import com.ip.subnet.prefix.common.exceptions.EmptyResultException;
import com.ip.subnet.prefix.util.SubnetChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PrefixService {

    private final CloudProviders cloudProviders;

    public PrefixSearchResponse search(String ip) {
        return getPrefixSearchResponse(List.of(ip));
    }

    public PrefixSearchResponse batchLookup(LookupBatchRequest lookupBatchRequest) {
        return getPrefixSearchResponse(lookupBatchRequest.getIps());
    }

    protected PrefixSearchResponse getPrefixSearchResponse(List<String> ip) {
        PrefixSearchResponse prefixSearchResponse = new PrefixSearchResponse();

        List<PrefixResult> result = cloudProviders.getCloudProviders().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .flatMap(group -> ip.parallelStream()
                                .map(x -> getPrefixResultList(x, entry.getKey(), group))
                                .flatMap(List::stream)))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            log.error("No results found for the provided IP(s) - " + ip);
            throw new EmptyResultException("No results found for the provided IP(s).");
        }

        prefixSearchResponse.setResult(result);
        return prefixSearchResponse;
    }

    protected List<PrefixResult> getPrefixResultList(String ip, String providerName, PrefixGroup group) {
        List<PrefixResult> res = group.getPrefixes().parallelStream().filter(x -> SubnetChecker.isInRange(x, ip))
                .map(item -> new PrefixResult(item, providerName, ip, group.getTags())).collect(Collectors.toList());
        return res;
    }
}
