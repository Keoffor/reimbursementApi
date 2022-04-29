package com.example.reimbursementApi.model;

import com.example.reimbursementApi.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ApprovalResponse {
    private Status status;
    private String email;
    private String managerFullname;
    private String employeeName;
}
