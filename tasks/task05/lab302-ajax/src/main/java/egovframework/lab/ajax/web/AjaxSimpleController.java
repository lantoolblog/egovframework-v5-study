package egovframework.lab.ajax.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AjaxSimpleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxSimpleController.class);

	private String[] keywords = { "Kim GilDong", "Kim NaRi", "Kim GilSu", "Kim Angel" };

	@SuppressWarnings("unchecked")
	private List<String> search(String keyword) {
		if (ObjectUtils.isEmpty(keyword))
			return java.util.Collections.EMPTY_LIST;

		keyword = keyword.toUpperCase();

		List<String> result = new ArrayList<>();

		for (int i = 0; i < keywords.length; i++) {
			if (((String) keywords[i]).startsWith(keyword)) {
				result.add(keywords[i]);
			}
		}

		return result;
	}

	//TODO [Step 2-1-1] simpleAjax 메소드 완성하기 


}
