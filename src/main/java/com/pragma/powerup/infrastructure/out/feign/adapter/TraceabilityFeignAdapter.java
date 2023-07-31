package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.feign.ITraceabilityPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.TraceabilityFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.TraceabilityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements ITraceabilityPersistencePort {
    private final TraceabilityFeignClient traceabilityFeignClient;
    @Override
    public void saveTrace(TraceabilityDto trace) {
        traceabilityFeignClient.saveTraceability(trace);
    }
}
