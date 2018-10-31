package com.mobilelive.retailstore.exception;

public class ProductAlreadyUsedException extends BadRequestAlertException {

	private static final long serialVersionUID = 1L;

	public ProductAlreadyUsedException() {
		super(ErrorConstants.PRODUCT_ALREADY_USED_TYPE, "Name already used!", "productManagement", "productexists");
	}
}
