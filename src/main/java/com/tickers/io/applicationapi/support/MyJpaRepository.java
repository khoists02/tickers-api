package com.tickers.io.applicationapi.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface MyJpaRepository<T, ID> extends MyJpaSpecificationExecutor<T>, DynamicEntityGraph<T, ID>, JpaRepository<T, ID> {
}
