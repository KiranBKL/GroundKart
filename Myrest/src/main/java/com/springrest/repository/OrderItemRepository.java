package com.springrest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springrest.model.*;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer>{

}
