package com.tickers.io.applicationapi.support;

import java.util.UUID;

public interface TenantContext {
    UUID getTenantId();
    void setTenantId(UUID tenantId);
    void clear();
}
