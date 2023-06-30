package com.tickers.io.applicationapi.support;


import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface TenantContext {
    UUID getTenantId();
    void setTenantId(UUID tenantId);
    void clear();
}
