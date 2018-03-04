package ua.challenge.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.challenge.config.RedisConfig;
import ua.challenge.redis.model.Student;
import ua.challenge.redis.repository.StudentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class StudentRepositoryIntegrationTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void save() {
        Student student = new Student();
        studentRepository.save(student);
    }
}
