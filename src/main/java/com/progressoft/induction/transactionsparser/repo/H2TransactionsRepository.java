package com.progressoft.induction.transactionsparser.repo;

import com.progressoft.induction.transactionsparser.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2TransactionsRepository implements TransactionsRepository {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    static final String USER = "muhannad";
    static final String PASS = "";

    @Override
    public void saveTransaction(Transaction transaction) {

            Connection conn = null;
            Statement stmt = null;
            try{
                Class.forName(JDBC_DRIVER);

                System.out.println("Connecting to a selected database...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                System.out.println("Connected database successfully...");

                String description = transaction.getDescription();
                String direction = transaction.getDirection().toString();
                String amount = transaction.getAmount().toString();
                String currency = transaction.getCurrency().toString();

                stmt = conn.createStatement();
                String sql = "INSERT INTO MY_TRANSACTIONS (description, direction, amount, currency)"
                        + "VALUES ('"+description+"','"+direction+"','"+amount+"','"+currency+"')";

                stmt.executeUpdate(sql);
                System.out.println("Inserted records into the table...");
                System.out.println(transaction.toString());

                stmt.close();
                conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(stmt!=null) stmt.close();
                } catch(SQLException se2) {
                }
                try {
                    if(conn!=null) conn.close();
                } catch(SQLException se) {
                    se.printStackTrace();
                }
            }
            System.out.println("Done");
    }
}
