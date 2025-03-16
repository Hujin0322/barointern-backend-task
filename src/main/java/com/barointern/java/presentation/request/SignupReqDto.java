package com.barointern.java.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignupReqDto(

    @NotBlank(message = "아이디를 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 ]*$", message = "아이디는 첫 글자가 영문이어야 하며 영문자와 숫자만 허용됩니다.")
    @Size(min = 5, max = 8, message = "아이디는 5~8자 이내로 작성해야 합니다.")
    String username,

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$",
        message = "비밀번호는 최소 한 개의 영문자, 숫자, 특수문자가 포함되어야 하며 8~16자여야 합니다.")
    String password,

    @NotBlank(message = "닉네임을 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "닉네임은 영문자, 숫자만 허용되며 특수문자는 포함될 수 없습니다.")
    @Size(min = 2, max = 20, message = "닉네임은 2~20자 사이여야 합니다.")
    String nickname

) {

}
