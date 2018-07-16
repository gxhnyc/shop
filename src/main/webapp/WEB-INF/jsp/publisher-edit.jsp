<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- 调用布局，将内容插入布局的content区域 -->
<t:layout title="出版社编辑">

<fieldset>
		<legend>添加/修改publisher</legend>
		<br><br><br>
		<!--action为空意味着会提交到当前表单页面的路径-->
		<form:form action="" method="post" commandName="publisher">
		<div>
		<!--<label for="publisher_name"> 当鼠标点击lable标签时，for属性对应的标签就会获得焦点  -->
		
		<div>
			<label for="publisher_id">出版社id：</label>
			<form:input type="text" path="publisher_id" id="publisher_id"/>
			<form:errors path="publisher_id" cssClass="add-error"></form:errors>
		</div>	
			<label for="publisher_name">出版社名：</label>
			<form:input type="text" path="publisher_name" id="publisher_name"/>
			<form:errors path="publisher_name" cssClass="add-error"></form:errors>
		</div>
		<div>
			<label for="publisher_brief">出版社简介：</label><br>
			<form:textarea path="publisher_brief" id="publisher_brief"/>
			<form:errors path="publisher_brief" cssClass="add-error"></form:errors>
		</div>	
			<button type="submit">添加</button>
		</form:form>
	
	</fieldset>

</t:layout>

