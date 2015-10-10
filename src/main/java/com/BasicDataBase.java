package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.maxmind.geoip2.DatabaseReader;

public class BasicDataBase {
    
    
    private static DatabaseReader reader =null;
    
    public static DatabaseReader getDatabaseReader(String path){
        if(reader==null){
        	try { 
                File file = new File("GeoLite2-City.mmdb");
                System.out.println(file.getAbsolutePath());
                path=file.getAbsolutePath(); 
                reader = new DatabaseReader.Builder(file).build();  
            } catch (FileNotFoundException e) {  
            } catch (IOException e) {  
            } 
        }
        return reader;  
    }  
      
    private BasicDataBase(){  
    }  

}  