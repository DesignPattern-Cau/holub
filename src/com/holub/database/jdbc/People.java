package com.holub.database.jdbc;

public class People {
    private String last;
    private String first;
    private int addrId;

    public People(String last, String first, int addrId) {
        this.last = last;
        this.first = first;
        this.addrId = addrId;
    }

    @Override
    public String toString() {
        return "People{" +
                "last='" + last + '\'' +
                ", first='" + first + '\'' +
                ", addrId=" + addrId +
                '}';
    }
}
