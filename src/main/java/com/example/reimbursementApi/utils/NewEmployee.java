package com.example.reimbursementApi.utils;

import com.example.reimbursementApi.model.Reimbursement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class NewEmployee {

    @Column(unique = true)
    private int employeeId;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Roles roles;
}

