package com.kim.demo4;

public enum intEnums {
	
	randNumLength(10);
	
    private  int value;
    
    intEnums(int value){
        this.value = value;      
    }
    
    public int getValue(){
        return value;
    }
}
