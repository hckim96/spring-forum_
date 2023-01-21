package com.example.jpamvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteDto {

    private Long authorId;

    @NotBlank
    private String title;

    @NotBlank
    private String body;
}
