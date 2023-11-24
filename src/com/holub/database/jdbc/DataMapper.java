package com.holub.database.jdbc;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataMapper<T> {
    List<T> mapResultSetToObject(ResultSet resultSet) throws SQLException;

    static <T extends DataMapper<?>> T createSingletonInstance(Class<T> clazz){
        try{
            Method method = clazz.getMethod("getInstance");
            return (T) method.invoke(null);
        }catch (Exception e){
            return null;
        }
    }

}
