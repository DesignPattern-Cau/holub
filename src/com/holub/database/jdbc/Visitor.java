package com.holub.database.jdbc;

import com.holub.database.Table;

public interface Visitor {
    public void visit(String[] sub);
    public String[] result();
}
