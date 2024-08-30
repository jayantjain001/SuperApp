package com.dev.superapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter   // necessary for deserialisation otherwise errors are there
@Builder
public class Item {
    private int id;
    private String name;
}
