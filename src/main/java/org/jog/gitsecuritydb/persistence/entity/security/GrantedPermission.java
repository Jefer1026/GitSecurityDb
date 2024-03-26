package org.jog.gitsecuritydb.persistence.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class GrantedPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
