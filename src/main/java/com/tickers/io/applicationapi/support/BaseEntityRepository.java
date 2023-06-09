package com.tickers.io.applicationapi.support;

import com.tickers.io.applicationapi.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity, ID> extends MyJpaRepository<T,ID> {
}
