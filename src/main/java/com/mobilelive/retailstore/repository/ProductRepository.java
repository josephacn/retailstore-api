package com.mobilelive.retailstore.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mobilelive.retailstore.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	Optional<Product> findOneByName(String name);

	// Product findOne(Long id);

	Page<Product> findAll(Pageable pageable);

}
