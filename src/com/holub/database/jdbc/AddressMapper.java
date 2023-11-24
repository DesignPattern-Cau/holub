package com.holub.database.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressMapper<T> implements DataMapper<T>{
    private static AddressMapper INSTANCE = new AddressMapper();

    private AddressMapper(){}

    public static AddressMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public List<T> mapResultSetToObject(ResultSet resultSet) throws SQLException {
        List<T> result = new ArrayList<>();
        while(resultSet.next()){
            Integer addrId = resultSet.getInt("addrId");
            String street = resultSet.getString("street");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            String zip = resultSet.getString("zip");
            Address target = new Address(addrId,street,city,state,zip);
            result.add((T) target);
        }
        return result;
    }
}
