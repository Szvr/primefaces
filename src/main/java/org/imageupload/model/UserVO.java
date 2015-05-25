package org.imageupload.model;

import java.util.Date;

/**
 * @author Georgia Papp
 */
public class UserVO {

    private String username;

    private String password;

    private Date lastLogin;

    private String role;

    private int id;

    public UserVO(String username, String password, Date lastLogin, String role) {
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
        this.role = role;
    }

    public UserVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
