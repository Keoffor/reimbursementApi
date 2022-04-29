package com.example.reimbursementApi.model;


import com.example.reimbursementApi.model.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RequestorForm {
    @NonNull
    private int employeeId;
    @NonNull
    private String preferedMethod;
    @NonNull
    private long totalExpenses;

    @JsonFormat(pattern ="dd/mm/yyyy")
    private Date incurred;
    @NonNull
    private String description;


}
