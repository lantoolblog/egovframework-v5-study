<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="login.form.title" /></title>
</head>
<body>
<spring:message code="login.success.memberInfo" />

<table>
	<tr>
		<td><label for="id"><spring:message code="login.form.id" /></label></label></td>
		<td><input type="text" value="<c:out value='${login.id}' />"></td>
	</tr>
	<tr>
		<td><label for="password"><spring:message code="login.form.password" /></label></td>
		<td><input type="text" value="<c:out value='${login.password}' />"></td>
	</tr>
	<tr>
		<td><label for="loginType"><spring:message code="login.form.type" /></label></td>
		<td><input type="text" value="<c:out value='${login.loginType}' />"></td>
	</tr>
	<tr>
		<td colspan="2"><a href="logout.do"><spring:message code="login.form.logout" /></a></td>
	</tr>
	<tr>
		<td colspan="2"><a href="loginProcess.do"><spring:message code="login.form.main" /></a></td>
	</tr>
</table>

</body>
</html>
