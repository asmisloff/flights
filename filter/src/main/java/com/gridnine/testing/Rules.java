package com.gridnine.testing;

import java.util.function.Predicate;

interface Rule<T> extends Predicate<T> {

    @SafeVarargs
    /* This method is used only by specific (not generic) typed methods, so varargs are safe. */
    static <U> Rule<U> and(Rule<U>... rules) {
        if (rules.length == 0) {
            throw new IllegalArgumentException("At least one rule must be provided for 'and' operation.");
        }
        return obj -> {
            for (var rule : rules) {
                if (!rule.test(obj)) {
                    return false;
                }
            }
            return true;
        };
    }

    @SafeVarargs
    /* This method is used only by specific (not generic) typed methods, so varargs are safe. */
    static <U> Rule<U> or(Rule<U>... rules) {
        if (rules.length == 0) {
            throw new IllegalArgumentException("At least one rule must be provided for 'or' operation.");
        }
        return obj -> {
            for (var rule : rules) {
                if (rule.test(obj)) {
                    return true;
                }
            }
            return false;
        };
    }

}

@FunctionalInterface
interface GapRule extends Rule<Long> {
}

@FunctionalInterface
interface FlightRule extends Rule<Flight> {
}

@FunctionalInterface
interface SegmentRule extends Rule<Segment> {
}
