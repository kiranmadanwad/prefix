package com.ip.subnet.prefix.api.v1;

import com.ip.subnet.prefix.api.v1.dtos.LookupBatchRequest;
import com.ip.subnet.prefix.api.v1.dtos.PrefixSearchResponse;
import com.ip.subnet.prefix.service.PrefixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/prefixes")
@Tag(name = "Prefix")
@AllArgsConstructor
public class PrefixController {

    private final PrefixService prefixService;

    @GetMapping("/{id}")
    @Operation(summary = "Gets a subnet and cloud provider details by ip")
    public PrefixSearchResponse get(@PathVariable String id) {
        return prefixService.search(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Performs a batch search operation for list of ips provided")
    public PrefixSearchResponse lookup(@Validated @RequestBody LookupBatchRequest request) {
        return prefixService.batchLookup(request);
    }

}
