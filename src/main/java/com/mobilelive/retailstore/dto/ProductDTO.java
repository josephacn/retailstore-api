package com.mobilelive.retailstore.dto;

import com.mobilelive.retailstore.domain.Product;

public class ProductDTO {

	private Long id;

	private String name;

	private Double price;

	private String description;

	public ProductDTO() {

	}

	public ProductDTO(Product product) {

		id = product.getId();
		name = product.getName();
		price = product.getPrice();
		description = product.getDescription();
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProductDTO{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", price='" + price + '\'' + "}";
	}
}
