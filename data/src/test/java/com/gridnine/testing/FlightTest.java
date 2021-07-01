package com.gridnine.testing;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class FlightTest {

    @Test
    public void calculateGapsTest() {
        List<Segment> segs = List.of(
                new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(3)),
                new Segment(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10))
        );
        Flight flight = new Flight(segs);

        Assert.assertEquals(1, flight.getGaps().size());
        Assert.assertEquals(2 * 60, (long) flight.getGaps().get(0));
    }

}
