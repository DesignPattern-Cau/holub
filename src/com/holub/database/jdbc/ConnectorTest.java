package com.holub.database.jdbc;

import java.util.List;

public class ConnectorTest {

    public static void main(String[] args) {
        HolubRepository<Address> repository = new HolubRepository<>();
        List<Address> people = repository.processSelect("address", "select * from address");
        for (Address person : people) {
            System.out.println("person = " + person);
        }
    }
}
