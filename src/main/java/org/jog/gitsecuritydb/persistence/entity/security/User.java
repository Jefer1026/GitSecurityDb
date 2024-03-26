package org.jog.gitsecuritydb.persistence.entity.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (role == null) return null;
        if (role.getPermissions() == null) return null;

        List<SimpleGrantedAuthority> authorities = role.getPermissions()
                .stream()
                .map(grantedPermission -> grantedPermission.getOperation().getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("Role_" + this.role.getName()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
