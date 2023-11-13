package com.tourmanagement.Shared.Types;

public enum EnumStatusBookedTour {
    PENDING(false),
    CONFIRMED(true);

    private final boolean value;

    EnumStatusBookedTour(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
