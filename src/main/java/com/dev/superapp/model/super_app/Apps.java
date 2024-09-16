package com.dev.superapp.model.super_app;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apps")
public class Apps {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "app_name",nullable = false)
    String appName;

    @Column(name = "prog_language")
    String programmingLanguage;

    @Column(name = "framework",nullable = false)
    String framework;
}
