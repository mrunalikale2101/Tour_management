package com.tourmanagement.Shared.Types;

public enum EnumStatusBookedTour {
    NOSTART("Chưa diễn ra"),
    STARTED("Đang diễn ra"),
    FINISHED("Đã kết thúc");

    private final String status;

    EnumStatusBookedTour(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
