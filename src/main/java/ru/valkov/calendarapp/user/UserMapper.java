package ru.valkov.calendarapp.user;

import org.springframework.stereotype.Component;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

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

    public UserResponse map(User user) {
        return new UserResponse()
                .id(user.getId().intValue())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName());
    }
}
