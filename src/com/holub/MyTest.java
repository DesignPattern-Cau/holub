package com.holub;

import com.holub.database.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class MyTest {

    public static void main(String[] args) {

        testCSVExport();
    }

    public static void testCSVExport(){
        System.out.println("//// TEST Exporter /////");
        Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
        try {
            people.insert(new Object[]{"Holub", "Allen", "1"});
            people.insert(new Object[]{"Holub2", "Allen", "12"});
            Writer out = new FileWriter( "people.html" );
            people.export( new HTMLExporter(out) );
            out.close();
            Reader in = new FileReader( "people.xml" );
        	people = new ConcreteTable( new XMLImporter(in) );
            System.out.println("people = " + people);
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
