package ru.rutmiit.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.rutmiit.models.exceptions.ChatNotFoundException;
import ru.rutmiit.models.exceptions.RecursionException;
import ru.rutmiit.models.exceptions.UserNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRecursion(RecursionException ex, Model model) {
        log.warn("Создание чата с самим собой: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Невозможно создать чат с самим собой");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "400");
        return "error/custom-error";
    }

    @ExceptionHandler(ChatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleChatNotFound(ChatNotFoundException ex, Model model) {
        log.warn("Чат не найден: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Чат не найден");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/custom-error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        log.warn("Пользователь не найден: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Пользователь не найден");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/custom-error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        log.warn("Некорректные данные: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Некорректные данные");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "400");
        return "error/custom-error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Внутренняя ошибка сервера", ex);
        model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.");
        model.addAttribute("errorCode", "500");
        return "error/custom-error";
    }
}
