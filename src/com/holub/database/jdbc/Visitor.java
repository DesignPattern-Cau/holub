package com.holub.database.jdbc;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface Visitor {
    public void visit(String[] sub);
    public String[] result();

}
