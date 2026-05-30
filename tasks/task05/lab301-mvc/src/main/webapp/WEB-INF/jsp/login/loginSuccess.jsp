<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="login.form.title" /></title>
</head>
<body>
<p><spring:message code="login.success.message" arguments="${login.id}" /></p>
<br/>
<a href="memberInfo.do"><spring:message code="login.success.memberInfo" /></a>
</body>
</html>
