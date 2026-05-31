<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="login.form.title" /></title>
</head>

<body>
<h3>Login Page</h3>

<c:if test="${loginRequired}">
<p><spring:message code="login.required" /></p>
</c:if>

<c:url value="/loginProcess.do" var="loginProcessAction" />

<form:form modelAttribute="login" action="${loginProcessAction}" method="post">
<form:errors />

<!-- TODO [Step 1-2-6] SpringMessage 사용 -->
<table>
	<tr>
		<td><label for="id"><spring:message code="login.form.id" /></label></td>
		<td><form:input id="id" path="id" /></td>
		<td><form:errors path="id" /></td>
	</tr>
	<tr>
		<td><label for="password"><spring:message code="login.form.password" /></label></td>
		<td><form:password id="password" path="password" /></td>
		<td><form:errors path="password" /></td>
	</tr>
	<tr>
		<td><label for="loginType"><spring:message code="login.form.type" /></label></td>
		<td>
			<form:select path="loginType">
				<option value="">---선택하세요---</option>
				<form:options items="${loginTypes}" itemValue="code" itemLabel="value" />
			</form:select>
		</td>
		<td><form:errors path="loginType" /></td>
	</tr>
	<tr>
		<td colspan="3">
			<input type="submit" value="<spring:message code='login.form.submit' />">
		</td>
	</tr>
</table>

</form:form>

</body>
</html>
