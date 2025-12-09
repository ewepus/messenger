package ru.rutmiit.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.rutmiit.repositories.ChatRepository;

@Component
public class UniqueChatNameValidator implements ConstraintValidator<UniqueChatName, String> {
    private final ChatRepository chatRepository;

    public UniqueChatNameValidator(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return chatRepository.findChatByName(value).isEmpty();
    }
}
