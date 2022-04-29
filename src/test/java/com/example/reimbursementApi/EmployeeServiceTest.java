package com.example.reimbursementApi;

import com.example.reimbursementApi.Repository.EmployeeRepository;
import com.example.reimbursementApi.Repository.ReimbursementRepository;
import com.example.reimbursementApi.model.Employee;
import com.example.reimbursementApi.model.EmployeeAccountResponse;
import com.example.reimbursementApi.model.EmployeeResponse;
import com.example.reimbursementApi.model.Reimbursement;
import com.example.reimbursementApi.services.EmployeeService;
import com.example.reimbursementApi.utils.EmployeeId;
import com.example.reimbursementApi.utils.NewEmployee;
import com.example.reimbursementApi.utils.Roles;
import com.example.reimbursementApi.utils.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;


@Profile("test")
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {


    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    ReimbursementRepository repo;

    @InjectMocks
    private EmployeeService employeeService;
    Employee response = new Employee("username","xdjdjd","jsn@yahoo.com","mark",
            "long",Roles.EMPLOYEE);
    NewEmployee newEmployee = new NewEmployee("knwow","dkdk","dmdm@yahoo.com",
            "friday","long",Roles.MANAGER);
    Reimbursement re = new Reimbursement("ken@yahoo.com","cash",599,"travel " +
            "expenses", Status.UNDER_REVEIW);

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllEmployeesTest(){
        List<Employee> records = new  ArrayList<>(Arrays.asList(response));
        Mockito.when(employeeRepository.findAll()).thenReturn(records);
        List<EmployeeResponse> app = employeeService.gellAllEmployee();
        Assert.assertEquals(app.get(0).getUsername(),"username");
        Assert.assertEquals(app.get(0).getRoles(),Roles.EMPLOYEE);
    }

    @Test (expected = Exception.class)

    public void saveEmployeeShouldReturnError() throws  Exception{

        response.setEmployeeId(111);
        Mockito.when(employeeRepository.findUserByEmail(any())).thenReturn(response);
        employeeService.saveEmployee(newEmployee);
    }


    @Test
    public void saveEmployeeTest() throws Exception {
        response.setEmployeeId(1);

        Mockito.when(employeeRepository.findUserByEmail(any())).thenReturn(null);
        Mockito.when(employeeRepository.save(response)).thenReturn(response);

        Employee employee = employeeService.saveEmployee(newEmployee);
        Assert.assertEquals(employee.getRoles(), Roles.EMPLOYEE);
        Assert.assertEquals(employee.getFirstName(), newEmployee.getFirstName());
    }

    @Test
    public void deleteByIdTest(){

        response.setEmployeeId(111);

        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(response));
        employeeService.deleteEmployeeById(111);
        ArgumentCaptor<Integer> getDeleteId = ArgumentCaptor.forClass(Integer.class);
        verify(employeeRepository).deleteById(getDeleteId.capture());
        int getCaptured = getDeleteId.getValue();
        Assert.assertEquals(getCaptured,111);
    }

    @Test
    public void testshouldGetEmployeeAccount(){
        List<Reimbursement> sweet = new ArrayList<>(Arrays.asList(re));
        EmployeeId  id = new EmployeeId();
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(response));
        Mockito.when(repo.findByEmployee(anyInt())).thenReturn(sweet);
        EmployeeAccountResponse acct = employeeService.employeeAccount(id);
        Assert.assertEquals(acct.getFirstName(),"mark");

    }

    @Test
    public void testshouldReturnErrorEmployeeAccount(){
        List<Reimbursement> sweet = new ArrayList<>(Arrays.asList(re));
        EmployeeId  id = new EmployeeId();
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        Mockito.when(repo.findByEmployee(anyInt())).thenReturn(sweet);
        EmployeeAccountResponse acct = employeeService.employeeAccount(id);
        Assert.assertEquals(acct.getFirstName(),null);

    }



}
