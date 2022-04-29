package com.example.reimbursementApi.model;

import com.example.reimbursementApi.utils.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "EMPLOYEE")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
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

    @OneToMany(mappedBy = "employee")
    private List<Reimbursement> reimbursement;
}
