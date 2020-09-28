package com.progressoft.induction.transactionsparser;

public enum Direction {
    Credit,
    Debit;

    public static String isValidDirection(String testDirection) {
        for (Direction d : Direction.values()) {
            if (d.name().equals(testDirection)) {
                return testDirection;
            }
        }
        throw new Direction.DirectionException("Invalid Direction Value " + testDirection);
    }

    public static class DirectionException extends RuntimeException {
        public DirectionException(String message) {
            super(message);
        }
    }
}
