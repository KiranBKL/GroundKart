package com.springrest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springrest.model.*;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
	
	@Query("select p from Product p where p.productName like concat(:p,'%')")
	public List<Product> getProductsByName(@Param("p") String pname);
	
	@Query("select p from Product p where p.productCategory like concat(:p,'%')")
	public List<Product> getProductsByCategory(@Param("p") String category);
	
	@Query("select p from Product p where p.brand like concat(:p,'%')")
	public List<Product> getProductsBybrand(@Param("p") String brand);
	
	

}
