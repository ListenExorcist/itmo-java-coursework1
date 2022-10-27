package ru.itmo.coursework1;

import java.time.LocalTime;

public enum SubscriptionType {
    ONE_TIME(LocalTime.parse("08:00:00"), LocalTime.parse("22:00:00"), new Zone[] {Zone.GYM, Zone.SWIMMING_POOL}),
    DAYTIME(LocalTime.parse("08:00:00"), LocalTime.parse("16:00:00"), new Zone[] {Zone.GYM, Zone.GROUP_SESSION}),
    FULL(LocalTime.parse("08:00:00"), LocalTime.parse("22:00:00"), new Zone[] {Zone.GYM, Zone.SWIMMING_POOL, Zone.GROUP_SESSION});

    private final LocalTime AVAILABLE_TIME_BEGIN;
    private final LocalTime AVAILABLE_TIME_END;
    private final Zone[] AVAILABLE_ZONES;

    SubscriptionType(LocalTime AVAILABLE_TIME_BEGIN, LocalTime AVAILABLE_TIME_END, Zone[] AVAILABLE_ZONES) {
        this.AVAILABLE_TIME_BEGIN = AVAILABLE_TIME_BEGIN;
        this.AVAILABLE_TIME_END = AVAILABLE_TIME_END;
        this.AVAILABLE_ZONES = AVAILABLE_ZONES;
    }

    public LocalTime getAvailableTimeBegin() {
        return AVAILABLE_TIME_BEGIN;
    }

    public LocalTime getAvailableTimeEnd() {
        return AVAILABLE_TIME_END;
    }

    public Zone[] getAvailableZones() {
        return AVAILABLE_ZONES;
    }
}
