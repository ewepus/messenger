package ru.rutmiit.dto;

import jakarta.validation.constraints.*;
import ru.rutmiit.utils.validation.UniqueEmail;
import ru.rutmiit.utils.validation.UniquePhoneNumber;
import ru.rutmiit.utils.validation.UniqueUsername;

public class UserRegistrationDto {

    @UniqueUsername
    private String username;

    private String password;

    private String confirmPassword;

    @UniquePhoneNumber
    private String phoneNumber;

    @UniqueEmail
    private String email;

    public UserRegistrationDto() {
    }

    @NotEmpty(message = "Имя пользователя не должно быть пустым!")
    @Size(min = 4, max = 32, message = "Имя пользователя должно быть от 4 до 32 символов!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Email не должен быть пустым!")
    @Email(message = "Введите корректный email!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Номер телефона не должен быть пустым!")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotEmpty(message = "Пароль не должен быть пустым!")
    @Size(min = 8, max = 32, message = "Пароль должен быть от 8 до 32 символов!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Подтверждение пароля не должно быть пустым!")
    @Size(min = 8, max = 32, message = "Подтверждение пароля должно быть от 8 до 32 символов!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
