package com.springrest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springrest.model.*;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer>{


	
	
}
