package ru.rutmiit.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateMessageDto {
    private String text;

    @NotEmpty(message = "Сообщение не должно быть пустым!")
    @Size(max = 4096, message = "Длина сообщения не должна превышать 4096 символов!")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
