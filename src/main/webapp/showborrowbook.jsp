<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg" %>
<teg:if test="${!empty err_msg}">
		<script>alert('${err_msg}');window.location.href='showborrowbook.teg';</script>
</teg:if>
<teg:if test="${empty readername}">
	<teg:redirect url="default.jsp"></teg:redirect>
	</teg:if>
<%@ page import="java.util.*,entities.*" %>
<!DOCTYPE>
<html>
<head>
<title>借书记录</title>
<%@ include file="head.jsp" %> 
</head>
<body onload="returnbook_alert('${success_msg}',${!empty success_msg})">
<div class="main">
<h1 style="text-align:center">借书记录</h1>
<teg:if  test="${!empty borrow_historys}">
<teg:set var="borrow_history_first" value="${borrow_historys[0]}" /> 
<form>
<table class="simpletable">
<tr>
<teg:forEach items="${borrow_history_first.columns_CN}" var="historycol">
<th>
${historycol}
</th>
</teg:forEach>
<th>
<%="操作"%>
</th>
</tr>

<teg:forEach items="${borrow_historys}" var="borrow_history">
<tr>
<teg:forEach items="${borrow_history.info}" var="value" varStatus="varStatus">
<td>
<teg:if test="${varStatus.index==5}">
   <teg:if test="${value==1}">
       <teg:out value="已归还"></teg:out>
       <teg:set var="isreturn" value="1"></teg:set>
   </teg:if>
   <teg:if test="${value==0}">
       <teg:out value="未归还"></teg:out>
        <teg:set var="isreturn" value="0"></teg:set>
   </teg:if>
</teg:if>
<teg:if test="${varStatus.index!=5}">
   ${value}
</teg:if>  
</td>
</teg:forEach>
 <td>
<teg:if test="${isreturn!=0}" >
<%-- <a href='showborrowinfo.teg?borrow_id=${borrow_history.borrow_id}'  >查看详情</a> --%>
<a href='#'  >查看详情</a>
</teg:if>
<teg:if test="${isreturn==0}">
 <a href='javascript:' onclick="comfirmreturn(${borrow_history.borrow_id},${borrow_history.book_id})">归还</a>
</teg:if>
</td> 
</tr>
</teg:forEach>
</table>
</form>
</teg:if>
<div style="text-align:center;margin:0 auto">
<a href="showborrowbook.teg?currentPage=1">首页</a>
<teg:if test="${currentPage>1}">
<a style="text-decoration: none;" href="showborrowbook.teg?currentPage=${currentPage-1}">上一页</a>
</teg:if>
<teg:forEach items="${arr}" varStatus="varStatus">
<teg:if test="${(varStatus.count>currentPage-2)&&(varStatus.count<currentPage+2)}">
<a style="text-decoration: none;" href="showborrowbook.teg?currentPage=${varStatus.count}">${varStatus.count}</a>
</teg:if>
</teg:forEach>
<teg:if test="${currentPage<pages}">
<a style="text-decoration: none;" href="showborrowbook.teg?currentPage=${currentPage+1}">下一页</a>
</teg:if>
<a href="showborrowbook.teg?currentPage=${pages}">最后一页</a>
</div>
<teg:import url="tail.jsp"></teg:import>
<a style="float: right;text-decoration: none;" href="readerdefault.jsp">返回上页</a>
</div>
</body>