package egovframework.lab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

	private String viewName = "hello/helloworld";

	private String getViewName() {
		return this.viewName;
	}

	/*
	 * TODO [Step 1-1-2] @RequestMapping - 요청 URL과 View 연결하기
	 * 반환은 String 이고 getViewName() 메소드를 이용한다.
	 */


}
