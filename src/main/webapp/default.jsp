<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg"%>
<!DOCTYPE>
<html>
<head>
<title>首页</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<div class="main"  >
		<!-- 显示当前连接数 -->
		<div style="text-align: right; margin-right: 30px; height: 100px">
			<label>${"当前连接数 :"}</label> <label style="color: blue"> <%=application.getAttribute("onlineNumber")%></label>
		</div>
		<div style="text-align: center">
			<img src="images/EGroup.png"
				style="width: 80px; vertical-align: middle" /> <label
				style="font-size: 30px; vertical-align: middle">图书管理系统 v3.0</label>

			<hr class="normal_hr">
			<img src="images/library.jpg"
				style="width: 1000px; border-radius: 50px; margin: 20px" />
		</div>
		<teg:import url="tail.jsp"></teg:import>
	</div>
</body>