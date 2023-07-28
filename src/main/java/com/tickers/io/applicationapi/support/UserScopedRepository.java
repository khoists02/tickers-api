package com.tickers.io.applicationapi.support;

import com.tickers.io.applicationapi.model.UserScoped;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserScopedRepository<T extends UserScoped, ID> extends MyJpaRepository<T,ID> {
}
