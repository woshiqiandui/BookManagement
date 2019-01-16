<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List,entities.Book"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg"%>
<!DOCTYPE>
<html>
<head>
<title>书籍详情</title>
<%@ include file="head.jsp"%>
</head>
<body >
<teg:if test="${empty readerid}">
	<teg:redirect url="default.jsp"></teg:redirect>
	</teg:if>
<teg:if test="${!empty books}">
				<teg:set var="book" value="${books[0]}"></teg:set>
			</teg:if>
	<div class="main" style="height:800px">
	 <div style="text-align:center;margin:0 auto"> 
		<h1 style='text-align: center'>书籍详情</h1>
		<%--先居中，然后再移动位置 --%>
		<span style=" width:350px;height:450px;margin-left:300px; float:left;" >
		<img alt="${book.id}.png" src="images/books/${book.id}.png" width="280px" style="margin-top:50px;margin-left:10px;'">
 		<hr class="normal_hr" style="margin-top:50px"/> 
		</span> 
		
				<form action='borrowbook.teg' method='post'>
			<span style="margin-top:50px;margin-left:20px;display:block;text-align:left;float:left; ">
					<teg:if test="${!empty book }">
						<teg:forEach begin="0" end="${book.columnsNum}"
							varStatus="varStatus">
							<div>
								<teg:if test="${varStatus.index!=9}">
									<span style="width: 100px; display: inline-block;"> <label
										class="normal_label">
											${book.columns_CN[varStatus.index]} </label>
									</span>
								</teg:if>
								<%--此处添加一个隐藏的 --%>
								<teg:if test="${varStatus.index==0}">
									<input class="input_text_blue" type="hidden"
										name='${book.columns_EN[varStatus.index] }'
										value='${book.info[varStatus.index]}'/>
								</teg:if> 
								<teg:if test="${varStatus.index!=9}">
									<span style="width: 100px; display: inline-block;"> 
									<label
										class="normal_label"
										style="display: inline-block; width: 200px">
											${book.info[varStatus.index]} 
											<teg:if test="${varStatus.index==10}" >
											<teg:out value="元（￥）"></teg:out>
											</teg:if>
											</label>
									</span>
								</teg:if>
								<teg:if test="${varStatus.index==9}">
									<span style="width: 100px; display: inline-block;">
										<fieldset>
											<legend> ${book.columns_CN[varStatus.index]} </legend>
											<textarea  cols="50" rows="6" readonly="readonly">
				                               ${book.info[varStatus.index]}
				                            </textarea>
										</fieldset>

									</span>
								</teg:if>
							</div>
						</teg:forEach>
					</teg:if>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="input_button_big_modify" type='submit' value='添加到借阅车' />
					</span>
				</form> 
		</div> 
	</div>
	  	<teg:import url="tail.jsp"></teg:import>  
</body>