package com.kim.demo4.uploadService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.demo4.restController;
import com.kim.demo4.apis.aws.awsService;

@Service
public class uploadService {
	private static Logger logger=LoggerFactory.getLogger(uploadService.class);

	
	@Autowired
	private awsService awsService;
	
	public void imageUpload() {
		logger.debug("imageUpload");
	}
}
