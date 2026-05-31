package egovframework.lab.web;

import egovframework.lab.com.Authenticator;
import egovframework.lab.model.LoginCommand;
import egovframework.lab.model.LoginType;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import javax.naming.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

  /** 세션에 보관할 로그인 객체의 속성 이름(form 의 modelAttribute 및 JSP 와 동일) */
  private static final String SESSION_LOGIN_ATTR = "login";

  @Resource(name = "LoginAuthenticator")
  private Authenticator authenticator;

  private String formView = "login/loginForm";
  private String successView = "login/loginSuccess";

  /*
   * TODO [Step 1-2-4] @RequestMapping - method별 mapping 전략
   * 웹을 통해 들어오는 url 은 loginProcess.do 이며 Get/Post 형식으로 넘어온다.
   * 두 가지를 다 받는 메소드를 만들어보자.
   */
  @RequestMapping(value = "/loginProcess.do", method = RequestMethod.GET)
  public String loginFormSetUp(Model model) {
    model.addAttribute("login", new LoginCommand());
    return formView;
  }

  @RequestMapping(value = "/loginProcess.do", method = RequestMethod.POST)
  public String loginProcess(
      @Valid @ModelAttribute("login") LoginCommand loginCommand,
      BindingResult errors,
      HttpSession session) {
    if (errors.hasErrors()) {
      return formView;
    }
    try {
      authenticator.authenticate(loginCommand.getId(), loginCommand.getPassword());
    } catch (AuthenticationException ex) {
      errors.reject("invalidIdOrPassword", new Object[] {loginCommand.getId()}, null);
      return formView;
    }
    session.setAttribute(SESSION_LOGIN_ATTR, loginCommand);
    return successView;
  }

  /*
   * TODO [Step 1-2-5] @ModelAttribute - 모델의 초기화
   * ModelAttribute를 이용하여 loginTypes와 login 객체를 초기화 해주는 메소드를 만든다.
   */
  @ModelAttribute("loginTypes")
  protected List<LoginType> referenceData() throws Exception {
    List<LoginType> loginTypes = new ArrayList<LoginType>();
    loginTypes.add(new LoginType("A", "개인회원"));
    loginTypes.add(new LoginType("B", "기업회원"));
    loginTypes.add(new LoginType("C", "관리자"));
    return loginTypes;
  }

  @ModelAttribute("login")
  protected Object referenceData4login() throws Exception {
    return new LoginCommand();
  }

  @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
  public String logOut(HttpSession session) {
    session.removeAttribute(SESSION_LOGIN_ATTR);
    return "redirect:/loginProcess.do";
  }

  @RequestMapping(value = "/memberInfo.do")
  public ModelAndView memberInfo(HttpSession session, RedirectAttributes redirectAttributes) {
    LoginCommand login = (LoginCommand) session.getAttribute(SESSION_LOGIN_ATTR);
    if (login == null) {
      redirectAttributes.addFlashAttribute("loginRequired", Boolean.TRUE);
      return new ModelAndView("redirect:/loginProcess.do");
    }
    ModelAndView mav = new ModelAndView("login/memberInfo");
    mav.addObject(SESSION_LOGIN_ATTR, login);
    return mav;
  }
}
