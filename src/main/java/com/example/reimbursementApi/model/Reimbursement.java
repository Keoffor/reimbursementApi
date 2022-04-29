package com.example.reimbursementApi.model;

import com.example.reimbursementApi.utils.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "reimbursement")
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbursementId;
    @NonNull
    private String email;
    @NonNull
    private String preferedMethod;
    @NonNull
    private long totalExpenses;

    @JsonFormat(pattern ="dd/mm/yyyy")
    private Date incurred;
    @NonNull
    private String description;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="employee_id", nullable =false)
    private Employee employee;

    private String assignedManager;




}
