package com.autofx.autofxbackend.iam.infrastructure.authorization.sfs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.autofx.autofxbackend.iam.domain.model.aggregates.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private final String username;
    @JsonIgnore
    private final String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enable;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enable = true;
    }

    public static UserDetailsImpl build(User user) {
       var authorities = user.getRoles().stream()
               .map(role -> role.getName().name())
               .map(SimpleGrantedAuthority::new)
               .collect(Collectors.toList());
       return new UserDetailsImpl(
               user.getEmail().address(),
               user.getPassword(),
               authorities
       );
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
