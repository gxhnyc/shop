<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- 调用布局，内容将插入布局的content区域 --%>
<t:layout title="首页">
 	<div>
 	<c:choose>
 		<c:when test="${o_username !=null||o_username!=''}">
 		【${o_username }】
 		</c:when>
 		<c:otherwise>游客</c:otherwise>
 	</c:choose>
    	您好，欢迎来到我们的首页！
  	</div>  

</t:layout>

