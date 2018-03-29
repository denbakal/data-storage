package ua.challenge.cache.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import ua.challenge.cache.model.Employee;

import java.util.List;

@RepositoryConfig(cacheName = "EmployeeCache")
public interface EmployeeRepository extends IgniteRepository<Employee, Long> {
    List<Employee> getEmployeeByFirstName();
    Employee getEmployeeById();
}
