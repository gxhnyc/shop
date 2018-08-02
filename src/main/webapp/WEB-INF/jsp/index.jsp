<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>   
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- 调用布局，内容将插入布局的content区域 --%>
<t:layout title="首页">
 	<div>
 	<c:choose>
 		<c:when test="${c_username !=null}">
 		【${c_username }】您好，欢迎来到我们的首页！
 		</c:when>
 		<c:otherwise>
 			游客您好，欢迎来到我们的首页！<br> 			
 		</c:otherwise>
 	</c:choose>
    	
  	</div> 
  	<div>
		  <form action="" method="get">
		    <div>
		      <label for="cp_brand">品牌</label>
		      <select name="cp_brand">
		        <option value="">--请选择品牌--</option>
		        <option value="apple">apple</option>
		        <option value="锤子">锤子</option>
		        <option value="HUWEI">华为</option>
		      </select>
		    </div>
		    
		    <div>
		      <label for="cp_os">操作系统</label>
		      <select name="cp_os">
		        <option value="">--请选择操作系统--</option>
		        <option value="Android">Android</option>
		        <option value="ios">ios</option>
		        <option value="Windows Phone">Windows Phone</option>
		      </select>      
		    </div>
		    
		    <div>
		      <label for="cp_cpu">CPU品牌</label>
		      <select name="cp_cpu">
		        <option value="">--请选择CPU品牌--</option>
		        <option value="Qualcomm10">Qualcomm10</option>
		        <option value="Qualcomm9">Qualcomm9</option>
		        <option value="联发科">联发科</option>
		      </select>
		    </div>    
		    
		    <div>
		      <label for="cp_cpu_cores">CPU核心数</label>
		      <input name="cp_cpu_cores" type="number" min="1" max="16" />
		    </div>
		    
		    <div>
		      <label for="cp_ram">运行内存</label>
		      <input name="cp_ram" type="number" min="1" max="16" placeholder="GB" />
		    </div>
		    
		    <div>
		      <label for="cp_storage">机身存储</label>
		      <input name="cp_storage" type="number" min="16" max="256" placeholder="GB" />
		    </div>    
		    
		    <div>
		      <button type="submit">搜索</button>
		    </div>
  	</form>
  	</div>
  	<hr>
  	<div>
  		<table>
	    <tr>
	      <th>品　牌　</th>
	      <th>型　号　　　</th>
	      <th>操作系统　</th>
	      <th>CPU　　　　　　　</th>
	      <th>RAM　　</th>
	      <th>存　储　</th>
	      <th>颜　色　</th>
	      <th>价　格　</th>
	      
	    </tr>
	    <c:forEach items="${cellphones}" var="cellphone">
	      <tr>
	        <td>${cellphone.cp_brand}</td>
	        <td>${cellphone.cp_model}</td>
	        <td>${cellphone.cp_os}</td>
	        <td>${cellphone.cp_cpu} ${cellphone.cp_cpu_cores}</td>
	        <td>${cellphone.cp_ram}GB</td>
	        <td>${cellphone.cp_storage}GB</td>
	        <td>${cellphone.cp_color}</td>
	        <td>￥：${cellphone.cp_price/100}</td>
	        <td><a href="${contextPath }/details/${ cellphone.cp_id}">详情</a></td>
	      </tr>
	    </c:forEach>
	  </table>
  	
  	</div> 

</t:layout>

