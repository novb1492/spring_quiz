package com.kim.demo4;

public enum stringEnums {
	
	email("email"),
	dtos("dtos"),
	bucketName("kimsshop/images");
	
    private  String value;
    
    stringEnums(String value){
        this.value = value;      
    }
    
    public String getValue(){
        return value;
    }
}
