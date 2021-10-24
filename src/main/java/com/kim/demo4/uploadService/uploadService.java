package com.kim.demo4.uploadService;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kim.demo4.restController;
import com.kim.demo4.apis.aws.awsService;

@Service
public class uploadService {
	private static Logger logger=LoggerFactory.getLogger(uploadService.class);

	
	@Autowired
	private awsService awsService;
	
	public JSONObject imageUpload(MultipartHttpServletRequest request) {
		logger.debug("imageUpload");
		 List<MultipartFile> multipartFiles=new ArrayList<MultipartFile>();
			
			multipartFiles = request.getFiles("upload");
		
			System.out.println(multipartFiles.toString());
		return awsService.uploadAws(multipartFiles.get(0), "kimsshop/images");
	}
}
