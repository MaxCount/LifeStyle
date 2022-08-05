package com.project.lifestyle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesRequest {
    private int cloth;
    private int leisure;
    private int technique;
    private int transport;
    private int medicine;
    private int food;
    private int real_estate;
    private int education;
    private int cryptocurrency;
    private int other;
    private Instant date;

    private String username;

}
