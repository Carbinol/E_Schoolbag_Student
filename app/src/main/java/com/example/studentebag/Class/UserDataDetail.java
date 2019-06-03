package com.example.studentebag.Class;


import java.io.Serializable;
import java.util.List;

public class UserDataDetail implements Serializable {
    private String name;

    private String email;

    private String role;

    private Student student;

    private Teacher teacher;

    private Boolean enabled;

    private String username;

    private List<Authorities> authorities;

    private Boolean accountNonLocked;

    private Boolean accountNonExpired;

    private Boolean credentialsNonExpired;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Student getStudent() {
        return student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getUsername() {
        return username;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }
}
