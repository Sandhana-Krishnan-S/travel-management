package com.sandu.travelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue
    private long id;

    private String userName;
    private String email;
    private String password;

}
