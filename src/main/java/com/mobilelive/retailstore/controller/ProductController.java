package com.mobilelive.retailstore.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilelive.retailstore.domain.Product;
import com.mobilelive.retailstore.dto.ProductDTO;
import com.mobilelive.retailstore.exception.BadRequestAlertException;
import com.mobilelive.retailstore.exception.ProductAlreadyUsedException;
import com.mobilelive.retailstore.repository.ProductRepository;
import com.mobilelive.retailstore.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	private final Logger log = LoggerFactory.getLogger(ProductController.class);

	private final ProductService productService;

	private final ProductRepository productRepository;

	public ProductController(ProductService productService, ProductRepository productRepository) {

		this.productService = productService;
		this.productRepository = productRepository;
	}

	/**
	 * POST /products : Creates a new product.
	 * <p>
	 * Creates a new product if the name are not already used, and sends
	 *
	 * @param productDTO
	 *                       the product to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         product, or with status 400 (Bad Request) if the name is already in
	 *         use
	 * @throws URISyntaxException
	 *                                      if the Location URI syntax is incorrect
	 * @throws BadRequestAlertException
	 *                                      400 (Bad Request) if the name is already
	 *                                      in use
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
		log.debug("REST request to save Product : {}", productDTO);

		if (productDTO.getId() != null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", "productManagement",
					"idexists");
		} else if (productRepository.findOneByName(productDTO.getName()).isPresent()) {
			throw new ProductAlreadyUsedException();
		} else {
			final Product newProduct = productService.createProduct(productDTO);
			return ResponseEntity.created(new URI("/api/products/" + newProduct.getId())).body(newProduct);
		}
	}

	/**
	 * PUT /products : Updates an existing product.
	 *
	 * @param productDTO
	 *                       the product to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         product in use
	 * @throws ProductAlreadyUsedException
	 *                                         400 (Bad Request) if the login is
	 *                                         already in use
	 */
	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
		final Optional<Product> existingProduct = productRepository.findOneByName(productDTO.getName());
		if (existingProduct.isPresent() && !existingProduct.get().getName().equals(productDTO.getName())) {
			throw new ProductAlreadyUsedException();
		}
		final Optional<ProductDTO> updatedProduct = productService.updateProduct(productDTO);

		return ResponseEntity.ok().body(updatedProduct.get());
	}

	/**
	 * GET /products : get all products.
	 *
	 * @param pageable
	 *                     the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all products
	 */
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
		final Page<ProductDTO> page = productService.getAllProducts(pageable);
		return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
	}

	/**
	 * GET /products/:id : get the "id" product.
	 *
	 * @param id
	 *               of the product to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "id"
	 *         product, or with status 404 (Not Found)
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		log.debug("REST request to get Product : {}", id);
		final Optional<Product> product = productRepository.findById(id);

		return product.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /products/:id : delete the "id" product.
	 *
	 * @param id
	 *               of the product to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		log.debug("REST request to delete Product: {}", id);
		productService.deleteProduct(id);
		return new ResponseEntity<String>("Product has been deleted!", HttpStatus.OK);
	}
}