<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>黑马商城购物车</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
	</head>

	<body>
		<!-- 引入header.jsp -->
		<jsp:include page="/header.jsp"></jsp:include>

		<div class="container">
			<%-- 当购物车的商品总数不为 0 的时候显示表格，否则显示提示信息 --%>
			<c:choose>
				<c:when test="${CART.num != 0}">
					<div class="row">
						<div style="margin:0 auto; margin-top:10px;width:950px;">
							<strong style="font-size:16px;margin:5px 0;">购物车详情</strong>
							<%-- <span style="float:right;padding-right:10px;">
								<a href="${pageContext.request.contextPath}/ProductServlet?action=getProductByCid&cid=">继续剁手</a>
							</span> --%>
							<table class="table table-bordered">
								<tbody>
									<tr class="warning">
										<th>图片</th>
										<th>商品</th>
										<th>价格</th>
										<th>数量</th>
										<th>小计</th>
										<th>操作</th>
									</tr>
									
									<%-- items 为购物车中的购物项Map集合 --%>
									<c:forEach var="item" items="${CART.items}">
										<tr class="active">
											<td width="60" width="40%">
												<input type="hidden" name="id" value="${item.value.product.pid}">
												<img src="${item.value.product.pimage}" width="70" height="60">
											</td>
											<td width="30%">
												<a target="_blank">${item.value.product.pname}</a>
											</td>
											<td width="20%">￥${item.value.product.shop_price}</td>
											<td width="10%">
												<input type="text" name="quantity" value="${item.value.num}" maxlength="4" size="10">
											</td>
											<td width="15%">
												<span class="subtotal">￥${item.value.subTotal}</span>
											</td>
											<td>
												<%-- <a href="javascript:void(0)" class="delete" onclick="deleteProd()">删除</a>
												<input type="hidden" value="${item.value.product.pid}"/> --%>
												<a href="${pageContext.request.contextPath}/CartServlet?action=deleteProd&pid=${item.value.product.pid}" class="delete">删除</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div style="margin-right:130px;">
						<div style="text-align:right;">
							<%-- <em style="color:#ff6600;">登录后确认是否享有优惠&nbsp;&nbsp;</em>  --%>
							商品总数：<em style="color:#ff6600;">${CART.num}</em>&nbsp; 
							商品金额: <strong style="color:#ff6600;">￥${CART.total}元</strong>
						</div>
						<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
							<a href="${pageContext.request.contextPath}/CartServlet?action=clearCart&cart=${CART}" id="clear" class="clear">清空购物车</a>
							<a href="${pageContext.request.contextPath}/OrderServlet?action=submitOrder">
								<input type="submit" width="100" value="提交订单" name="submit" border="0"
									style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
									height:35px;width:100px;color:white;">
							</a>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h1 style="color:red;font-size:22px;text-align:center;">购物车为空</h1>
					<p style="text-align:center;"><a href="default.jsp">先逛逛吧~~</a></p>
				</c:otherwise>
			</c:choose>
		</div>
		
		<script>
		
			//删除购物项
			/*function deleteItem(){
				//如果用户确认删除则发送请求
				if(confirm("确认删除？")){
					//var pid=document.getElementById("pidInput").value;
					var reqUrl="${pageContext.request.contextPath}/CartServlet?action=deleteProd&pid="+pid;
					location.url=reqUrl;
				}
				
			}*/
		</script>

		<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>

	</body>

</html>