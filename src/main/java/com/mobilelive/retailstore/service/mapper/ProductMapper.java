package com.mobilelive.retailstore.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mobilelive.retailstore.domain.Product;
import com.mobilelive.retailstore.dto.ProductDTO;

@Service
public class ProductMapper {

	public ProductDTO productToProductDTO(Product product) {
		return new ProductDTO(product);
	}

	public List<ProductDTO> productsToUProductDTOs(List<Product> products) {
		return products.stream().filter(Objects::nonNull).map(this::productToProductDTO).collect(Collectors.toList());
	}

	public Product productDTOToProduct(ProductDTO productDTO) {
		if (productDTO == null) {
			return null;
		} else {
			final Product product = new Product();
			product.setId(productDTO.getId());
			product.setPrice(productDTO.getPrice());
			product.setDescription(productDTO.getDescription());
			return product;
		}
	}

	public List<Product> userDTOsToUsers(List<ProductDTO> productDTOs) {
		return productDTOs.stream().filter(Objects::nonNull).map(this::productDTOToProduct)
				.collect(Collectors.toList());
	}

	public Product productFromId(Long id) {
		if (id == null) {
			return null;
		}
		final Product product = new Product();
		product.setId(id);
		return product;
	}

}
