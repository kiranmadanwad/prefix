package com.ip.subnet.prefix.api.v1.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LookupBatchRequest {
    @NotNull
    @NotEmpty
    List<String> ips;
}
