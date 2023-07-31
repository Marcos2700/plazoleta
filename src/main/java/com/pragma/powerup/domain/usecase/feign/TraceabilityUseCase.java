package com.pragma.powerup.domain.usecase.feign;

import com.pragma.powerup.domain.api.feign.ITraceabilityServicePort;
import com.pragma.powerup.domain.spi.feign.ITraceabilityPersistencePort;
import com.pragma.powerup.infrastructure.out.feign.dto.TraceabilityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {
    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    @Override
    public void saveTrace(TraceabilityDto trace) {
        traceabilityPersistencePort.saveTrace(trace);
    }
}
