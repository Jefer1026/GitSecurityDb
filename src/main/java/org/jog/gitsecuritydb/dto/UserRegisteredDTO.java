package org.jog.gitsecuritydb.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRegisteredDTO implements Serializable {


    private Long id;

    private String name;

    private String username;

    private String jwt;

    private String role;
}
