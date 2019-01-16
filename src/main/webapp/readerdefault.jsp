<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*,entities.Book"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg"%>

<teg:if test="${!empty err_msg}">
	<script>
		alert('${err_msg}')
	</script>
</teg:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户界面</title>
<%@ include file="head.jsp"%>
</head>
<body onload="getNow()">
	<div class="main">
		<teg:if test="${!empty success_msg}">
			<p id="text" class="success_info">
				<teg:out value="${success_msg}"></teg:out>
			</p>
		</teg:if>
		<teg:if test="${empty readername}">
			<teg:redirect url="readerlogin.jsp" />
		</teg:if>

 
          
		<p style="text-align: right">
		<label style="color:purple;margin-right:100px" id ="welcomemsg"></label> 
			欢迎你 ${readername}<label style="color:blue"> 读者</label> <a style="text-decoration: none;" href="readerlogout.teg">退出</a>
		</p>
		<div style="text-align: center; margin: 0 auto">
			<form action="readersearch.teg" method="post">
				<div id="search">
					<label class="normal_label"><%="请输入你要查询的内容"%></label> <input
						class="input_text_red" type="text" name="value" /> <select
						class="normal_select" name="searchtype">
						<option value="id">序号</option>
						<option value="name">书名</option>
						<option value="ISBN">ISBN</option>
						<option value="writer">作者</option>
						<option value="publisher">出版社</option>
						<option value="type">种类</option>
					</select> <input class="input_button_search" type="submit" value="检索" /> <a
						style="text-decoration: none;" href="borrowcart.teg">借阅车</a> <a
						style="text-decoration: none;" href="showborrowbook.teg">已借阅图书</a>
					<span style="float: right"><span style="margin-right: 30px">上次签到：<label
							id="lastsign" style="color: green"></label></span><span
						style="margin-right: 20px">积分： <label id="point"
							style="color: red"></label>
					</span><input class="input_button_submit" type="button" onclick="sign()"
						value="签到"></span>
				</div>
				
			</form>
			
           <script type="text/javascript">
          
           </script>
			<div>
				<teg:if test="${!empty books}">
				<h1 style="text-align:center;margin:10px auto">查询结果</h1>
					<form>
						<table class="simpletable">
							<tr>
								<teg:forEach items="<%=Book.getColumnsCN()%>"
									varStatus="varStatus" var="bookcol" begin="0"
									end="<%=Book.getColumnsnumber() - 1%>">
									<teg:if test="${varStatus.index!=9}">
										<th><teg:out value="${bookcol}"></teg:out></th>
									</teg:if>
								</teg:forEach>
								<th><teg:out value="操作"></teg:out></th>
							</tr>
							<%--forEach 嵌套循环 --%>
							<teg:forEach items="${books}" var="book">
								<tr>
									<teg:forEach items="${book.info}" var="value"
										varStatus="varStatus">
										<teg:if test="${varStatus.index!=9}">
											<td><teg:out value="${value}">
												</teg:out></td>
										</teg:if>
									</teg:forEach>
									<td><a href='bookinfo.teg?id=${book.id}'>查看详情</a></td>
								</tr>
							</teg:forEach>
						</table>
					</form>
				</teg:if>
				<teg:if test="${!empty emptyresult}">
					<%--如果查询图书结果为空并且要排除用户输入空值的情况 ，友好提示--%>
					<p id="text" class="warning_info">
						<teg:out value="${emptyresult}"></teg:out>
					</p>
				</teg:if>
			</div>
			<div style="text-align: center; margin-top: 50px">
					<%--设置最先出现的页数 --%>
					<teg:if test="${currrentPage>2}">
						<teg:set var="firstpage" value="${currentPage-2}"></teg:set>
					</teg:if>
					<teg:if test="${currentPage<=2 }">
						<teg:set var="firstpage" value="1"></teg:set>
					</teg:if>
					<%--当前页不是首页 才显示 --%>
					<teg:if test="${currentPage>1 }">
					<a href="readersearch.teg?currentpage=1">首页</a>
					</teg:if>
					<teg:if test="${currentPage>1}">
						<a href="readersearch.teg?currentpage=${currentPage-1}">上一页</a>
					</teg:if>
					<teg:forEach items="${arr}" varStatus="varStatus">
						<teg:if
							test="${(currentPage-3<=varStatus.count)&&(currentPage+3>=varStatus.count)}">
							<a style="text-decoration: none"
								href="readersearch.teg?currentpage=${varStatus.count}">${varStatus.count}</a>
						</teg:if>
					</teg:forEach>
					<teg:if test="${currentPage<pages}">
						<a href="readersearch.teg?currentpage=${currentPage+1}">下一页</a>
					</teg:if>
					<%--当前页不是最后一页才显示 --%>
					<teg:if test="${currentPage<pages }">
					<a href="readersearch.teg?currentpage=${pages}">最后一页</a>
					</teg:if>
				</div>
		</div>
		<teg:import url="tail.jsp"></teg:import>
	</div>
</body>

</html>