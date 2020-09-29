package com.progressoft.induction.transactionsparser;

public enum Direction {
    //TODO: By convention, enums are constants. In Java, constants are defined in all UPPER_CASE letters.
    //enum name should be in title case (same as class names).
    //enum fields should be in all UPPER CASE (same as static final constants).
    Credit,
    Debit;

    //TODO : use valueOf method
    public static String isValidDirection(String direction) {
        for (Direction d : Direction.values()) {
            if (d.name().equals(direction)) {
                return direction;
            }
        }
        throw new DirectionException("Invalid Direction Value " + direction);
    }

    public static class DirectionException extends RuntimeException {
        public DirectionException(String message) {
            super(message);
        }
    }
}
