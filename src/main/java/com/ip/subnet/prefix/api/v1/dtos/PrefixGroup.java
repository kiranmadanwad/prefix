package com.ip.subnet.prefix.api.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrefixGroup {
    private List<String> prefixes;
    private List<String> tags;
}
