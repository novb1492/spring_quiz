package com.kim.demo4.apis.aws;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class awsConfig {

    private String accessKey ="AKIAXVQHL24PHXPXKYU4";
    private String secretKey ="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";

   // private AmazonS3 s3Client;
    
    @Bean
    public AmazonS3 S3Client() {
        System.out.println(secretKey);
        AWSCredentials credentials=new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                    .withRegion("ap-northeast-2").build();
    }
    /*public awsConfig () {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        this.s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint("s3.ap-northeast-2.amazonaws.com"); //  아시아 태평양 서울
    }*/

   /* public void fileupload(String bucketName,String fileName, File file) throws FileNotFoundException{
        this.s3Client.putObject(bucketName,fileName, file);
    }
    public void deleteFile(String bucktetName,String fileName) {
    	this.s3Client.deleteObject(bucktetName, fileName);
    }*/
}

