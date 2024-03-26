package org.jog.gitsecuritydb.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 800750706544273674L;

    @Size(min = 4)
    private String name;

    @Size(min = 4)
    private String username;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String repeatPassword;
}
