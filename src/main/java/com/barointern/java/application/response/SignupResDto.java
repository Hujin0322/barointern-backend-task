package com.barointern.java.application.response;

import com.barointern.java.domain.model.RoleType;
import com.barointern.java.domain.model.User;

public record SignupResDto(

    String username,
    String nickname,
    RoleType role

) {

  public static SignupResDto from(User user) {
    return new SignupResDto(user.getUsername(), user.getNickname(), user.getRole());
  }
}
