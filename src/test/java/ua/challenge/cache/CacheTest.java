package ua.challenge.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.challenge.cache.model.Department;
import ua.challenge.cache.repository.DepartmentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void persist() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");
        departmentRepository.save(1L, department);
    }
}
