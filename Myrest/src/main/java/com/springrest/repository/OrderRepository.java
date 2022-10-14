package com.springrest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springrest.model.*;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

	@Query("select o from Order o where o.orderStatus=:p")
	public List<Order> getProductsBybrand(@Param("p") String status);
//
//	@Query("update o Order set o.orderStatus=:p where o.id=:oid")
//	public void deliveryConfirm(@Param("p") String status,@Param("oid") int oid);

}
