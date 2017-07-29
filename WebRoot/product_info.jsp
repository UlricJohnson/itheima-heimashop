<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>会员登录</title>
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
	</style>
</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div
				style="border: 1px solid #e4e4e4; width: 930px; margin-bottom: 10px; margin: 0 auto; padding: 10px; margin-bottom: 10px;">
				<a href="default.jsp">首页&nbsp;&nbsp;</a> &gt;&nbsp;
				<a href="${pageContext.request.contextPath}/ProductServlet?action=getProductByCid&cid=${PRODUCT.category.cid}">${PRODUCT.category.cname}&nbsp;&nbsp;</a>&gt;&nbsp;
				<a>${PRODUCT.pname}</a>
			</div>

			<div style="margin: 0 auto; width: 950px;">
				<div class="col-md-6">
					<img style="opacity: 1; width: 400px; height: 350px;" title=""
						class="medium"
						src="${PRODUCT.pimage}">
				</div>

				<div class="col-md-6">
					<div>
						<strong>${PRODUCT.pname}</strong>
					</div>
					<div
						style="border-bottom: 1px dotted #dddddd; width: 350px; margin: 10px 0 10px 0;">
						<div>编号：${PRODUCT.pid}</div>
					</div>

					<div style="margin: 10px 0 10px 0;">
						商城价: <strong style="color: #ef0101;">￥：${PRODUCT.shop_price}元/份</strong> 市场价：
						<del>￥${PRODUCT.market_price}元/份</del>
					</div>

					<div style="margin: 10px 0 10px 0;">
						促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)"
							style="background-color: #f07373;">限时抢购</a>
					</div>

					<div
						style="padding: 10px; border: 1px solid #e7dbb1; width: 330px; margin: 15px 0 10px 0;; background-color: #fffee6;">
						<div style="margin: 5px 0 10px 0;">白色</div>

						<div
							style="border-bottom: 1px solid #faeac7; margin-top: 20px; padding-left: 10px;">
							购买数量: <input id="quantity" name="quantity" value="1"
								maxlength="4" size="10" type="text">
						</div>

						<div style="margin: 20px 0 10px 0;; text-align: center;">
							<a href="javascript:void(0);" onclick="addToCart()"> <input
								style="background: url('./images/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0); height: 36px; width: 127px;"
								value="加入购物车" type="button">
							</a> 
							&nbsp;
							收藏商品
						</div>
					</div>
				</div>
			</div>
			
			<script>
				//点击"加入购物车"按钮
				function addToCart(){
					//商品数量
					var quantity = document.getElementById("quantity").value;
					
					//设置加入购物车a标签跳转
					var url="${pageContext.request.contextPath}/CartServlet?action=addProduct&pid=${PRODUCT.pid}&quantity="+quantity;
					location.href=url;
					
					//var xhr = new XMLHttpRequest();
					
					/********************POST方式*******************/
					//请求路径
					//var url = "${pageContext.request.contextPath}/CartServlet";
					//请求方式
					//var method="POST";
					//xhr.open(method, url, true);
					
					//var jsonStr = "{'action':'addProduct','pid':" + ${PRODUCT.pid} + ",'quantity':" + quantity + "}";
					//conso;le.log(jsonStr)
					//var json = eval("("+jsonStr+")");
					//alert("json type: "+typeof json);//object
					//xhr.setRequestHeader("content-type", "application/json");//设置发送数据的请求格式
					//xhr.send(json);
					/******************************************/
					
					/**************GET方式****************/
					//var url = "${pageContext.request.contextPath}/CartServlet?action=addProduct&pid=${PRODUCT.pid}&quantity="+quantity;
					//var method="GET";
					//xhr.open(method, url, true);
					//xhr.send(null);
					/**********************************/
					
					//响应函数
					//xhr.onreadystatechange = function(){
						//alert("AJAX响应函数")
					//};
					
				}
			</script>
			
			<div class="clear"></div>
			<div style="width: 950px; margin: 0 auto;">
				<div
					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;">
					<strong>商品介绍</strong>
				</div>

				<div>
					<img
						src="${PRODUCT.pimage}">
				</div>

				<div
					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;">
					<strong>商品参数</strong>
				</div>
				<div style="margin-top: 10px; width: 900px;">
					<table class="table table-bordered">
						<tbody>
							<tr class="active">
								<th colspan="2">基本参数</th>
							</tr>
							<tr>
								<th width="10%">级别</th>
								<td width="30%">标准</td>
							</tr>
							<tr>
								<th width="10%">标重</th>
								<td>500</td>
							</tr>
							<tr>
								<th width="10%">浮动</th>
								<td>200</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div style="background-color: #d3d3d3; width: 900px;">
					<table class="table table-bordered">
						<tbody>
							<tr class="active">
								<th><strong>商品评论</strong></th>
							</tr>
							<tr class="warning">
								<th>暂无商品评论信息 <a>[发表商品评论]</a></th>
							</tr>
						</tbody>
					</table>
				</div>

				<div style="background-color: #d3d3d3; width: 900px;">
					<table class="table table-bordered">
						<tbody>
							<tr class="active">
								<th><strong>商品咨询</strong></th>
							</tr>
							<tr class="warning">
								<th>暂无商品咨询信息 <a>[发表商品咨询]</a></th>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>