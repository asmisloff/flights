package com.gridnine.testing;

class FlightRules {

    static FlightRule numberOfSegmentsGreaterThan(int n) {
        return flight -> flight.getSegments().size() > n;
    }

    static FlightRule numberOfSegmentsLesserThan(int n) {
        return flight -> flight.getSegments().size() < n;
    }

    static FlightRule numberOfSegmentsEqualsTo(int n) {
        return flight -> flight.getSegments().size() == n;
    }

    static FlightRule anySegment(SegmentRule rule) {
        return f -> {
            for (var segment : f.getSegments()) {
                if (rule.test(segment)) {
                    return true;
                }
            }
            return false;
        };
    }

    static FlightRule allSegments(SegmentRule rule) {
        return f -> {
            for (var segment : f.getSegments()) {
                if (!rule.test(segment)) {
                    return false;
                }
            }
            return true;
        };
    }

    static FlightRule numberOfGapsGreaterThan(int n) {
        return flight -> flight.getGaps().size() > n;
    }

    static FlightRule numberOfGapsLesserThan(int n) {
        return flight -> flight.getGaps().size() < n;
    }

    static FlightRule numberOfGapsEqualsTo(int n) {
        return flight -> flight.getGaps().size() == n;
    }

    static FlightRule anyGap(GapRule rule) {
        return f -> {
            for (var gap : f.getGaps()) {
                if (rule.test(gap)) {
                    return true;
                }
            }
            return false;
        };
    }

    static FlightRule allGaps(GapRule rule) {
        return f -> {
            for (var gap : f.getGaps()) {
                if (!rule.test(gap)) {
                    return false;
                }
            }
            return true;
        };
    }

    static FlightRule and(FlightRule... rules) {
        return f -> Rule.and(rules).test(f);
    }

    static FlightRule or(FlightRule... rules) {
        return f -> Rule.or(rules).test(f);
    }

}
