package com.progressoft.induction.transactionsparser.repo;

import com.progressoft.induction.transactionsparser.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2TransactionsRepository implements TransactionsRepository {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/db;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";

    static final String USER = "user";
    static final String PASS = "";

    private Connection register() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void sqlStatement(Connection conn, Transaction transaction) {
        Statement stmt = null;

        String description = transaction.getDescription();
        String direction = transaction.getDirection().toString();
        String amount = transaction.getAmount().toString();
        String currency = transaction.getCurrency().toString();
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO MY_TRANSACTIONS (description, direction, amount, currency)"
                    + "VALUES ('"+description+"','"+direction+"','"+amount+"','"+currency+"')";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void saveTransaction(Transaction transaction) {

        System.out.println("Connecting to a selected database...");
        Connection conn = register();
        System.out.println("Connected database successfully...");
        sqlStatement(conn, transaction);
        System.out.println("Inserted records into the table...");
        System.out.println(transaction.toString());
        System.out.println("Done");
    }
}
