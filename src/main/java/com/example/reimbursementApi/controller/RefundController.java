package com.example.reimbursementApi.controller;

import com.example.reimbursementApi.model.*;
import com.example.reimbursementApi.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/reimbursement")
public class RefundController {
    @Value("${api.config.api2-url: http://localhost:8001/email}")
    String email_url;

    @Autowired
    ReimbursementService reimbursementService;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping(value = "/makeRequest", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity makeRequest(@RequestBody RequestorForm form){
        Reimbursement res = reimbursementService.requestForm(form);
        if (res!=null){
            restTemplate.postForEntity(email_url+"/send",res.getEmail(),null );
            return ResponseEntity.status(HttpStatus.OK).body("request successfully submitted");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("wrong employee id");
        }
    }

    @GetMapping(value = "/allRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllRequest(){
        List<ReimbursementResponse> er = reimbursementService.allRequest();
        return ResponseEntity.status(HttpStatus.OK).body(er);
    }

    /**
     *
     * @param ar This is kenneth
     * @return Anything, i dont know
     */
    @PutMapping(value = "/approvalDesk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity approvalStatus(@RequestBody ApprovedRequest ar) {
        try {

            ApprovalResponse res = reimbursementService.approvedStatus(ar);
            restTemplate.postForEntity(email_url+"/approval",res,null );
            return ResponseEntity.status(HttpStatus.OK).body("Request " + res.getStatus());
        }catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("There is no such Reimbursement Id number. Also be sure " +
                    "you choose the right command key: \n" +
                    "'A' - Approval \n'D' - Denied \n'R' - Reassigned");
        }
    }
}
