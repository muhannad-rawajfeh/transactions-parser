package com.progressoft.induction.transactionsparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XmlTransactionsParser implements TransactionParser {
    private final int numberOfElements;

    // Todo: add fields validation ? yes please :D
    XmlTransactionsParser(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {

        ParserValidators.isNullFile(transactionsFile);
        ParserValidators.isExistingFile(transactionsFile);
        ParserValidators.isXmlFile(transactionsFile);

        final List<Transaction> transactions = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(transactionsFile, handle(transactions));

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private DefaultHandler handle(List<Transaction> transactions) {

        DefaultHandler handler;
        handler = new DefaultHandler() {
            boolean bDescription = false;
            boolean bDirection = false;
            boolean bValue = false;
            boolean bCurrency = false;
            int lineNumber = 0;
            Transaction transaction = null;

            @Override
            public void startElement(String uri, String localName, String qName,
                                     Attributes attributes) {

                //TODO extract constants (fields name)
                if (qName.equalsIgnoreCase("Transaction")) {
                    transaction = new Transaction();
                    lineNumber++;
                }
                if (qName.equalsIgnoreCase("Description")) {
                    bDescription = true;
                }
                if (qName.equalsIgnoreCase("Direction")) {
                    bDirection = true;
                }
                if (qName.equalsIgnoreCase("Value")) {
                    bValue = true;
                }
                if (qName.equalsIgnoreCase("Currency")) {
                    bCurrency = true;
                }
            }

            @Override
            public void endElement(String uri, String localName,
                                   String qName) {

                if (qName.equalsIgnoreCase("Transaction")) {
                    transactions.add(transaction);
                }
                if (qName.equalsIgnoreCase("Description")) {
                    bDescription = false;
                }
                if (qName.equalsIgnoreCase("Direction")) {
                    bDirection = false;
                }
                if (qName.equalsIgnoreCase("Value")) {
                    bValue = false;
                }
                if (qName.equalsIgnoreCase("Currency")) {
                    bCurrency = false;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {

                if (bDescription) {
                    transaction.setDescription(new String(ch, start, length));
                }
                if (bDirection) {
                    transaction.setDirection(Direction.getValidDirection(new String(ch, start, length)));
                }
                if (bValue) {
                    transaction.setAmount(new BigDecimal(ParserValidators.getValidAmount(new String(ch, start, length), lineNumber)));
                }
                if (bCurrency) {
                    transaction.setCurrency(ParserValidators.getValidCurrency(new String(ch, start, length), lineNumber));
                }
            }
        };
        return handler;
    }
}