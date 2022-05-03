package ru.valkov.calendarapp.user;

import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.UserRequest;

@Component
public class UserMapper {
    public User map(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .status(UserStatus.ONLINE)
                .build();
    }
}
