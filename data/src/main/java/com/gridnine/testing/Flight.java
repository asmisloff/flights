package com.gridnine.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean that represents a flight.
 */
class Flight {

    private final List<Segment> segments;
    private final List<Long> gaps;

    Flight(final List<Segment> segs) {
        segments = segs;
        gaps = new ArrayList<>(segments.size() - 1);
        calculateGaps();
    }

    private void calculateGaps() {
        if (segments.size() > 1) {
            for (int i = 0; i < segments.size() - 1; ++i) {
                long gap = Duration.between(
                        segments.get(i).getArrivalDate(),
                        segments.get(i + 1).getDepartureDate()
                )
                        .toMinutes();
                gaps.add(i, gap);
            }
        }
    }

    List<Segment> getSegments() {
        return segments;
    }

    List<Long> getGaps() {
        return gaps;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

}