package com.github.lucasyukio.nossobancodigital.advice;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

	private HttpStatus status;
	private List<String> errors;
	
	public ApiError(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }
 
    public ApiError(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = Arrays.asList(error);
    }
    
}
