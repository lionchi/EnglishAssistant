package org.gavrilov.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class UserDTO {
    private Long id;

    @NotEmpty(message = "Это поле недолжно быть пустым")
    private String login;

    @NotEmpty(message = "Это поле недолжно быть пустым")
    @Length(min = 6, message = "Длина пароля должна быть от 6 символов")
    private String password;

    @NotEmpty(message = "Это поле недолжно быть пустым")
    private String fio;

    private Integer enabled;

    private Long userRoleId;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
}
