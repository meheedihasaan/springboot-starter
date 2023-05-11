package com.springboot.starter.security;

import com.springboot.starter.entities.Privilege;
import com.springboot.starter.entities.Role;
import com.springboot.starter.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private Long id;

    private String email;

    private String password;

    private String name;

    private Boolean verified;

    private Set<Role> roles = new HashSet<>();

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails() {}

    public CustomUserDetails(Long id, String email, String password, String name, Boolean verified, Set<Role> roles, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.verified = verified;
        this.roles = roles;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(User user) {
        Set<Role> roles = user.getRoles();
        Set<Privilege> privileges = new HashSet<>();
        for(Role role : roles) {
            privileges.addAll(role.getPrivileges());
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for(Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getPrivilegeName()));
        }

        return new CustomUserDetails(
                user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.isVerified(), user.getRoles(), authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
