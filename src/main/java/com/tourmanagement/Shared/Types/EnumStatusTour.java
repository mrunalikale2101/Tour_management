package com.tourmanagement.Shared.Types;

public enum EnumStatusTour {
    NOSTART("Chưa diễn ra"),
    STARTED("Đang diễn ra"),
    FINISHED("Đã kết thúc");

    private final String status;

    EnumStatusTour(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
