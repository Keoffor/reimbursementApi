package com.example.reimbursementApi.services;

import com.example.reimbursementApi.Repository.EmployeeRepository;
import com.example.reimbursementApi.Repository.ReimbursementRepository;
import com.example.reimbursementApi.model.*;
import com.example.reimbursementApi.utils.Roles;
import com.example.reimbursementApi.utils.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ReimbursementService {
    private final Logger logger = LoggerFactory.getLogger(ReimbursementService.class);


    @Autowired
    ReimbursementRepository reRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    /**
     *
     * @return list of only users with the Role of managers that exist in the database in a random order.
     */
    private String assignManager(){
        List<Employee> managerList = employeeRepository.findByRoles(Roles.MANAGER);

        int randomNumber = new Random().nextInt(managerList.size());
        Employee newManager = managerList.get(randomNumber);
        return newManager.getFirstName()+","+newManager.getLastName();

    }

    /**
     *
     * @param requestor  will get user's  id and reimbursement request data
     * @return  user reimbursement request data into the database
     */
    public Reimbursement requestForm(RequestorForm requestor) {
        Optional<Employee> getEmployee = employeeRepository.findById(requestor.getEmployeeId());
        Employee employee = getEmployee.isPresent() ? getEmployee.get() : null;
        Reimbursement res = new Reimbursement();
        if (employee != null) {

            res.setEmail(employee.getEmail());
            res.setIncurred(requestor.getIncurred());
            res.setStatus(Status.UNDER_REVEIW);
            res.setPreferedMethod(requestor.getPreferedMethod());
            res.setTotalExpenses(requestor.getTotalExpenses());
            res.setDescription(requestor.getDescription());
            res.setAssignedManager(assignManager());
            res.setEmployee(employee);
            reRepository.save(res);

        } else {
            res = null;
            logger.info("This id does not exist");

        }

        return res;
    }

    public List<Reimbursement> getAllRequest() {
        return reRepository.findAll();
    }


    /**
     *
     * @return the list of all reimbursement requests in the Database submitted by users
     */
    public List<ReimbursementResponse> allRequest() {
        List<Reimbursement> getUsers = getAllRequest();
        List<ReimbursementResponse> response = getUsers.stream().map(em -> {
            ReimbursementResponse res = new ReimbursementResponse();
            res.setReimbursementId(em.getReimbursementId());
            res.setEmail(em.getEmail());
            res.setDescription(em.getDescription());
            res.setIncurred(em.getIncurred());
            res.setPreferedMethod(em.getPreferedMethod());
            res.setStatus(em.getStatus());
            res.setCreated(em.getCreated());
            res.setTotalExpenses(em.getTotalExpenses());
            res.setEmployee_id(em.getEmployee().getId());
            return res;
        }).collect(Collectors.toList());

        return response;

    }

    /**
     *
     * @param id the user id that exist in the database
     *
     * @return all reimbursement request records of the user that exist in the database
     */
    public Reimbursement getReimburseById(int id){
        Optional<Reimbursement> check = reRepository.findById(id);

        return check.isPresent()?check.get():null;
    }

    /**
     *
     * @return only first and lastname of  all managers that exist in the database in a random fashion.
     */
    private String assignRequest(){
        List<Employee> managerList = employeeRepository.findByRoles(Roles.MANAGER);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional <Employee> employee = employeeRepository.findByUsername(userDetails.getUsername());
        if(employee.isPresent()){
            Employee getManager = employee.get();
            List<Employee> filteredManager = managerList.stream().filter(em -> em.getId()!= getManager.getId()).collect(Collectors.toList());
            int randomNumber = new Random().nextInt(filteredManager.size());
            Employee newManager = filteredManager.get(randomNumber);

            return newManager.getFirstName()+"," +newManager.getLastName();


        }
        return "";

    }

    /**
     *
     * @param app will get the unique id number of the reimbursement request that exist in the database
     * @return approval status update of the reimbursement request by the user.
     * @throws Exception when the reimbursement id number does not exist in the database
     */

    public ApprovalResponse approvedStatus(ApprovedRequest app) throws Exception{
        ApprovalResponse  reimburseEmail;

        Reimbursement reimburse = getReimburseById(app.getReimbursementId());
        if(reimburse==null){
            logger.info("There is no such reimbursement id number");
        }

        if(reimburse!=null && app.getOptions().equals("A")){
            reimburse.setStatus(Status.APPROVED);
            reimburse.setReimbursementId(app.getReimbursementId());
            reRepository.save(reimburse);
        }else if(app.getOptions().equals("R")){

            reimburse.setStatus(Status.REASSIGNED);
            reimburse.setReimbursementId(app.getReimbursementId());
            reimburse.setAssignedManager(assignRequest());
            reRepository.save(reimburse);
        }
        else if(app.getOptions().equals("D")){
            reimburse.setStatus(Status.DENIED);
            reimburse.setReimbursementId(app.getReimbursementId());
            reRepository.save(reimburse);
        }
        else{
            logger.info("A wrong command key was selected");
            throw new Exception();
        }


        reimburseEmail =  new ApprovalResponse(reimburse.getStatus(), reimburse.getEmail(),
                reimburse.getAssignedManager(),reimburse.getEmployee().getFirstName());


        return reimburseEmail;
    }
}



