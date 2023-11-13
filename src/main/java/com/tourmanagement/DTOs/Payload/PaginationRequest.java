package com.tourmanagement.DTOs.Payload;

import lombok.Data;

@Data
public class PaginationRequest {
    private int page;
    private int itemsPerPage;
}
