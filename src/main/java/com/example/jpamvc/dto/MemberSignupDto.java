package com.example.jpamvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupDto {
    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    @Length(min = 4, max = 12)
    private String loginId;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Length(min = 4, max = 12)
    private String password;
}
