package com.barointern.yujin.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequest(

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 5, max = 30, message = "이름은 최소 5자 이상, 30자 이하로 설정 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z ]{5,30}$", message = "이름은 영문, 공백만 허용됩니다.")
    String username,

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 100, message = "비밀번호는 최소 8자 이상, 100자 이하로 설정 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9@$!%*?&]{8,100}$", message = "비밀번호는 대문자, 소문자, 숫자, 특수문자만 허용됩니다.")
    String password,

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 최소 2자 이상, 20자 이하로 설정 가능합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9_-]{2,20}$", message = "닉네임은 한글, 영문, 숫자, '_', '-'만 허용됩니다.")
    String nickname

) {

}
