package com.example.reimbursementApi.services;

import com.example.reimbursementApi.Repository.EmployeeRepository;
import com.example.reimbursementApi.Repository.ReimbursementRepository;
import com.example.reimbursementApi.model.Employee;
import com.example.reimbursementApi.model.EmployeeAccountResponse;
import com.example.reimbursementApi.model.EmployeeResponse;
import com.example.reimbursementApi.model.Reimbursement;
import com.example.reimbursementApi.utils.EmployeeId;
import com.example.reimbursementApi.utils.NewEmployee;
import com.example.reimbursementApi.utils.Roles;
import org.apache.commons.lang3.RandomStringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository repository;

   @Autowired
    ReimbursementRepository repon;
    public List<Employee>getAllUsers(){
        return repository.findAll();
    }

    /**
     *
     * @param em (employee) will create an employee
     * @return  the created employee
     * @throws Exception when email already exist in the database.
     */

    public Employee saveEmployee(NewEmployee em) throws Exception {
        int emGen = Integer.parseInt(RandomStringUtils.randomNumeric(9));


        Employee e = new Employee();
        Employee checkEmail = repository.findUserByEmail(em.getEmail());
        if(checkEmail!=null)
        {

            logger.info("email already exist");
            throw new Exception();
        }
        else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(em.getPassword());

            e.setEmployeeId(emGen);
            e.setUsername(em.getUsername());
            e.setEmail(em.getEmail());
            e.setFirstName(em.getFirstName());
            e.setLastName(em.getLastName());
            e.setPassword(encodedPassword);
            e.setRoles(Roles.EMPLOYEE);
            repository.save(e);
            logger.info("user was added successfully");
        }

        return e;

    }

    /**
     *
     * @param em (employee) will create an employee
     * @return the created employee
     * @throws Exception when email already exist in the database
     */
    public Employee saveManager(NewEmployee em) throws Exception {
        int emGen = Integer.parseInt(RandomStringUtils.randomNumeric(9));

        Employee e = new Employee();
       Employee checkEmail = repository.findUserByEmail(em.getEmail());
       if(checkEmail!=null)
        {

            logger.info("email already exist");
            throw new Exception();
        }
        else{
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(em.getPassword());

        e.setEmployeeId(emGen);
        e.setUsername(em.getUsername());
        e.setEmail(em.getEmail());
        e.setFirstName(em.getFirstName());
        e.setLastName(em.getLastName());
        e.setPassword(encodedPassword);
        e.setRoles(Roles.MANAGER);
        repository.save(e);
        }
        return e;

    }

    /**
     *
     * @return the list of all users in the Database
     */
    public List<EmployeeResponse> gellAllEmployee(){
        List<Employee> employ = getAllUsers();
        List<EmployeeResponse> details = new ArrayList<>();
        for(Employee e: employ){
            EmployeeResponse res = new EmployeeResponse();
            res.setLastName(e.getLastName());
            res.setEmail(e.getEmail());
            res.setFirstName(e.getFirstName());
            res.setRoles(e.getRoles());
            res.setUsername(e.getUsername());
            res.setEmployeeId(e.getEmployeeId());
            details.add(res);
        }
        return details;
    }
    public Optional<Employee> findByUserid(int id){

        return repository.findById(id);
    }

    /**
     *
     * @param id (employee id) will delete user with the employee id
     * @throws NullPointerException when the id does not exist in the database
     */
    public void deleteEmployeeById(int id) throws NullPointerException{
        Optional<Employee> getId = findByUserid(id);
        if(getId.isPresent()){
            repository.deleteById(id);
        }else{
            logger.info("id number does not exist");
            throw new NullPointerException();
        }


    }

    /**
     *
     * @param id the user id that exist in the database
     * @return all reimbursement request records of the user that exist in the database
     */
    public List<Reimbursement> userReimburse(int id){

        List<Reimbursement> filter = repon.findByEmployee(id);

        return filter;
    }

    /**
     *
     * @param id the user id that exist in the database
     * @return the user page environment that include details/records of all reimbursement transaction history by the user
     */
    public EmployeeAccountResponse employeeAccount(EmployeeId id){
        Optional<Employee> check = findByUserid(id.getEmployeeId());
        EmployeeAccountResponse details = new EmployeeAccountResponse();
        Employee getTheId = check.isPresent()? check.get():null;
        if(getTheId==null){
            logger.info("invalid employee id");
        }else {
            details.setEmail(getTheId.getEmail());
            details.setFirstName(getTheId.getFirstName());
            details.setLastName(getTheId.getLastName());
           details.setReimbursement_records(userReimburse(getTheId.getId()));

        }
        return details;

    }



}
