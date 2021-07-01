package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

class FlightFilter {

    static List<Flight> applyRule(FlightRule rule, List<Flight> flights) {
        return flights.stream().filter(rule).collect(Collectors.toUnmodifiableList());
    }

}
