package ru.yandex.practicum.filmorate.validateTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.ValidateUser;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateUserTest {

        static User user = User.builder()
                .name("Name1")
                .email("email@yandex.ru")
                .login("login")
                .birthday(LocalDate.of(1985, 05, 13))
                .build();

        @AfterEach
        public void user() {
            user = User.builder()
                    .name("Name1")
                    .email("email@yandex.ru")
                    .login("login")
                    .birthday(LocalDate.of(1985, 05, 13))
                    .build();
        }

    @Test
    public void incorrectEmail() {
        user.setEmail("emailyandex.ru");
        assertFalse(new ValidateUser(user).checkAllData());
    }

    @Test
    public void emptyEmail() {
        user.setEmail("");
        assertFalse(new ValidateUser(user).checkAllData());
    }

    @Test
    public void spaseLogin() {
        user.setLogin("l o g i n");
        assertFalse(new ValidateUser(user).checkAllData());
    }

    @Test
    public void emptyLogin() {
        user.setLogin("");
        assertFalse(new ValidateUser(user).checkAllData());
    }

    @Test
    public void futureBirthday() {
        user.setBirthday(LocalDate.of(2033, 03, 03));
        assertFalse(new ValidateUser(user).checkAllData());
    }

    @Test
    public void correctAllData() {
        assertTrue(new ValidateUser(user).checkAllData());
    }
}
