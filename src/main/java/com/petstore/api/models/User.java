package com.petstore.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    int userStatus;
}
