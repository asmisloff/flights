package com.gridnine.testing;

class GapRules {

    static GapRule greaterThan(long minutes) {
        return g -> g.compareTo(minutes) > 0;
    }

    static GapRule lesserThan(long minutes) {
        return g -> g.compareTo(minutes) < 0;
    }

    static GapRule equalsTo(long minutes) {
        return g -> g.compareTo(minutes) == 0;
    }

    static GapRule and(GapRule... rules) {
        return g -> Rule.and(rules).test(g);
    }

    static GapRule or(GapRule... rules) {
        return g -> Rule.or(rules).test(g);
    }

}
