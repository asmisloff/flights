package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Departure before now: " +
                FlightFilter.applyRule(
                        FlightRules.anySegment(
                                SegmentRules.departureDateIsBefore(LocalDateTime.now())
                        ),
                        flights
                )
        );

        System.out.println("Contains segments with arrival before department: " +
                FlightFilter.applyRule(
                        FlightRules.anySegment(
                                SegmentRules.durationLesserThan(0)
                        ),
                        flights
                )
        );

        FlightRule totalGroundTimeMoreThanTwoHours = f -> f.getGaps().stream().reduce(Long::sum).orElse(0L) > 2 * 60;
        System.out.println("More than two hours ground time: " +
                FlightFilter.applyRule(
                        totalGroundTimeMoreThanTwoHours,
                        flights
                )
        );

    }

}
