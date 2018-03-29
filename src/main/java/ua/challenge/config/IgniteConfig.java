package ua.challenge.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.challenge.cache.model.Department;
import ua.challenge.cache.model.Employee;

@Configuration
@EnableIgniteRepositories
public class IgniteConfig {
    @Bean
    public Ignite igniteInstance() {
//        IgniteCluster
        IgniteConfiguration cfg = new IgniteConfiguration();
        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("springDataNode");
        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(false);
        // Defining and creating a new cache to be used by Ignite Spring Data
        // repository.
        CacheConfiguration ccfgEmployee = new CacheConfiguration("EmployeeCache");
        CacheConfiguration ccfgDepartment = new CacheConfiguration("DepartmentCache");
        // Setting SQL schema for the cache.
        ccfgDepartment.setIndexedTypes(Long.class, Department.class);
        ccfgEmployee.setIndexedTypes(Long.class, Employee.class);
        cfg.setCacheConfiguration(new CacheConfiguration[] {
                ccfgEmployee,
                ccfgDepartment
        });
        return Ignition.start(cfg);
    }
}
