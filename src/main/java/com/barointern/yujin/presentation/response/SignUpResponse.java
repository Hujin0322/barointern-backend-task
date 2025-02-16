package com.barointern.yujin.presentation.response;

import com.barointern.yujin.domain.model.RoleType;
import java.util.List;

public record SignUpResponse(

    String username,
    String nickname,
    List<Authority> authorities

) {

  public record Authority(String authorityName) {}

  public static SignUpResponse from(String username, String nickname, List<RoleType> roles) {
    List<Authority> authorities = roles.stream()
        .map(role -> new Authority(role.getAuthorityName()))
        .toList();

    return new SignUpResponse(username, nickname, authorities);
  }

}
