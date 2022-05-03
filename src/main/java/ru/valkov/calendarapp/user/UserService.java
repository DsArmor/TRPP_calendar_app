package ru.valkov.calendarapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.valkov.calendarapp.openapi.model.UserRequest;

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
}
