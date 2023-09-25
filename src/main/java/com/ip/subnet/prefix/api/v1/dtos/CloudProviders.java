package com.ip.subnet.prefix.api.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudProviders {
    Map<String, List<PrefixGroup>> cloudProviders;
}
