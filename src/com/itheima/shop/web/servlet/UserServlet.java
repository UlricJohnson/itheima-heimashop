package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.shop.entity.User;
import com.itheima.shop.service.UserService;

/**
 * 用户相关操作处理
 * 
 * 注册、登录、退出
 * 
 * web层
 * 
 * @author Ulric
 * 
 */

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======UserServlet--doGet()");

		String action = req.getParameter("action");

		if (action.equalsIgnoreCase("register")) {
			this.register(req, resp);
		} else if (action.equalsIgnoreCase("login")) {
			this.login(req, resp);
		} else if (action.equalsIgnoreCase("logout")) {
			this.logout(req, resp);
		}
	}

	/**
	 * 退出登录
	 * 
	 * 删除该用户的HttpSession然后重定向到index.jsp
	 */
	private void logout(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======UserServlet--logout()");

		// 移除session中的数据
		HttpSession session = req.getSession();
		// session.removeAttribute("USER");
		session.invalidate();// 直接销毁session

		// 重定向到index.jsp
		resp.sendRedirect(req.getContextPath() + "/index.jsp");

	}

	/**
	 * 用户登录
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======UserServlet--login()");

		// 获取用户名和密码
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// 是否自动登录，on / null
		String isAutoLogin = req.getParameter("autoLogin");
		System.out.println("==============isAutoLogin --> " + isAutoLogin);

		// 根据用户名和密码寻找是否存在该用户，若存在则以User对象形式返回该用户
		UserService service = new UserService();
		User user = null;
		try {
			user = service.login(username, password);

			// 如果存在该用户则把用户对象放进session中，然后跳转到首页
			if (user != null) {
				req.getSession().setAttribute("USER", user);
				// req.getRequestDispatcher("index.jsp").forward(req, resp);
				resp.sendRedirect("index.jsp");// 此处可以用重定向
			} else {
				// 不存在则跳转到错误页面
				req.setAttribute("ERRORMSG", "账号或密码错误");
				req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req,
						resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 用户注册
	 * 
	 * @param req
	 * @param resp
	 */
	private void register(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======UserServlet--register()");

		try {
			// 由于注册信息太多，所以一次性获取所有属性
			Map<String, String[]> map = req.getParameterMap();

			User user = new User();
			// 根据Map集合中的属性封装成一个User对象
			BeanUtils.populate(user, map);

			/**
			 * *********这部分应该放到service层 **********************
			 * 
			 * 
			 * // 把密码用MD5加密 String pwdMD5 =
			 * Md5Util.encodeByMd5(user.getPassword());
			 * user.setPassword(pwdMD5);
			 * 
			 * // 把激活状态置为N (即为未激活) user.setState("N");
			 ****************************************************/

			UserService service = new UserService();
			service.register(user);

			// 注册成功，重定向到注册成功提示页面
			resp.sendRedirect(req.getContextPath() + "/register_ok.jsp");
		} catch (Exception e) {
			// 转发到错误页面
			req.setAttribute("ERRORMSG", "注册失败");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);

			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("======UserServlet--doPost()");
		doGet(req, resp);
	}

}
