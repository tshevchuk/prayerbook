package com.tshevchuk.calendar_validation.comparators;

/**
 * Created by taras on 14.05.16.
 */
public class CompareResult {
    private final boolean equal;
    private final String expected;
    private final String actual;

    public CompareResult(boolean equal, String expected, String actual) {
        this.equal = equal;
        this.expected = expected;
        this.actual = actual;
    }

    public boolean isEqual() {
        return equal;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }
}
