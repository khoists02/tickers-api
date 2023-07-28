package com.tickers.io.applicationapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tickers.io.applicationapi.support.events.EventDispatchingEntityListener;
import com.tickers.io.applicationapi.support.tenanting.UserEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@FilterDefs({
        @FilterDef(name = UserScoped.USER_FILTER_ID, parameters = @ParamDef(name = UserScoped.USER_PARAM_ID, type = UUID.class),
                defaultCondition = UserScoped.USER_COLUMN_ID + " = :" + UserScoped.USER_PARAM_ID),
})
@Filters({
        @org.hibernate.annotations.Filter(name = UserScoped.USER_FILTER_ID),
})
@EntityListeners({UserEntityListener.class, EventDispatchingEntityListener.class})
public class UserScoped extends BaseEntity  {
    public static final String USER_FILTER_ID = "userFilter";
    public static final String USER_PARAM_ID = "userId";
    public static final String USER_COLUMN_ID = "user_id";
    @JsonIgnore
    @Column(nullable = false, name = "user_id")
    protected UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserScoped)) return false;
        if (!super.equals(o)) return false;
        UserScoped that = (UserScoped) o;
        if(userId == null && that.userId == null) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }
}
