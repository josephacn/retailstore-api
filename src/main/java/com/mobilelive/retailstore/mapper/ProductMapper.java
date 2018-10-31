package com.mobilelive.retailstore.mapper;

import java.util.Set;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.mobilelive.retailstore.domain.Product;
import com.mobilelive.retailstore.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

	Set<ProductDTO> productsToProductDTOs(Stream<Product> products);

	ProductDTO productToProductDTO(Product product);

	Product productDTOToProduct(ProductDTO productDTO);

	Set<Product> productDTOsToProducts(Stream<ProductDTO> productDTOs);

	Product productFromId(String id);

}
