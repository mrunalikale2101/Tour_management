package com.tourmanagement.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationRespDTO<T> {
    private Long total;
    private int page;
    private int itemsPerPage;
    private List<T> data;
}
