package com.dev.superapp.model.local;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "first_name",nullable = false)
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "address",nullable = false)
    String address;

    @Column(name = "city",nullable = false)
    String city;

    @Column(name = "email")
    String email;
}
