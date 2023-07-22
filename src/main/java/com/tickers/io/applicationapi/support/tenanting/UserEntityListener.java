package com.tickers.io.applicationapi.support.tenanting;

import com.tickers.io.applicationapi.model.UserScoped;
import com.tickers.io.applicationapi.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class UserEntityListener {

    @PreUpdate
    @PrePersist
    public void prePersistOrUpdate(Object object) {
        if (object instanceof UserScoped userScoped) {
            Assert.notNull(UserUtils.getAuthenticatedUserId(), "User ID may not be null!");
            userScoped.setUserId(UserUtils.getAuthenticatedUserId());
        }
    }

    @PreRemove
    public void preRemove(Object object)
    {
        if(object instanceof UserScoped userScoped)
        {
            Assert.notNull(UserUtils.getAuthenticatedUserId(), "User ID may not be null!");
            if(!userScoped.getUserId().equals(UserUtils.getAuthenticatedUserId()))
            {
                throw new EntityNotFoundException();
            }
        }
    }
}
