package com.holub.database.jdbc;

public class Address {
    private int addrId;
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(int addrId, String street, String city, String state, String zip) {
        this.addrId = addrId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addrId=" + addrId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
