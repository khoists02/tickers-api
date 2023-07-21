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
@Table(name = "registration")
public class Registration extends BaseEntity {
    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    @Column(name = "user_name")
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

    private Boolean activated;

    private Boolean blocked;
}
