package com.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResult<T> {
    private Long currentPage;
    private Long count;
    private List<T> records;

    public PageResult() {

    }
}
