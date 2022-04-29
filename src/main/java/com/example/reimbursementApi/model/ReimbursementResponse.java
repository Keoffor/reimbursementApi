package com.example.reimbursementApi.model;

import com.example.reimbursementApi.model.Employee;
import com.example.reimbursementApi.utils.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReimbursementResponse {

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
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
    private int employee_id;
}
