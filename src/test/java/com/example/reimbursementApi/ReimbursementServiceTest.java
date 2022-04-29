package com.example.reimbursementApi;

import com.example.reimbursementApi.Repository.EmployeeRepository;
import com.example.reimbursementApi.Repository.ReimbursementRepository;
import com.example.reimbursementApi.model.*;
import com.example.reimbursementApi.services.EmployeeService;
import com.example.reimbursementApi.services.ReimbursementService;
import com.example.reimbursementApi.utils.NewEmployee;
import com.example.reimbursementApi.utils.Roles;
import com.example.reimbursementApi.utils.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {




    @Mock
    ReimbursementRepository reimbursementRepository;

    @Mock
    EmployeeRepository employeeRepository;


    @InjectMocks
    private ReimbursementService reimbursementService;

    Employee response = new Employee("username","xdjdjd","jsn@yahoo.com","mark",
            "long",Roles.EMPLOYEE);
    Reimbursement re = new Reimbursement("ken@yahoo.com","cash",599,"travel " +
            "expenses", Status.UNDER_REVEIW);
    ReimbursementResponse res = new ReimbursementResponse(1,"ken@yahoo.com","cash",
            89,new Date(),"traved",new Date(),Status.UNDER_REVEIW,1);
    RequestorForm form = new RequestorForm(1,"cash",500,new Date(),"wedding");

    ApprovedRequest appRequest = new ApprovedRequest(1,Status.APPROVED,new Date(),"A");


    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldGetAllReimburseRequest(){

        re.setEmployee(response);
        List<Reimbursement> reimburse = new ArrayList<>(Arrays.asList(re));
        Mockito.when(reimbursementRepository.findAll()).thenReturn(reimburse);
        List<ReimbursementResponse> result = reimbursementService.allRequest();
        Assert.assertEquals("ken@yahoo.com",result.get(0).getEmail());
        Assert.assertEquals("cash",result.get(0).getPreferedMethod());
        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void requestFormTest(){
        List<Employee> getEmployee = new ArrayList<>(Arrays.asList(response));
        Optional<Employee> check = Optional.of(response);
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(check);
        Mockito.when(employeeRepository.findByRoles(Roles.MANAGER)).thenReturn(getEmployee);

        Reimbursement reimbursement = reimbursementService.requestForm(form);
        Assert.assertEquals(reimbursement.getStatus(), Status.UNDER_REVEIW);
        Assert.assertEquals(reimbursement.getEmail(),"jsn@yahoo.com");
        Assert.assertEquals(reimbursement.getPreferedMethod(),"cash");


    }

    @Test
    public void requestFormTestShouldReturnError()
    {
        Optional<Employee> check = Optional.empty();
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(check);
        Reimbursement reimbursement = reimbursementService.requestForm(form);
        Assert.assertTrue(reimbursement == null);

    }
    @Test
    public void approvedStatusTest() throws Exception {
        re.setEmployee(response);

        List<Employee> employees = new ArrayList<>(Arrays.asList(response));
        Optional<Employee> optional = Optional.of(response);
        Optional<Reimbursement> ops = Optional.of(re);
        Mockito.when(employeeRepository.findByRoles(Roles.MANAGER)).thenReturn(employees);
        Mockito.when(employeeRepository.findByUsername(any())).thenReturn(optional);
        Mockito.when(reimbursementRepository.findById(anyInt())).thenReturn(ops);

        Mockito.when((reimbursementRepository.save(re))).thenReturn(re);


        ApprovalResponse app = reimbursementService.approvedStatus(appRequest);
        Assert.assertEquals(app.getEmail(),"ken@yahoo.com");
        Assert.assertEquals(app.getStatus(),Status.APPROVED);
        Assert.assertEquals(app.getEmployeeName(),"mark");


    }
    @Test(expected = Exception.class)
    public void approveStatusShouldReturnError() throws Exception {
        Optional<Reimbursement> check = Optional.empty();
        Mockito.when(reimbursementRepository.findById(anyInt())).thenReturn(check);
        ApprovalResponse app = reimbursementService.approvedStatus(appRequest);
        Assert.assertTrue(app ==null);

    }




}
