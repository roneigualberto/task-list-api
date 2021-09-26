package com.ronei.tasklist.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final HashGenerator hashGenerator;

    @Override
    public User createUser(UserForm userForm) {

        User user = User.builder().email(userForm.getEmail())
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .password(hashGenerator.hash(userForm.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }
}
