package com.barointern.yujin.domain.model;

import lombok.Getter;

@Getter
public enum RoleType {

  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN");

  private final String authorityName;

  RoleType(String authorityName) {
    this.authorityName = authorityName;
  }

}
