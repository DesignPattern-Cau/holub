package com.holub.database.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeopleMapper<T> implements DataMapper<T>{
    private static final PeopleMapper INSTANCE = new PeopleMapper();

    private PeopleMapper(){}

    public static PeopleMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<T> mapResultSetToObject(ResultSet resultSet) throws SQLException {
        List<T> resultList = new ArrayList<>();
        while(resultSet.next()){
            String first = resultSet.getString("first");
            String last = resultSet.getString("last");
            Integer addrId = resultSet.getInt("addrId");
            People people = new People(last,first,addrId);
            resultList.add((T) people);
        }
        return resultList;
    }
}
