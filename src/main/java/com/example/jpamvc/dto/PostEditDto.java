package com.example.jpamvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PostEditDto {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
