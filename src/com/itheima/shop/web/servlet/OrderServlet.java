package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.shop.entity.Cart;
import com.itheima.shop.entity.CartItem;
import com.itheima.shop.entity.OrderItem;
import com.itheima.shop.entity.User;

/**
 * 订单处理
 * 
 * @author Ulric
 * 
 */

public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======OrderServlet--doGet()");

		String action = req.getParameter("action");

		if (action.equalsIgnoreCase("submitOrder")) {
			this.submitOrder(req, resp);
		}

	}

	/**
	 * 提交订单(购物车页面发过来的请求)
	 * 
	 * 创建一个订单项集合
	 * 
	 * 获取session中的购物车，把其中的购物项放到订单项集合中
	 * 
	 * 把集合放到session中，因为提交之前没有涉及到user表(游客可以加入购物车)
	 * 
	 * 所以只需获取其中的购物项即可
	 * 
	 * 但提交订单之后需要和用户联系起来
	 * 
	 * 所以1.提交订单需要验证是否登录；2.对于提交的订单的处理需要和user表联系起来
	 */
	private void submitOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======OrderServlet--submitOrder()");

		// 判断用户是否登录并激活账号
		User user = (User) req.getSession().getAttribute("USER");
		// 用户没有登录
		if (user == null) {
			req.setAttribute("ERRORMSG", "亲，您还没有登录哟~");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
		} else {
			// 用户没有激活账号
			if (user.getState().equalsIgnoreCase("N")) {
				req.setAttribute("ERRORMSG", "亲，您的账号还没有激活哟~");
				req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req,
						resp);
			}
		}

		/** 用户已经登录并且激活账号 **/
		System.out.println("///////////////用户已经登录并且激活账号");

		// 获取session中的购物车
		Cart cart = (Cart) req.getSession().getAttribute("CART");

		// 获取购物车中的购物项集合
		Map<Integer, CartItem> cartItems = cart.getItems();

		// 创建订单项集合
		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		// 把购物项放到订单项集合中
		for (Entry<Integer, CartItem> cartItemEntry : cartItems.entrySet()) {
			// 获取购物项
			CartItem cartItem = cartItemEntry.getValue();

			// 创建一个订单项
			OrderItem orderItem = new OrderItem();

			// 把购物项中的值转移到订单项中
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setNum(cartItem.getNum());
			orderItem.setSubTotal(cartItem.getSubTotal());

			// 把订单项放进订单项集合中
			orderItems.add(orderItem);
		}

		// 把订单项集合放进session中
		req.getSession().setAttribute("ORDERITEMS", orderItems);
		resp.sendRedirect(req.getContextPath() + "/order_info.jsp");

	}

	/**** POST ***/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
