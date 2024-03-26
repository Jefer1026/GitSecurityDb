package org.jog.gitsecuritydb.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PermissionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7829775354101733636L;

    private String role;

    private String operation;
}
