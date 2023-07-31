package com.pragma.powerup.domain.api.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.TraceabilityDto;

public interface ITraceabilityServicePort {
    void saveTrace(TraceabilityDto trace);
}
