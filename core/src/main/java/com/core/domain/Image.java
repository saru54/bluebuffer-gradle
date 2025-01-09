package com.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Image {
    private String id;
    private String url;

    public Image(String url) {
        this.url = url;
    }
}
