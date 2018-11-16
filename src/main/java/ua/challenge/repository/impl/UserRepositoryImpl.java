package ua.challenge.repository.impl;

import org.springframework.stereotype.Repository;
import ua.challenge.entity.User;
import ua.challenge.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> implements UserRepository {
}
