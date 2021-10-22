package com.kim.demo4;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class restErrorController {
	
	   @ExceptionHandler(MethodArgumentNotValidException.class)
	    public JSONObject processValidationError(MethodArgumentNotValidException exception) {
	        System.out.println("processValidationError 유효성 검사 실패");
	        BindingResult bindingResult = exception.getBindingResult();
	        StringBuilder builder = new StringBuilder();
	        List<String>list=new ArrayList<>();
	        
	        for (FieldError fieldError : bindingResult.getFieldErrors()) {
	            builder.append(fieldError.getDefaultMessage());
	            list.add(fieldError.getField());
	        }

	        return utillService.makeJson(false, builder.toString());
	    }
	    @ExceptionHandler(RuntimeException.class)
	    public JSONObject runtimeException(RuntimeException exception) {
	        System.out.println("runtimeException");
	        exception.printStackTrace();
	        return utillService.makeJson(false, exception.getMessage());
	    }
}
