package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.shop.entity.Category;
import com.itheima.shop.entity.Page;
import com.itheima.shop.entity.Product;
import com.itheima.shop.service.ProductService;

/**
 * 商品相关操作
 * 
 * 展示
 * 
 * @author Ulric
 * 
 */

public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======ProductServlet-->method: " + req.getMethod());

		String action = req.getParameter("action");

		if (action.equalsIgnoreCase("toIndexJsp")) {
			// default.jsp重定向到index.jsp,查找热门和最新商品

			// 两者顺序不能调换，因为 getHotProducts() 查找成功后没有转发
			// 转发动作放在 getLatestProducts() 中
			this.getHotProducts(req, resp);
			this.getLatestProducts(req, resp);
		} else if (action.equalsIgnoreCase("getNavBar")) {
			// 从redis或mysql中获取导航条数据
			this.getNavBar(req, resp);
		} else if (action.equalsIgnoreCase("getProductByCid")) {
			// 展示相应类型的商品
			this.getProductByCid(req, resp);
		} else if (action.equalsIgnoreCase("getProductByPid")) {
			this.getProductByPid(req, resp);
		}
	}

	/**
	 * 展示商品详情
	 */
	private void getProductByPid(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("======ProductServlet--getProductByPid()");

		String pid = req.getParameter("pid");

		try {
			ProductService service = new ProductService();
			Product product = service.getProductByPid(pid);
			
			req.setAttribute("PRODUCT", product);
			req.getRequestDispatcher("/product_info.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("ERRORMSG", "暂无商品详情");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
		}
	}

	/**
	 * 根据商品类别cid获取相应的商品
	 */
	private void getProductByCid(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("======ProductServlet--getProductByCid()");

		String cid = req.getParameter("cid");

		String start = req.getParameter("start");// 从第start条数据开始显示
		if (start == null || start.trim().length() <= 0) {
			// 如果没有start参数，说明是从首页的导航条点击进来的，则默认从第0条数据开始显示
			start = "0";
		}

		ProductService service = new ProductService();
		try {
			Page page = service.getProductByCid(Integer.parseInt(cid),
					Integer.parseInt(start), 6);// 每页显示商品数先写死为6个
			req.setAttribute("PAGE", page);
			req.getRequestDispatcher("/product_list.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("ERRORMSG", "查询商品出错");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
			e.printStackTrace();
		}

	}

	/**
	 * 查找导航条数据
	 */
	private void getNavBar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======ProductServlet--getNavBar()");

		ProductService service = new ProductService();

		// 保存Category对象
		List<Category> cateList = new ArrayList<Category>();
		try {
			cateList = service.getNavBar();

			Gson gson = new Gson();
			String cateJson = gson.toJson(cateList);
			System.out.println("======ProductServlet--getNavBar()--cateJson: "
					+ cateJson);

			// 响应输出流
			resp.setCharacterEncoding("UTF-8");// 要放在获取输出流对象之前
			PrintWriter pWriter = resp.getWriter();
			pWriter.write(cateJson);
			pWriter.flush();
			pWriter.close();

			// System.out.println("======ProductServlet--getNavBar()--cateList: "
			// + cateList);//OK
			// req.setAttribute("CATE_LIST", cateList);
			// req.getRequestDispatcher("/header.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找热门商品，最多显示12项
	 */
	private void getHotProducts(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======ProductServlet--getHotProducts()");

		ProductService service = new ProductService();
		try {
			List<Product> hotProducts = service.getHotProducts();
			req.setAttribute("HOT_PRODUCT_LIST", hotProducts);

			// 在下面查找完最新商品之后再一次性转发
			// req.getRequestDispatcher("/index.jsp").forward(req, resp);

		} catch (SQLException e) {
			req.setAttribute("ERRORMSG", "天气太冷，找不到热门商品");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			e.printStackTrace();
		}

	}

	/**
	 * 查找最新商品，最多12项
	 */
	private void getLatestProducts(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("======ProductServlet--getLatestProducts()");

		try {
			ProductService service = new ProductService();
			List<Product> latestProducts = service.getLatestProducts();
			req.setAttribute("LATEST_PRODUCT_LIST", latestProducts);
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("ERRORMSG", "并没有！");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			e.printStackTrace();
		}
	}

	/***** post ****/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
