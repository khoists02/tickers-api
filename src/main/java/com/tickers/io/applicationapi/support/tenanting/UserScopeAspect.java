package com.tickers.io.applicationapi.support.tenanting;

import com.tickers.io.applicationapi.model.UserScoped;
import com.tickers.io.applicationapi.utils.UserUtils;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

@Component
@Aspect
public class UserScopeAspect {
    private static final Logger logger = LoggerFactory.getLogger(UserScopeAspect.class);

    private final Repositories repositories;

    @Autowired
    private EntityManager entityManager;

    public UserScopeAspect(ApplicationContext applicationContext) {
        repositories = new Repositories(applicationContext);
    }

    @Around("execution(* com.tickers.io.applicationapi.support.UserScopedRepository+.*(..))")
    public Object aroundFindOfCustomerScopedRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = entityManager.unwrap(Session.class);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        if (signature.getMethod().getAnnotation(NoUserScope.class) != null) {
            if (session.getEnabledFilter(UserScoped.USER_FILTER_ID) != null)
                session.disableFilter(UserScoped.USER_FILTER_ID);
        }  else  if (!signature.getMethod().getName().equals("createEntityGraph")){
//            Assert.isTrue(TransactionSynchronizationManager.isActualTransactionActive(), "A Transaction must be active before attempting a tenanted database request");

            Query queryAnnotation = signature.getMethod().getAnnotation(Query.class);

            if (queryAnnotation != null && queryAnnotation.nativeQuery() && !queryAnnotation.value().toLowerCase().startsWith("insert into")) {
                Assert.isTrue(queryAnnotation.value().contains("user_id = :userId"), "Native queries in a CustomerScoped repository must contain 'user_id = :userId'");
            }

            session
                    .enableFilter(UserScoped.USER_FILTER_ID)
                    .setParameter(UserScoped.USER_PARAM_ID, UserUtils.getAuthenticatedUserId());
            //Enables organisation ACLs
            //We need this lazy loading thread local, otherwise the call to get the Organisation IDs (since it will also call this aspect) will enter a recursive loop
            //We need to leave this filter enabled, so any subsequent lazy loads, will also have this filter applied

        }
        return joinPoint.proceed();
    }
}
