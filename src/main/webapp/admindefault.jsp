<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,entities.Book"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg"%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<%@ include file="head.jsp"%>
</head>
<body
	onload="getNow();null_alert('${err_msg}',${empty err_msg});
     deletesuccess_alert('${deletesuccess_msg}',${empty deletesuccess_msg});
     updatesuccess_alert('${updatesuccess_msg}',${empty updatesuccess_msg})">
	<teg:if test="${empty adminname}">
	<teg:redirect url="default.jsp"></teg:redirect>
	</teg:if>
	<teg:if test="${empty adminname}&&${empty deletesuccess_msg }">
		<%--session验证:如果这里的adminname为空，表示管理员还没有验证登陆，这时候需要跳转  --%>
		<teg:redirect url="adminlogin.jsp"></teg:redirect>
	</teg:if>
	<div class="main">
	<teg:if test="${!empty success_msg}">
		<p id="text" class="success_info">
			<teg:out value="${success_msg}"></teg:out>
		</p>
	</teg:if>
		<p style="text-align: right">
		<label style="color:purple;margin-right:100px" id ="welcomemsg"></label> 
			<teg:out value="欢迎你 ${adminname}"></teg:out><label style="color:red">  管理员 </label>
			<a style="text-decoration: none;" href="adminlogout.teg">退出</a>
		</p>
		<form action="adminsearch.teg" method="post"
			style='text-align: center'>
			<div class="ss">
				<label class="normal_label"><%="请输入你要查询的内容"%></label> <input
					class="input_text_red" type="text" name="value" /> <select
					class="normal_select" name="searchtype">
					<option value="id">序号</option>
					<option value="name">书名</option>
					<option value="ISBN">ISBN</option>
					<option value="writer">作者</option>
					<option value="publisher">出版社</option>
					<option value="type">种类</option>
				</select> <input class="input_button_search" type="submit" value="检索" />
				 
			</div>
		</form>
		<teg:if test="${!empty books }">
		<h1 style="text-align:center;margin:10px auto">查询结果</h1>
			<form>
				<table class="simpletable">
					<tr>
						<%--此处很不想用jsp中的标签，但是还是用了。
					想了很久没有找到合适的方式获得Book类中的静态数组。 --%>
						<teg:forEach items="<%=Book.getColumnsCN()%>" var="bookcol">
							<th><teg:out value="${bookcol}"></teg:out></th>
						</teg:forEach>
						<th><teg:out value="操作"></teg:out></th>
					</tr>
					<%--forEach 嵌套循环 --%>
					<teg:forEach items="${books}" var="book">
						<tr>
							<teg:forEach items="${book.info}" var="value">
								<td style="max-width:500px"><teg:out value="${value}">
									</teg:out></td>
							</teg:forEach>
							<td><a
								onclick="comfirm_to_alert('${book.name}','${book.id}')"><label style="color:green">修改</label></a> <a
								onclick="comfirm_delete_alert('${book.name}','${book.id}')"><label style="color:red">删除</label></a></td>
						</tr>
					</teg:forEach>
				</table>
				<div style="text-align: center; margin-top: 50px">
					<%--设置最先出现的页数 --%>
					<teg:if test="${currrentPage>2}">
						<teg:set var="firstpage" value="${currentPage-2}"></teg:set>
					</teg:if>
					<teg:if test="${currentPage<=2 }">
						<teg:set var="firstpage" value="1"></teg:set>
					</teg:if>
					<%--当前页不是首页 才显示 --%>
					<teg:if test="${currentPage!=1 }">
					<a href="adminsearch.teg?currentpage=1">首页</a>
					</teg:if>
					<teg:if test="${currentPage>1}">
						<a href="adminsearch.teg?currentpage=${currentPage-1}">上一页</a>
					</teg:if>
					<teg:forEach items="${arr}" varStatus="varStatus">
						<teg:if
							test="${(currentPage-3<=varStatus.count)&&(currentPage+3>=varStatus.count)}">
							<a style="text-decoration: none"
								href="adminsearch.teg?currentpage=${varStatus.count}">${varStatus.count}</a>
						</teg:if>
					</teg:forEach>
					<teg:if test="${currentPage<pages}">
						<a href="adminsearch.teg?currentpage=${currentPage+1}">下一页</a>
					</teg:if>
					<%--当前页不是最后一页才显示 --%>
					<teg:if test="${currentPage!=pages }">
					<a href="adminsearch.teg?currentpage=${pages}">最后一页</a>
					</teg:if>
				</div>
			</form>
		</teg:if>
		<teg:if
			test="${empty books&&empty err_msg&&empty deletesuccess_msg&&empty updatesuccess_msg&&empty success_msg}">
			<%--如果查询图书结果为空并且要排除用户输入空值的情况 ，友好提示--%>
			<p id="text" class="warning_info">
				<teg:out value="查询结果为空"></teg:out>
			</p>
		</teg:if>
		<teg:import url="tail.jsp"></teg:import>
	</div>
</body>
</html>