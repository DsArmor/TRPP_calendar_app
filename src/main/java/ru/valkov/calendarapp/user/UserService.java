package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.openapi.model.UserRequest;
import ru.valkov.calendarapp.openapi.model.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Long createUser(UserRequest userRequest) {
        User user = userMapper.map(userRequest);
        userRepository.save(user);
        return user.getId();
    }

    public List<UserResponse> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }
}
