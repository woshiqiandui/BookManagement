<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" />
<!-- 加载jQuery包 -->
   <script lang="javascript" src='jQuery/jquery-1.4.3.js'></script>
  <!-- 加载sweetalert的js和css文件 -->
  <script src="sweetalert/js/sweet-alert.js"></script>
  <link rel="stylesheet" href="sweetalert/css/sweet-alert.css">
    <!-- 加载个人的js包 --> 
   <script type="text/javascript" src="js/template.js"></script>
  <!-- 修改图标 -->
<link rel="shortcut icon" href="images/EGroup.ico" >
   <!--个人的css包 ,个人的包一定要放在最后面-->
<link rel="stylesheet" href="css/template.css" type="text/css" />  

<div id="navigation"> 
<ul id="nav"> 
    <li><a id="welcome" href="#">欢迎来到图书管理系统  WELCOME </a></li>
    <li><a  href="default.jsp">首页</a></li> 
    <li><a  href="readerlogin.teg">读者登录</a></li> 
    <li><a  href="adminlogin.teg">管理员登陆</a></li> 
    <li><a  href="#">你懂的</a></li> 
    <li><a  href="#" onclick="showInfo()">更多</a></li> 
    <li><a  href="#">联系我</a></li> 
    <li><span>全群加我  qq:772595674</span></li>
</ul> 
 </div>
 <script type="text/javascript">
 function showInfo(){ 
	   alert("                                            Version: v3.0\n" +
					"                               (c)Copyright Group2Java06,itek infor inc.\n" +
					"                                        All rights reserved\n"
					           +"                  Group Members: 陈迁对  李煜  江宜瑞  马超 李梅 韩云涛");
};
 </script>
  
 
 