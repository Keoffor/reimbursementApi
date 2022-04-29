package com.example.reimbursementApi.model;

import com.example.reimbursementApi.utils.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EmployeeResponse {


        private int employeeId;
        @NonNull
        private String username;
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
