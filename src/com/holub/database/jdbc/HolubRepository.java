package com.holub.database.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class HolubRepository<T> {

    private HolubConnection holubConnection;

    public HolubRepository() {
        this.holubConnection = HolubConnection.getInstance();
        holubConnection.openDatabase();
    }

    private final GenericMapperFactory genericMapperFactory = new GenericMapperFactory();
    public List<T> processSelect(String className, String query){
        className = Character.toUpperCase(className.trim().charAt(0)) + className.trim().substring(1);
        DataMapper<T> mapper = GenericMapperFactory.createMapper(className);

        if(mapper == null) return new ArrayList<>();

        try{
            ResultSet resultSet = holubConnection.processSQL(query);
            return mapper.mapResultSetToObject(resultSet);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public int processUpdate(String className, String query){
        try{
            holubConnection.processSQL(query);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

}
