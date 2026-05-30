package egovframework.lab.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

import egovframework.lab.model.Address;
import egovframework.lab.model.Code;
import egovframework.lab.model.MemberInfo;

@Controller
public class MemberController {

	private String formView = "member/memberForm";
	private String successView = "member/memberSuccess";

	@ModelAttribute("jobCodes")
	protected List<Code> getJobCodes() {
		List<Code> jobCodes = new ArrayList<Code>();
		jobCodes.add(new Code("1", "하나"));
		jobCodes.add(new Code("2", "UI둘"));
		jobCodes.add(new Code("3", "셋"));
		jobCodes.add(new Code("3", "넷"));
		return jobCodes;
	}

	@ModelAttribute("favoritesOsNames")
	protected String[] getFavoritesOsNames() {
		String[] favoritesOsNames = { "WIN2000", "WINXP", "OK", "SS", "A" };
		return favoritesOsNames;
	}

	@ModelAttribute("tools")
	protected String[] getTools() {
		String[] tools = { "Eclipse", "IntelliJ", "NetBeans" };
		return tools;
	}

	@ModelAttribute("memberInfo")
	protected Object formBackingObject() {
		MemberInfo member = new MemberInfo();
		member.setAddress(new Address());
		return member;
	}

	@RequestMapping(value = "/member.do", method = RequestMethod.GET)
	protected String defaultMember() {
		return formView;
	}

	@RequestMapping(value = "/member.do", method = RequestMethod.POST)
	protected String regist(@Valid @ModelAttribute("memberInfo") MemberInfo command, BindingResult errors)
			throws Exception {

		if (errors.hasErrors()) {
			return formView;
		}
		checkDuplicateId(command.getId(), errors);
		if (errors.hasErrors()) {
			return formView;
		}
		return successView;
	}

	private void checkDuplicateId(String userId, BindingResult errors) {
		if ("egovframe".equals(userId)) {
			errors.rejectValue("id", "duplicate");
		}
	}

}
