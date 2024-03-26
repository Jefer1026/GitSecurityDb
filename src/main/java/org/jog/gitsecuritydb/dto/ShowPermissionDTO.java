package org.jog.gitsecuritydb.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ShowPermissionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1233676500361498064L;

    private Long id;

    private String module;

    private String role;

    private String httpMethod;

    private String operation;

}
