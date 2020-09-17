package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String path = "/home/user/IdeaProjects/transactionsparser/src/main/resources/transactions.csv";
        File file = new File(path);
        CSVParser p = new CSVParser();
        List<Transaction> parse = p.parse(file);
        for(Transaction ob: parse) {
            System.out.println(ob.toString());
        }
    }
}
