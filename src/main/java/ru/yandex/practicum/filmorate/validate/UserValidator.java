package ru.yandex.practicum.filmorate.validate;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {

    private final User user;
    public UserValidator(User user) {
        this.user = user;
    }

    private boolean checkEmail() {
        if (!user.getEmail().isEmpty() && user.getEmail().contains("@")) {
            return true;
        } else {
            log.warn("Ошибка ввода данных. Электронная почта не должна быть пустой и должна содержать символ @");
            return false;
        }
    }

    private boolean checkLogin() {
        if (!user.getLogin().isEmpty() && !user.getLogin().contains(" ")) {
            return true;
        } else {
            log.warn("Ошибка ввода данных. Логин не должен быть пустым и содержать пробелы");
            return false;
        }
    }

    private boolean checkBirthday() {
        if(user.getBirthday().isBefore(LocalDate.now())) {
            return true;
        } else {
            log.warn("Ошибка ввода данных. Дата рождения не должна быть в будущем");
            return false;
        }
    }

    public boolean checkAllData() {
        return (checkEmail() && checkLogin() && checkBirthday());
    }

}
