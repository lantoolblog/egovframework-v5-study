package egovframework.lab.model;

import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

import jakarta.validation.constraints.Pattern;

public class LoginCommand {

  // TODO [Step 1-2-3] 커맨드 객체, 유효성검증 완성하기
  @EgovNullCheck(message = "{required.login.id}")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{invalid.message}")
  private String id;

  @EgovNullCheck(message = "{required.login.password}")
  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{invalid.message}")
  private String password;

  @EgovNullCheck(message = "{required.login.loginType}")
  private String loginType;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLoginType() {
    return loginType;
  }

  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }
}
