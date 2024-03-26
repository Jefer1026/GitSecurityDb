package org.jog.gitsecuritydb.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8045896120002091114L;

    private String jwt;
}
