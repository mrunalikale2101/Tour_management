package com.tourmanagement.Shared.Types;

public enum EnumTransportModeTour {
    BUS("Xe bus"),
    PLANE("Máy bay");

    private final String transportation;

    EnumTransportModeTour(String transportation) {
        this.transportation = transportation;
    }

    public String getTransportation() {
        return transportation;
    }
}
