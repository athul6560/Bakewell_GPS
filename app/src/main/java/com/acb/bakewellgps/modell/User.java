package com.acb.bakewellgps.modell;

public class User {
    public int id;
    public String username;
    public String userpassword;
    public int employee_id;
    public boolean is_active;
    public boolean access_to_backoffice;
    public String device_token;
    public String device_version;
    public String version_code;
    public boolean is_blocked;
    public boolean is_login;
    public int pos_pin;
    public int login_count;
    public String login_time;
    public String employee_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isAccess_to_backoffice() {
        return access_to_backoffice;
    }

    public void setAccess_to_backoffice(boolean access_to_backoffice) {
        this.access_to_backoffice = access_to_backoffice;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDevice_version() {
        return device_version;
    }

    public void setDevice_version(String device_version) {
        this.device_version = device_version;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean isIs_login() {
        return is_login;
    }

    public void setIs_login(boolean is_login) {
        this.is_login = is_login;
    }

    public int getPos_pin() {
        return pos_pin;
    }

    public void setPos_pin(int pos_pin) {
        this.pos_pin = pos_pin;
    }

    public int getLogin_count() {
        return login_count;
    }

    public void setLogin_count(int login_count) {
        this.login_count = login_count;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }
}
