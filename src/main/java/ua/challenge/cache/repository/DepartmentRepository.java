package ua.challenge.cache.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;
import ua.challenge.cache.model.Department;

import java.util.List;

@RepositoryConfig(cacheName = "DepartmentCache")
public interface DepartmentRepository extends IgniteRepository<Department, Long> {
    List<Department> getDepartmentsByName();

    @Query("SELECT id FROM Department where id = ?")
    List<Long> getDepartmentsById(Long id, Pageable pageable);
}
