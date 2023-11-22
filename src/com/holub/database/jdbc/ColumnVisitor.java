package com.holub.database.jdbc;

import com.holub.database.Table;

public class ColumnVisitor implements Visitor{

    private String[] holds;

    @Override
    public void visit(String[] subs) {
        if(holds == null) holds = subs;
        else{
            String[] mergedArray = new String[holds.length + subs.length];
            System.arraycopy(holds,0,mergedArray,0,holds.length);
            System.arraycopy(subs,0,mergedArray,holds.length,subs.length);
            holds = mergedArray;
        }
    }

    public String[] result(){
        return holds;
    }
}
