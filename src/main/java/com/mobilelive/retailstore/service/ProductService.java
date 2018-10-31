package com.mobilelive.retailstore.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobilelive.retailstore.domain.Product;
import com.mobilelive.retailstore.dto.ProductDTO;
import com.mobilelive.retailstore.exception.ProductAlreadyUsedException;
import com.mobilelive.retailstore.mapper.ProductMapper;
import com.mobilelive.retailstore.repository.ProductRepository;

/**
 * Service class for managing products.
 */
@Service
@Transactional
public class ProductService {

	private final Logger log = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;

	private final ProductMapper productMapper;

	public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
	}

	public Product createProduct(ProductDTO productDTO) {
		productRepository.findOneByName(productDTO.getName()).ifPresent(existingProduct -> {
			try {
				throw new ProductAlreadyUsedException();
			} catch (final ProductAlreadyUsedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Product newProduct = new Product();
		newProduct = productMapper.productDTOToProduct(productDTO);
		productRepository.save(newProduct);
		log.debug("Created Information for Product: {}", newProduct);
		return newProduct;
	}

	/**
	 * Update all information for a specific product, and return the modified
	 * product.
	 *
	 * @param productDTO product to update
	 * @return updated product
	 */
	public Optional<ProductDTO> updateProduct(ProductDTO productDTO) {
		return Optional.of(productRepository.findById(productDTO.getId())).filter(Optional::isPresent)
				.map(Optional::get).map(product -> {
					product = productMapper.productDTOToProduct(productDTO);
					log.debug("Changed Information for Product: {}", product);
					return product;
				}).map(ProductDTO::new);
	}

	public void deleteProduct(Long id) {
		productRepository.findById(id).ifPresent(product -> {
			productRepository.delete(product);
			log.debug("Deleted Product: {}", product);
		});
	}

	public Page<ProductDTO> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable).map(ProductDTO::new);
	}

}
