package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validate.UserValidator;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private HashMap<Long, User> users = new HashMap<>();

    private static long id = 0;

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if(new UserValidator(user).checkAllData()) {
            user.setId(getId());
            users.put(user.getId(), user);
            log.info("Получен запрос к эндпоинту: POST /users {}", user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            log.warn("Запрос к эндпоинту POST /users не обработан.");
            throw new ValidationException("Одно или несколько условий не выполняются");
        }
    }

    @PutMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if(user.getName().isEmpty()) {
            user.setName(user.getEmail());
        }
        if(new UserValidator(user).checkAllData() && user.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /users");
            users.put(user.getId(), user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            log.warn("Запрос к эндпоинту PUT /users не обработан.");
            throw new ValidationException("Одно или несколько условий не выполняются");
        }
    }

    public long getId() {
        this.id++;
        return id;
    }
}
