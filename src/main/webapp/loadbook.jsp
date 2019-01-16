<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,entities.Book"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="teg"%>
  
<!DOCTYPE html > 
<html>
<head> 
<title>加载书籍资料</title>
<%@ include file="head.jsp" %> 
</head>
<%--数据没有通过验证，则到loadbook.teg处理界面后 
这里用了sweet alert的回调函数。
--%>
<body onload="
    if(${!empty err_msg}){
	swal({
		  title: '操作失败',
		  text: ' ${err_msg}',
		  type: 'warning',
		  /* 不允许取消*/
		  showCancelButton: false,
		  confirmButtonColor: '#DD6B55',
		  confirmButtonText: '重新修改!',
		  closeOnConfirm: false
		},
		function(){ 
		<%--可以有两种方式跳转 %>
		 <%--  location.href='loadbook.teg?id=${id}'; --%>
		  window.location='loadbook.teg?id=${id}';
		});
}
">

<div class="main"> 
<h1 style="text-align：center;margin-left:470px">书籍详细信息</h1>
    <%--这里必须保证err-msg为空，否则前面的javascript代码中的跳转将无法执行。
                           就是说，一页符合跳转条件的跳转语句不能大于一条。
     --%>
    <teg:if test="${empty books&&empty err_msg }">
      <%--如果传入为空，就回跳到主界面 --%>
      <teg:redirect url="admindefault.jsp"/> 
    </teg:if>
    <teg:if test="${!empty books }">
    <teg:set var="book" value='${books[0]}'></teg:set>
      <form action="modifybook.teg" method='post'>
      <div style="text-align:left;margin-left:170px;height:600px;width:380px; float:left" >
         <%--获取中文和英文列 --%>
         <teg:set var="bookcol_EN" value="${book.columns_EN}" />
         <teg:set var="bookcol_CN" value="${book.columns_CN}" />
         <teg:forEach items="${book.info}"  var="value" varStatus="varStatus">
         <teg:if test="${varStatus.index!=9}">
         <div>
         <span style="width:100px;display:inline-block;">
         <label for='${bookcol}' class="normal_label" >${bookcol_CN[varStatus.index]}
         </label>
         </span>
         <teg:if test="${varStatus.index==0}">
         <input class="input_text_blue" style="margin:5px;background:#CCCCCC" type='text' name='${book.columns_EN[varStatus.index]}' 
			value='${value}' readonly />
         </teg:if>	
         <teg:if test="${varStatus.index!=0}">
         <input class="input_text_blue" style="margin:5px" type='text' name='${book.columns_EN[varStatus.index]}' 
			value='${value}' />
         </teg:if>	
         </div>
         
         </teg:if>
         </teg:forEach>
          <div style="margin-left:80px;margin-top:30px">
		   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <input class="input_button_big_submit" type='submit' value='提交' />
		 
		</div> 
      </div>
      <div style='width:750px;height:600px; float:right'>
      <!-- 加载图片 -->
		 <div style="margin-left:50px">
		 <img style="width:300px;border-radius:5px" alt="${book.id}.png" src="images/books/${book.id}.png">
		 </div>
		 <div  > 
				<textarea   style="margin:5px"  rows="7" cols="50" name='${bookcol_EN[9]}' 
			 >${book.info[9]}</textarea>
			</div>
		</div>	 
      </form>
    </teg:if>
 
</div>
<teg:import url="tail.jsp"></teg:import>
</body>
</html>