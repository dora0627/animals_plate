package com.example.animals_plate;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class pgConnection {
    Connection connection=null;
    public  Connection connectionPG(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgresql","g479500");
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
        return connection;
    }
}
