package com.gridnine.testing;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class SegmentRulesTest {

    private final LocalDateTime d = LocalDateTime.now();

    private final List<Segment> segments = List.of(
            new Segment(d, d.plusHours(1)),
            new Segment(d.plusHours(2), d.plusHours(5)),
            new Segment(d.plusHours(7), d.plusHours(10))
    );

    @Test
    public void departureDateIsAfterTest() {
        Assert.assertFalse(SegmentRules.departureDateIsAfter(d).test(segments.get(0)));
        Assert.assertTrue(SegmentRules.departureDateIsAfter(d).test(segments.get(1)));
        Assert.assertTrue(SegmentRules.departureDateIsAfter(d).test(segments.get(2)));
    }

    @Test
    public void departureDateIsBeforeTest() {
        Assert.assertFalse(SegmentRules.departureDateIsBefore(d).test(segments.get(0)));
        Assert.assertFalse(SegmentRules.departureDateIsBefore(d).test(segments.get(1)));
        Assert.assertTrue(SegmentRules.departureDateIsBefore(d.plusHours(8)).test(segments.get(2)));
    }

    @Test
    public void arrivalDateIsAfterTest() {
        Assert.assertTrue(SegmentRules.arrivalDateIsAfter(d).test(segments.get(0)));
        Assert.assertFalse(SegmentRules.arrivalDateIsAfter(d.plusHours(5)).test(segments.get(1)));
        Assert.assertFalse(SegmentRules.arrivalDateIsAfter(d.plusHours(6)).test(segments.get(1)));
    }

    @Test
    public void arrivalDateIsBeforeTest() {
        Assert.assertFalse(SegmentRules.arrivalDateIsBefore(d).test(segments.get(0)));
        Assert.assertFalse(SegmentRules.arrivalDateIsBefore(d.plusHours(1)).test(segments.get(0)));
        Assert.assertTrue(SegmentRules.arrivalDateIsBefore(d.plusHours(2)).test(segments.get(0)));
    }

    @Test
    public void durationGreaterThanTest() {
        Segment s = segments.get(0);
        Assert.assertTrue(SegmentRules.durationGreaterThan(50).test(s));
        Assert.assertFalse(SegmentRules.durationGreaterThan(61).test(s));
    }

    @Test
    public void durationLesserThanTest() {
        Segment s = segments.get(0);
        Assert.assertFalse(SegmentRules.durationLesserThan(50).test(s));
        Assert.assertTrue(SegmentRules.durationLesserThan(61).test(s));
    }

    @Test
    public void durationEqualsToTest() {
        Segment s = segments.get(0);
        Assert.assertFalse(SegmentRules.durationEqualsTo(50).test(s));
        Assert.assertTrue(SegmentRules.durationEqualsTo(60).test(s));
    }

    @Test
    public void andTest() {
        SegmentRule rule = SegmentRules
                .and(
                        SegmentRules.durationLesserThan(10 * 60),
                        SegmentRules.durationGreaterThan(2 * 60)
                );
        Assert.assertFalse(rule.test(segments.get(0)));
        Assert.assertTrue(rule.test(segments.get(1)));
    }

    @Test
    public void orTest() {
        SegmentRule rule = SegmentRules
                .or(
                        SegmentRules.departureDateIsAfter(d.plusHours(5)),
                        SegmentRules.durationGreaterThan(2 * 60)
                );
        Assert.assertFalse(rule.test(segments.get(0)));
        Assert.assertTrue(rule.test(segments.get(1)));
        Assert.assertTrue(rule.test(segments.get(2)));
    }

    @Test
    public void complexAndOrTest() {
        SegmentRule rule =
                SegmentRules.and(
                        SegmentRules.departureDateIsBefore(d.plusHours(6)),
                        SegmentRules.or(
                                SegmentRules.durationGreaterThan(3 * 60),
                                SegmentRules.durationLesserThan(2 * 60)
                        )
                );

        boolean[] results = new boolean[segments.size()];
        for (int i = 0; i < results.length; ++i) {
            results[i] = rule.test(segments.get(i));
        }

        Assert.assertArrayEquals(results, new boolean[] {true, false, false});
    }

}
