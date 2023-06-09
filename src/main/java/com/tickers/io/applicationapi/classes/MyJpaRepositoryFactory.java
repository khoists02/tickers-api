package com.tickers.io.applicationapi.classes;

import com.tickers.io.applicationapi.support.BaseEntityRepository;
import com.tickers.io.applicationapi.support.DynamicEntityGraph;
import com.tickers.io.applicationapi.support.MyJpaSpecificationExecutor;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFragment;

import java.io.Serializable;

public class MyJpaRepositoryFactory extends JpaRepositoryFactory {
    private EntityManager entityManager;

    public MyJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
        RepositoryComposition.RepositoryFragments fragments = super.getRepositoryFragments(metadata);

        if(DynamicEntityGraph.class.isAssignableFrom(metadata.getRepositoryInterface()))
        {
            JpaEntityInformation<?, Serializable> entityInformation = this.getEntityInformation(metadata.getDomainType());
            fragments = fragments.append(RepositoryFragment.implemented(DynamicEntityGraph.class, new DynamicEntityGraphImpl(entityInformation, entityManager)));
        }

        return fragments;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if(MyJpaSpecificationExecutor.class.isAssignableFrom(metadata.getRepositoryInterface()) || BaseEntityRepository.class.isAssignableFrom(metadata.getRepositoryInterface()))
        {
            return MySimpleJpaRepository.class;
        }
        return super.getRepositoryBaseClass(metadata);
    }
}
