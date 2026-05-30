<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>eGovFrame LAB-AJAX</title>
<style type="text/css">
	#menu a img.menuon {
	    display: none;
	}
	#menu a:hover img.menuoff {
	    display: inline;
	}
</style>
</head>
<body>
<ul id="menu">
	<li>Ajax 
		<ul>
			<li><a href="autocomplete.jsp">basic</a></li>      

			<li><a href="login.do">AutoComplete , AutoSelected</a></li>

			<!-- <li><a href="employeelist.do">Interceptor 보안적용 (로긴없이 /lab/employeelist.do 로 접근시) </a></li> -->
		</ul>
	</li>
</ul>

</body>
</html>