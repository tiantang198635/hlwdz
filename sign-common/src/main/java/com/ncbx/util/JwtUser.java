package com.ncbx.util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ncbx.vo.UserDetails;

public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String password; 
    private final Date lastPasswordResetDate;

    public JwtUser(
            String id,
            String username,
            String password, 
            Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.password = password; 
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
 
    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
