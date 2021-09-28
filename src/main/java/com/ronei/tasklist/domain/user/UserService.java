package com.ronei.tasklist.domain.user;

import java.util.Optional;

public interface UserService {

    User createUser(UserForm user);

    Optional<User> findByEmail(String username);

    Optional<User> findById(Long userId);
}
