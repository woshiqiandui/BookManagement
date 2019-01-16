$(function() {
	/* 隔行不同颜色 */
	$(function() {
		$(".simpletable tr:even").addClass("even");
		$(".simpletable tr:odd").addClass("odd");
	});
	/* 鼠标悬停变色方法一 */
	$(".simpletable tr.odd").hover(function() {
		$(this).removeClass("odd");
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
		$(this).addClass("odd");
	});
	$(".simpletable tr.even").hover(function() {
		$(this).removeClass("even");
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
		$(this).addClass("even");
	});

	/* 鼠标悬停变色方法二 */
	/*
	 * $(".simpletable tr.odd").bind('mouseover',function(){
	 * $(this).removeClass('odd'); $(this).addClass('hover'); });
	 * $(".simpletable tr.odd").bind('mouseout',function(){
	 * $(this).removeClass('hover'); $(this).addClass('odd'); });
	 * $(".simpletable tr.even").bind('mouseover',function(){
	 * $(this).removeClass('even'); $(this).addClass('hover'); });
	 * $(".simpletable tr.even").bind('mouseout',function(){
	 * $(this).removeClass('hover'); $(this).addClass('even'); });
	 */

});

/** 登陆信息提示 */
$(function() {
	var time = $('#sec').html();
	var interval = setInterval(function() {
		time--;
		$('#sec').html(time);
		if (time <= 0) {
			$('#text').hide();
			clearInterval(interval);
		}
	}, 2500);
});

/** 操作错误提示 */
/* 登陆密码错误提醒 */
function loginfail_alert(msg, flag) {
	// flag=false执行,表示出错了，就要提醒；flag=true,不执行提醒操作
	if (flag == false) {
		swal("登陆失败", msg, "error");
	}
}
/* 空值提醒 */
function null_alert(msg, flag) {
	// flag=false执行,表示出错了，就要提醒；flag=true,不执行提醒操作
	if (flag == false) {
		swal("警告", msg, "warning");
	}
}
/* 确认删除数据 */
function comfirm_to_alert(name, id) {
	swal({
		title : '修改',
		text : '是否要修改' + "《" + name + "》?",
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : '#DD6B55',
		confirmButtonText : '立刻修改!',
		cancelButtonText : '取消',
		closeOnConfirm : false
	}, function() {
		/* alert(msg); */
		window.location = 'loadbook.teg?id=' + id;
	});
}
/* 确认修改数据 */
function comfirm_delete_alert(name, id) {
	swal({
		title : '删除',
		text : '是否要删除' + "《" + name + "》?",
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : '#DD6B55',
		confirmButtonText : '立刻删除!',
		cancelButtonText : '取消',
		closeOnConfirm : false
	}, function() {
		window.location = 'deletebook.teg?id=' + id;
	});
}
/* 确认归还书籍 */
function comfirmreturn(borrowid, bookid) {
	swal({
		title : '还书',
		text : '确定要归还?',
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : '#DD6B55',
		confirmButtonText : '确定',
		cancelButtonText : '取消',
		closeOnConfirm : false
	}, function() {
		/* alert(msg); */
		window.location = 'returnbook.teg?borrow_id=' + borrowid;
	});
}
/* 确认借阅图书 */
function comfirm_borrow_alert(id, name, rent) {
	swal({
		title : '借阅',
		text : '是否要借阅' + "《" + name + "》?,费用每天" + rent + "元",
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : '#DD6B55',
		confirmButtonText : '立刻借阅!',
		cancelButtonText : '取消',
		closeOnConfirm : false
	}, function() {
		swal("成功提示", "图书" + name + "借阅成功", "success");
		window.location = 'borrowconfirm.teg?id=' + id;
	});
}
/* 还书成功提醒 */
function returnbook_alert(msg, flag) {
	if (flag == true) {
		sweetAlertInitialize();
		swal("成功提示", msg, "success");
	}
}

/* 删除成功提醒 */
function deletesuccess_alert(msg, flag) {
	// flag=false执行,表示出错了，就要提醒；flag=true,不执行提醒操作
	if (flag == false) {
		swal("删除成功", msg, "success");
	}
}

/* 更新成功提醒 */
function updatesuccess_alert(msg, flag) {
	// flag=false执行,表示出错了，就要提醒；flag=true,不执行提醒操作
	if (flag == false) {
		swal("更新数据成功", msg, "success");
	}
}
/* 读者页面签到 */
function sign() {
	$.ajax({
		url : "sign.teg",
		type : "post",
		dateType : "string",
		// data 为传输过来的数据，就是在servelt里面写入的数据
		success : function(data) {
			if (data.substring(0, 7) == "success") {
				swal("签到成功", "已记录", "success");
				// 修改页面显示的数据
				var sumsign = $("#point").text();
				sumsign++;
				$("#point").text(sumsign);
				// 修改日期为当前日期
				var currentdate = new Date();
				var year = currentdate.getFullYear();
				var month = currentdate.getMonth() + 1;
				var day = currentdate.getDate();
				if (month >= 1 && month <= 9) {
					month = "0" + month;
				}
				if (day >= 0 && day <= 9) {
					day = "0" + day;
				}
				var currentdatestr = year + "-" + month + "-" + day;
				$("#lastsign").text(currentdatestr);

			} else {
				swal("您已签到", "不要重复操作", "warning");
			}

		}
	});
}
/** 显示读者积分 */
$(document).ready(function showpoint() {
	$.ajax({
		url : "showpoint.teg",
		type : "post",
		dateType : "string",
		// data 为传输过来的数据，就是在servelt里面写入的数据
		success : function(data) {
			$("#point").text(data);
		}
	});
})
/** 显示上次登录时间 */
$(document).ready(function showlastsign() {
	$.ajax({
		url : "showlastsign.teg",
		type : "post",
		dateType : "string",
		// data 为传输过来的数据，就是在servelt里面写入的数据
		success : function(data) {
			if (data.substring(0, 4) == "null") {
				$("#lastsign").text("从未签到");
			} else {
				$("#lastsign").text(data);
			}
		}
	});
})
/** 获得当前时间：小时（0-23） */
function getNow() {
	// 获得当前时间，小时
	var date = new Date();
	var hour = date.getHours();
	var welcomemsg;
	if (hour > 0 && hour < 5 || hour > 23) {
		welcomemsg = "夜深了，注意休息";
	} else if (hour > 6 && hour < 10) {
		welcomemsg = "早上好";
	} else if (hour > 10 && hour < 14) {
		welcomemsg = "中午好";
	} else if (hour > 14 && hour < 18) {
		welcomemsg = "下午好";
	} else {
		welcomemsg = "晚上好";
	}
	$("#welcomemsg").text(welcomemsg);
}