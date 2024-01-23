package com.grpcflix.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "\"user\"")
public class User {

    @Id
    private String login;
    private String name;
    private String genre;
}
