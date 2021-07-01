package com.gridnine.testing;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class FlightRulesTest {

    private final LocalDateTime d = LocalDateTime.now();

    private final List<Segment> segments = List.of(
            new Segment(d, d.plusHours(1)),
            new Segment(d.plusHours(2), d.plusHours(5)),
            new Segment(d.plusHours(7), d.plusHours(10))
    );

    private final Flight flight = new Flight(segments);

    @Test
    public void numberOfSegmentsGreaterThanTest() {
        Assert.assertTrue(FlightRules.numberOfSegmentsGreaterThan(2).test(flight));
        Assert.assertFalse(FlightRules.numberOfSegmentsGreaterThan(3).test(flight));
    }

    @Test
    public void numberOfSegmentsLesserThanTest() {
        Assert.assertTrue(FlightRules.numberOfSegmentsLesserThan(4).test(flight));
        Assert.assertFalse(FlightRules.numberOfSegmentsLesserThan(3).test(flight));
    }

    @Test
    public void numberOfSegmentsEqualsToTest() {
        Assert.assertFalse(FlightRules.numberOfSegmentsEqualsTo(2).test(flight));
        Assert.assertTrue(FlightRules.numberOfSegmentsEqualsTo(3).test(flight));
    }

    @Test
    public void anySegmentTest() {
        Assert.assertTrue(FlightRules
                .anySegment(SegmentRules.durationGreaterThan(2 * 60))
                .test(flight));
        Assert.assertFalse(FlightRules
                .anySegment(SegmentRules.durationLesserThan(60))
                .test(flight));
    }

    @Test
    public void allSegmentsTest() {
        Assert.assertTrue(FlightRules
                .allSegments(SegmentRules.durationGreaterThan(59))
                .test(flight));
        Assert.assertFalse(FlightRules
                .allSegments(SegmentRules.durationLesserThan(120))
                .test(flight));
    }

    @Test public void anyGapTest() {
        Assert.assertTrue(FlightRules.anyGap(
                GapRules.greaterThan((long) (1.5 * 60)))
                .test(flight)
        );
        Assert.assertFalse(FlightRules.anyGap(
                GapRules.lesserThan(60))
                .test(flight)
        );
    }

    @Test public void allGapsTest() {
        Assert.assertTrue(FlightRules.allGaps(
                GapRules.greaterThan(50))
                .test(flight)
        );
        Assert.assertFalse(FlightRules.allGaps(
                GapRules.lesserThan((long) (1.5 * 60)))
                .test(flight)
        );
    }

    @Test
    public void andTest() {
        var rule1 = FlightRules
                .and(
                        FlightRules.anySegment(SegmentRules.durationGreaterThan(2 * 60)),
                        FlightRules.numberOfSegmentsEqualsTo(3)
                );
        Assert.assertTrue(rule1.test(flight));

        var rule2 = FlightRules
                .and(
                        FlightRules.anySegment(SegmentRules.durationGreaterThan(2 * 60)),
                        FlightRules.numberOfSegmentsEqualsTo(3),
                        FlightRules.allSegments(SegmentRules.durationLesserThan(120))
                );
        Assert.assertFalse(rule2.test(flight));
    }

    @Test
    public void orTest() {
        var rule1 = FlightRules
                .or(
                        FlightRules.anySegment(SegmentRules.durationGreaterThan(2 * 60)),
                        FlightRules.numberOfSegmentsEqualsTo(5)
                );
        Assert.assertTrue(rule1.test(flight));

        var rule2 = FlightRules
                .or(
                        FlightRules.anySegment(SegmentRules.durationGreaterThan(2 * 60)),
                        FlightRules.numberOfSegmentsEqualsTo(3),
                        FlightRules.allSegments(SegmentRules.durationLesserThan(120))
                );
        Assert.assertTrue(rule2.test(flight));

        var rule3 = FlightRules
                .or(
                        FlightRules.anySegment(SegmentRules.durationGreaterThan(5 * 60)),
                        FlightRules.numberOfSegmentsEqualsTo(1),
                        FlightRules.allSegments(SegmentRules.durationLesserThan(120))
                );
        Assert.assertFalse(rule3.test(flight));
    }

    @Test
    public void complexAndOrTest() {
        var rule1 = FlightRules
                .and(
                        FlightRules.or(
                                FlightRules.anySegment(SegmentRules.durationGreaterThan(2 * 60)),
                                FlightRules.numberOfSegmentsEqualsTo(3),
                                FlightRules.allSegments(SegmentRules.durationLesserThan(120))
                        ),
                        FlightRules.or(
                                FlightRules.anySegment(SegmentRules.durationGreaterThan(2 * 60)),
                                FlightRules.numberOfSegmentsEqualsTo(3),
                                FlightRules.allSegments(SegmentRules.durationLesserThan(120))
                        ),
                        FlightRules.anyGap(
                                GapRules.lesserThan(2 * 60)
                        )
                );
        Assert.assertTrue(rule1.test(flight));

        var rule2 = FlightRules
                .and(
                        rule1,
                        FlightRules.or(
                                FlightRules.anySegment(SegmentRules.durationGreaterThan(5 * 60)),
                                FlightRules.numberOfSegmentsEqualsTo(1),
                                FlightRules.allSegments(SegmentRules.durationLesserThan(120))
                        ),
                        FlightRules.anyGap(
                                GapRules.equalsTo(2 * 60)
                        )
                );
        Assert.assertFalse(rule2.test(flight));
    }

}
