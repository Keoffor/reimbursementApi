package com.example.reimbursementApi.controller;
import com.example.reimbursementApi.model.EmployeeAccountResponse;
import com.example.reimbursementApi.model.EmployeeResponse;
import com.example.reimbursementApi.model.Reimbursement;
import com.example.reimbursementApi.services.EmployeeService;
import com.example.reimbursementApi.utils.EmployeeId;
import com.example.reimbursementApi.utils.NewEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reimbursement")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping(value = "/allEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll(){
        List<EmployeeResponse> er = employeeService.gellAllEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(er);
    }

    @PostMapping(value = "/addEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEmployee(@RequestBody NewEmployee em) {
        try {
            employeeService.saveEmployee(em);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully created");

        } catch (Exception e) {

            e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email already exist");
        }


    }
    @PostMapping(value = "/addManager", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addManager(@RequestBody NewEmployee em) {
        try {
            employeeService.saveManager(em);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully created");

        } catch (Exception e) {

            e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("email already exist");
        }

    }

    @GetMapping (value = "/employeeAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity userAccount(@RequestBody EmployeeId em) {
           EmployeeAccountResponse response = employeeService.employeeAccount(em);
            return ResponseEntity.status(HttpStatus.OK).body(response);


        }
    @GetMapping(value = "/users")
    public ResponseEntity user(){

            return ResponseEntity.status(HttpStatus.OK).body(employeeService.userReimburse(30));
        }

        @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable Integer id){
        try {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Record with id number " + id + " deleted");
        }catch (NullPointerException e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id number does not exist ");
        }
    }

}
