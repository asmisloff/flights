package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;

class SegmentRules {

    static SegmentRule departureDateIsAfter(LocalDateTime dt) {
        return s -> s.getDepartureDate().isAfter(dt);
    }

    static SegmentRule departureDateIsBefore(LocalDateTime dt) {
        return s -> s.getDepartureDate().isBefore(dt);
    }

    static SegmentRule arrivalDateIsAfter(LocalDateTime dt) {
        return s -> s.getArrivalDate().isAfter(dt);
    }

    static SegmentRule arrivalDateIsBefore(LocalDateTime dt) {
        return s -> s.getArrivalDate().isBefore(dt);
    }

    static SegmentRule durationGreaterThan(long minutes) {
        return s -> {
            long groundTime = Duration.between(s.getDepartureDate(), s.getArrivalDate()).toMinutes();
            return groundTime > minutes;
        };
    }

    static SegmentRule durationLesserThan(long minutes) {
        return s -> {
            long groundTime = Duration.between(s.getDepartureDate(), s.getArrivalDate()).toMinutes();
            return groundTime < minutes;
        };
    }

    static SegmentRule durationEqualsTo(long minutes) {
        return s -> {
            long groundTime = Duration.between(s.getDepartureDate(), s.getArrivalDate()).toMinutes();
            return groundTime == minutes;
        };
    }

    static SegmentRule and(SegmentRule... rules) {
        return s -> Rule.and(rules).test(s);
    }

    static SegmentRule or(SegmentRule... rules) {
        return s -> Rule.or(rules).test(s);
    }

}
