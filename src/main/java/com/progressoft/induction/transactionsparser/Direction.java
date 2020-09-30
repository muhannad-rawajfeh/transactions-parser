package com.progressoft.induction.transactionsparser;

public enum Direction {

    CREDIT("Credit"),
    DEBIT("Debit");

    Direction(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    public static Direction getValidDirection(String direction) {
        if (ParserValidators.isValidDirection(direction)) {
            return Direction.valueOf(direction.toUpperCase());
        }
        throw new Direction.DirectionException("Invalid Direction Value " + direction);
    }

    public static class DirectionException extends RuntimeException {
        public DirectionException(String message) {
            super(message);
        }
    }
}