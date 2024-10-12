package br.com.ewerton.userserviceapi.entity;

import models.enums.ProfileEnum;

import java.util.Set;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}
