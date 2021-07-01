package com.gridnine.testing;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class GapRulesTest {

    private final LocalDateTime d = LocalDateTime.now();

    private final List<Segment> segments = List.of(
            new Segment(d, d.plusHours(1)),
            new Segment(d.plusHours(2), d.plusHours(5)),
            new Segment(d.plusHours(7), d.plusHours(10))
    );

    private final Flight flight = new Flight(segments);

    @Test
    public void greaterThanTest() {
        List<Long> gaps = flight.getGaps();
        Assert.assertTrue(GapRules.greaterThan(30).test(gaps.get(0)));
        Assert.assertFalse(GapRules.greaterThan(3 * 60).test(gaps.get(1)));
    }

    @Test
    public void lesserThanTest() {
        List<Long> gaps = flight.getGaps();
        Assert.assertFalse(GapRules.lesserThan(30).test(gaps.get(0)));
        Assert.assertTrue(GapRules.lesserThan(3 * 60).test(gaps.get(1)));
    }

    @Test
    public void equalsToTest() {
        List<Long> gaps = flight.getGaps();
        Assert.assertFalse(GapRules.equalsTo(30).test(gaps.get(0)));
        Assert.assertTrue(GapRules.equalsTo(2 * 60).test(gaps.get(1)));
    }

    @Test
    public void andTest() {
        GapRule rule1 = GapRules
                .and(
                        GapRules.greaterThan(30),
                        GapRules.lesserThan(2 * 60)
                );
        Assert.assertTrue(rule1.test(flight.getGaps().get(0)));
        Assert.assertFalse(rule1.test(flight.getGaps().get(1)));
    }

    @Test
    public void orTest() {
        GapRule rule1 = GapRules
                .or(
                        GapRules.greaterThan(30),
                        GapRules.lesserThan(2 * 60)
                );
        Assert.assertTrue(rule1.test(flight.getGaps().get(0)));
        Assert.assertTrue(rule1.test(flight.getGaps().get(1)));
    }

}
