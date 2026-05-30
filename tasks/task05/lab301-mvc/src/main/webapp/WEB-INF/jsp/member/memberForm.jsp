<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="member.form.title" /></title>
</head>
<body>
<form:form modelAttribute="memberInfo">
	<table>
		<tr>
			<td><form:label path="id"><spring:message code="member.form.id" /></form:label></td>
			<td><form:input path="id" /></td>
			<td><form:errors path="id" /></td>
		</tr>
		<tr>
			<td><form:label path="name"><spring:message code="member.form.name" /></form:label></td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<td><form:label path="address.address"><spring:message code="member.form.addr1" /></form:label></td>
			<td><form:input path="address.address" /></td>
			<td><form:errors path="address.address" /></td>
		</tr>
		<tr>
			<td><form:label path="address.addressDetail"><spring:message code="member.form.addr2" /></form:label></td>
			<td><form:input path="address.addressDetail" /></td>
			<td><form:errors path="address.addressDetail" /></td>
		</tr>
		<tr>
			<td><form:label path="jobCode"><spring:message code="member.form.job" /></form:label></td>
			<td><form:select path="jobCode">
				<option value="">--- 선택하세요 ---</option>
				<form:options items="${jobCodes}" itemLabel="label" itemValue="code" />
			</form:select></td>
			<td><form:errors path="jobCode" /></td>
		</tr>
		<tr>
			<td><form:label path="favorites"><spring:message code="member.form.os" /></form:label></td>
			<td><form:checkboxes items="${favoritesOsNames}" path="favorites" /></td>
			<td><form:errors path="favorites" /></td>
		</tr>
		<tr>
			<td><form:label path="tool"><spring:message code="member.form.ide" /></form:label></td>
			<td><form:radiobuttons items="${tools}" path="tool" /></td>
			<td><form:errors path="tool" /></td>
		</tr>
		<tr>
			<td><form:label path="etc"><spring:message code="member.form.etc" /></form:label></td>
			<td colspan="2"><form:textarea path="etc" cols="20" rows="3" /></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="<spring:message code="member.form.insert" />"></td>
		</tr>
	</table>
</form:form>
</body>
</html>
