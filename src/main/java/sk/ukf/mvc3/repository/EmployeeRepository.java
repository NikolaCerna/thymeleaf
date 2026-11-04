package sk.ukf.mvc3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.ukf.mvc3.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
