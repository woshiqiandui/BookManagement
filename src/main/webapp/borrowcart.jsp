<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,entities.Book,java.util.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg" %>
<%--如果有信息传递过来就先处理，后跳转到borrowcart.teg进行下一步处理  --%>
<teg:if test="${empty readerid}">
	<teg:redirect url="default.jsp"></teg:redirect>
</teg:if>
<teg:if test="${!empty err_msg}">
		<script>alert('${err_msg}');window.location.href='borrowcart.teg'</script>
</teg:if>
 

 
<!DOCTYPE>
<html>
<head>
<title>borrowcart</title>
<%@ include file="head.jsp" %>
</head>
<body>
<div class="main">
<teg:if test="${!empty books}">
<%--先获得数据 --%>
<teg:set  var="book_fisrt" value="${books[0]}"/>
<form>
<table class="simpletable">
<tr>
<teg:forEach  items="${book_fisrt.columns_CN}" var="col">
<th>
${col}
</th>
</teg:forEach>
<th><%="操作"%></th> 
</tr>
<tr>
<teg:forEach items="${books}" var="book">
<tr>
<teg:forEach items="${book.info}" var="value" varStatus="varStatus">
<td>
${value}
</td>
</teg:forEach>
<td>
<a href='javascript:' onclick="comfirm_borrow_alert('${book.id}','${book.name}',${book.rent})">借阅</a>
</td>
</tr>
</teg:forEach>
</tr>
</table>
</form>
</teg:if>
<teg:import url="tail.jsp"></teg:import>
</div>
</body>