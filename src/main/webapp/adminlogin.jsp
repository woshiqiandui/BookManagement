<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg"%>
<!DOCTYPE>
<html>
<head>
<title>管理员登录界面</title>
<%@ include file="head.jsp"%>
</head>
<body onload="loginfail_alert('${err_msg}',${empty err_msg});">
	<div class="main" onload="deletefail_alert('${err_msg}');" >   
		<div style=" text-align:center;margin:0 auto;height:180px;">
		<img alt="EGroup.png" style="height:175px " src="images/EGroup.png"  >
		</div>
		<hr class="normal_hr">
		<div style="margin: 0 auto; text-align: center">
			<form action="adminlogin.teg" method="post">
				<span style="width: 100px; display: inline-block;"><label
					class="normal_label" for="name">用户名：</label></span> <input
					class="input_text_red" name="name" placeholder="请输入管理员姓名或者ID" /> <br />
				<br /> <span style="width: 100px; display: inline-block;"><label
					class="normal_label" for="pwd">密码：</label></span> <input
					class="input_text_red" placeholder="请输入密码" type="password"
					name="pwd" /> <br />
				<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="width: 100px; display: inline-block;"><label
					class="normal_label" for="vcode">验证码：</label></span> <input
					class="input_text_red" placeholder="请输入验证码" name="vcode"> <img
					style="vertical-align: middle" src="code"
					onclick="this.src='code?'+Math.random();" class="s1" title="点击更换" /><br />
				<br /> <input class="input_button_login" type="submit" value="登录" />
			</form>
		</div>
		<teg:import url="tail.jsp"></teg:import>
	</div>
</body>