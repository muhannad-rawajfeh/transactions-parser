package com.progressoft.induction.transactionsparser;

import java.io.File;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        XMLParser p = new XMLParser();
        List<Transaction> parse = p.parse(new File("resources\\transactions.xml"));
        for(Transaction ob: parse){
            System.out.println(ob.toString());
        }
    }
}
