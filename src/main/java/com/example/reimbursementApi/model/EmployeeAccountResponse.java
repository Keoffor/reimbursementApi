package com.example.reimbursementApi.model;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class EmployeeAccountResponse {
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private List<Reimbursement> reimbursement_records;
}
