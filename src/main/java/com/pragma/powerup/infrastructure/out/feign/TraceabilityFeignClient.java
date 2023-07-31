package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.configuration.BeanConfiguration;
import com.pragma.powerup.infrastructure.out.feign.dto.TraceabilityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "traceability-microservice", url = "http://localhost:8084", configuration = BeanConfiguration.class)
public interface TraceabilityFeignClient {

    @PostMapping("/trace/save")
    void saveTraceability(@RequestBody TraceabilityDto traceabilityDto);
}
