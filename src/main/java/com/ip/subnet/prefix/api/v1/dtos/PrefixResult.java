package com.ip.subnet.prefix.api.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PrefixResult {
    private String subnet;
    private String provider;
    private String ip;
    private List<String> tags;
}
