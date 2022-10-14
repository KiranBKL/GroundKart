package com.springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springrest.model.PaymentType;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Integer>{

}
