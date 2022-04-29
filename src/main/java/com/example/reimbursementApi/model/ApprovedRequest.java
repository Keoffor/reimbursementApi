package com.example.reimbursementApi.model;

import com.example.reimbursementApi.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedRequest {

    private int reimbursementId;
    private Status status;
    private Date updated;
    private String options;
}
