package com.example.reimbursementApi.Repository;

import com.example.reimbursementApi.model.Employee;
import com.example.reimbursementApi.utils.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username);
    Employee findUserByEmail(String email);

    List<Employee> findByRoles(Roles roles);

}
