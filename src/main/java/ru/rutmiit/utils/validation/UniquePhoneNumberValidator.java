package ru.rutmiit.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.rutmiit.repositories.UserRepository;

@Component
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    private final UserRepository userRepository;

    public UniquePhoneNumberValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return userRepository.findByPhoneNumber(value).isEmpty();
    }
}
