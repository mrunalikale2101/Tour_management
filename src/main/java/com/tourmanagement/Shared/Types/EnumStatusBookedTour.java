package com.tourmanagement.Shared.Types;

public enum EnumStatusBookedTour {
    PENDING("pending"),
    CONFIRMED("confirmed");

    private final String value;

    EnumStatusBookedTour(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumStatusBookedTour fromString(String status) {
        for (EnumStatusBookedTour enumStatus : EnumStatusBookedTour.values()) {
            if (enumStatus.value.equalsIgnoreCase(status.toLowerCase())) {
                return enumStatus;
            }
        }
        throw new IllegalArgumentException("No constant with the specified status found");
    }
}
