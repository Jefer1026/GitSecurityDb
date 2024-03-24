package org.jog.gitsecuritydb.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class CategoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5037851230733883097L;

    @NotBlank
    private String name;
}
