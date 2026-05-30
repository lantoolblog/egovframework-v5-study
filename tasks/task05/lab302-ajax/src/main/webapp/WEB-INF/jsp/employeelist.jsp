<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="easaycompany.employeelist.title"/></title>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/easycompany.css'/>" />
<!-- jQuery -->
<link rel="stylesheet" href="<c:url value='/css/jqueryui.css'/>"/>
<script src="<c:url value='/js/jquery.js'/>"></script>
<script src="<c:url value='/js/jqueryui.js'/>"></script>
<script src="<c:url value='/js/select_load.js'/>"></script>
<script type="text/javascript">
$(document).ready(function() {
	//검색어에 대한 jquery ajax 자동완성 구현하기(jquery autocomplete)
	$("#searchName").autocomplete({
		source: function(request, response) {
			$.ajax({
				url: "<c:url value='/autoComplete.do'/>",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data: { keyword : encodeURIComponent(request.term) },	 
				dataType: "json",
				success: function(returnData, status) {
					response(returnData.resultList);
				}
			});
		},
		minLength : 1,
		select: function(event, ui) {
			$("#searchName").val(this.value);
		}
	});

	// TODO [Step 2-2-1] 부서번호에 대한 jquery autoSelected 기능 구현하기(jquery autoselected)


});
</script>
</head>
<body>

<h2>AJAX : AutoComplete 기능</h2>
<div id="searchform">
<form:form modelAttribute="searchCriteria" action="employeelist.do">
<table width="80%" border="0">
	<tr>
		<td>이름 : <form:input path="searchName"/></td>
	</tr>
</table>
</form:form>
</div>

<br/>
<br/>
<br/>

<h2>AJAX : AutoSelected 기능</h2>
<div id="editform">
<form:form modelAttribute="employee">
<table>
	<tr>
		<td>부서번호 : </td>
		<td>
			<form:select path="superdeptid">
				<option value="">상위부서를 선택하세요.</option>
				<form:options items="${deptInfoOneDepthCategory}" />
			</form:select>
		</td>
		<td>
			<form:select path="departmentid">
				<option value="">근무부서를 선택하세요.</option>
				<form:options items="${deptInfoTwoDepthCategory}" />
			</form:select>
		</td>
	</tr>
</table>
</form:form>
</div>

</body>
</html>