package ru.rutmiit.dto;

import ru.rutmiit.utils.validation.UniqueChatName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateChatDto {

    @UniqueChatName
    private String name;

    @NotEmpty(message = "Название чата не должно быть пустым!")
    @Size(max = 32, message = "Название чата не должно превышать 32 символа!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
