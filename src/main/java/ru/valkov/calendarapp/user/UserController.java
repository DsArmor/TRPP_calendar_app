package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.valkov.calendarapp.openapi.controller.UsersApi;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController implements UsersApi {
    private final UserService userService;

    @Override
    public ResponseEntity<Object> createUser(UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
