<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
	<script url="js/jquery-1.11.3.min.js"></script>

<!-- 顶部 -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:choose>
				<%-- 如果在session中找到用户对象，则显示信息 --%>
				<c:when test="${!empty USER}">
					<li><span>欢迎光临：${USER.username}</span></li>
					<li><a href="cart.jsp">购物车</a></li>
					<li><a href="order_list.jsp">我的订单</a></li>
					<li><a
						href="${pageContext.request.contextPath}/UserServlet?action=logout">退出</a></li>
				</c:when>
				<c:otherwise>
					<li><span>欢迎光临：游客</span></li>
					<li><a href="login.jsp">登录</a></li>
					<li><a href="register.jsp">注册</a></li>
				</c:otherwise>
			</c:choose>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="default.jsp">首页</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="navBarUl">

					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>

<script>
	/**
		由于导航条中的内容为商品类别，可能会万年不变，
		不同于首页中的商品信息，所以利用AJAX和redis局部更新导航条中的数据，
		以提升并发访问的效率
	 */
	window.onload = function() {
		var xhr = new XMLHttpRequest();

		//POST方式
		//var url = "${pageContext.request.contextPath}/ProductServlet";//EL不能放在引号外面
		//xhr.open("POST", url, true);//true表示异步请求
		/* xhr.send({
			"action":"getNavBar"
		}); */

		//响应函数
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var returnData = this.responseText;//获取到的是json字符串
				//console.log(returnData)
				
				var json = eval("(" + returnData + ")");//转成json对象
				//console.log(json)

				for (var i = 0; i < json.length; i++) {
					//创建li元素并设置属性和文本内容
					var $liEle=$("<li><a href="
							+ "'${pageContext.request.contextPath}/ProductServlet?action=getProductByCid&cid=" + json[i].cid + "'>"
							+ json[i].cname + "</a></li>");
					
					//添加元素
					$("#navBarUl").append($liEle);
				}
			}
		};

		//get方式
		var url = "${pageContext.request.contextPath}/ProductServlet?action=getNavBar";
		xhr.open("GET", url, true);

		xhr.send(null);

	}
</script>
