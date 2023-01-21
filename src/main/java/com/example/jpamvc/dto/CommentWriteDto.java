package com.example.jpamvc.dto;

import com.example.jpamvc.domain.Member;
import com.example.jpamvc.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentWriteDto {
    private Long postId;
    private Long authorId;
    private String body;
}
