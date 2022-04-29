package com.example.reimbursementApi.Repository;

import com.example.reimbursementApi.model.Reimbursement;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    @Query(value = "select reimbursement_id, created, description, email,incurred,prefered_method ,status ,total_expenses ,updated,employee_id,assigned_manager  \n" +
           "from reimbursement where employee_id = ?1", nativeQuery = true)
    List<Reimbursement> findByEmployee(int id);


}
