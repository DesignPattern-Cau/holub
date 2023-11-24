package com.holub.database.jdbc;

import java.io.File;
import java.sql.*;

public class HolubConnection {
    private static HolubConnection INSTANCE = null;
    private static final String driverName = "com.holub.database.jdbc.JDBCDriver";
    private Connection connection = null;
    private Statement statement  = null;
    private final String DATABASE_NAME = "c:/dp";

    private HolubConnection(){}

    public static HolubConnection getInstance(){
        if(INSTANCE == null){
            INSTANCE = new HolubConnection();
            INSTANCE.openDatabase();
        }
        return INSTANCE;
    }

    public void openDatabase()
    {
        File database = new File( DATABASE_NAME );
        try
        {
            Class.forName( driverName ).newInstance();
            connection = DriverManager.getConnection("file:/"+DATABASE_NAME, "harpo", "swordfish");
            statement = connection.createStatement();
        }
        catch( Exception e )
        {
            System.out.println("e = " + e);
        }
    }

    private void closeDatabase()
    {	try
    {	if(statement != null) statement.close();
        if(connection!= null) connection.close();
    }
    catch(Exception e)
    {
    }
    }

    public ResultSet processSQL(String query) throws SQLException {
        return statement.executeQuery(query);
    }

}
