package com.pragma.powerup.domain.spi.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.TraceabilityDto;

public interface ITraceabilityPersistencePort {
    void saveTrace(TraceabilityDto trace);
}
