package com.kim.demo4.apis.aws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class awsService {
	
	@Autowired
    private awsConfig awsConfig;
	
    private final String awsS3Url="https://s3.ap-northeast-2.amazonaws.com/kimsshop/images/";
    public JSONObject  uploadAws(MultipartFile multipartFile,String bucketName) {
   
        System.out.println("uploadAws");
        File file=convert(multipartFile);
        String saveName=file.getName();
        try {
			awsConfig.fileupload(bucketName, saveName, file);
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
    
        file.delete();
        System.out.println("파일업로드 완료");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("uploaded",true ); //ckeditor5
        jsonObject.put("url",awsS3Url+saveName);
        return jsonObject;
    }
    private File convert(MultipartFile multipartFile) {
        System.out.println("convert");
        File file=new File(LocalDate.now().toString()+UUID.randomUUID()+multipartFile.getOriginalFilename());
        try(FileOutputStream fileOutputStream=new FileOutputStream(file)){
            fileOutputStream.write(multipartFile.getBytes()); 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("파일형식변환에 실패했습니다");
        }
        return file;
    }
    public void deleteFile(String bucktetName,String fileName) {
    	awsConfig.deleteFile(bucktetName, fileName);
    }
}
