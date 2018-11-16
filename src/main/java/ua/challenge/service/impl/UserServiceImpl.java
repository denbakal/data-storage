package ua.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ua.challenge.entity.User;
import ua.challenge.repository.UserRepository;
import ua.challenge.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void create() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@gmail.com");

        this.userRepository.save(user);

        this.applicationEventPublisher.publishEvent("commit yes " + user.toString());
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
}
