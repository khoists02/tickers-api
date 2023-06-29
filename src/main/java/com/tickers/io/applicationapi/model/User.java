package com.tickers.io.applicationapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Getter
    @Setter
    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Getter
    @Setter
    @NotNull
    @Email
    private String email;

    @Getter
    @Setter
    @NotNull
    private String password;

    private Boolean blocked;
}
