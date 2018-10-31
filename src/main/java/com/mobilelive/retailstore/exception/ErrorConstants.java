package com.mobilelive.retailstore.exception;

import java.net.URI;

public class ErrorConstants {

	public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
	public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
	public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
	public static final URI PRODUCT_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/product-already-used");

	private ErrorConstants() {
	}

}
