package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.shop.entity.Cart;
import com.itheima.shop.service.CartService;

/**
 * 购物车事务
 * 
 * @author Ulric
 * 
 */

public class CartServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======CartServlet--doGet()");

		String action = req.getParameter("action");
		if (action.equalsIgnoreCase("addProduct")) {
			this.addProduct(req, resp);
		} else if (action.equalsIgnoreCase("deleteProd")) {
			this.deleteProd(req, resp);
		} else if (action.equalsIgnoreCase("clearCart")) {
			this.clearCart(req, resp);
		}

	}

	/**
	 * 清空购物车
	 * 
	 * @param req
	 * @param resp
	 */
	private void clearCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======CartServlet--clearCart()");

		// 获得购物车
		Cart cart = (Cart) req.getSession().getAttribute("CART");

		// 传到service层进行清空操作
		CartService service = new CartService();
		service.clearCart(cart);

		// 刷新本页面
		resp.sendRedirect(req.getContextPath() + "/cart.jsp?time="
				+ new Date().getTime());
	}

	/**
	 * 删除商品
	 */
	private void deleteProd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======CartServlet--deleteProd()");

		// 获取商品pid
		int pid = Integer.parseInt(req.getParameter("pid"));

		System.out.println("============删除pid为" + pid + "的商品项");

		// 获取session和购物车
		HttpSession session = (HttpSession) req.getSession();
		Cart cart = (Cart) session.getAttribute("CART");

		// 在service层进行具体的删除操作
		CartService service = new CartService();
		service.deleteProd(pid, cart);

		// 把新的cart再次放入session中
		// session.setAttribute("CART", cart);

		// 以重定向到本页面的方式刷新页面
		resp.sendRedirect(req.getContextPath() + "/cart.jsp?time="
				+ new Date().getTime());
	}

	/**
	 * 添加商品到购物车
	 * 
	 * 添加完成后把购物车存入session中(暂时方案)
	 */
	private void addProduct(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======CartServlet--addProduct()");

		// 商品pid
		int pid = Integer.valueOf(req.getParameter("pid"));

		// 商品数量
		int quantity = Integer.valueOf(req.getParameter("quantity"));

		// 获取本用户的购物车对象，目前用户的购物车暂时存在session中
		HttpSession session = (HttpSession) req.getSession();
		Cart cart = (Cart) session.getAttribute("CART");

		// 如果session中没有cart，说明用户为第1次访问本网站，则为用户创建新的购物车
		if (cart == null) {
			cart = new Cart();
		}

		// service层添加商品操作
		CartService service = new CartService();
		try {
			// 向该购物车添加商品
			service.addProduct(pid, quantity, cart);

			// 把添加商品完成后的购物车存到session域中
			session.setAttribute("CART", cart);

			// 重定向到cart.jsp
			resp.sendRedirect(req.getContextPath() + "/cart.jsp");
		} catch (Exception e) {
			// 发生异常则转到错误页面
			req.setAttribute("ERRORMSG", "添加失败");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
			e.printStackTrace();
		}

	}

	/*** post ***/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("=======CartServlet--doPost()");

		/**
		 * POST方式传递参数为json格式，放在request的body中
		 */

		doGet(req, resp);
	}

}
