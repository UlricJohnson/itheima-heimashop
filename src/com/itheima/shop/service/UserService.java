package com.itheima.shop.service;

import java.sql.SQLException;

import com.itheima.shop.dao.UserDao;
import com.itheima.shop.entity.User;
import com.itheima.shop.util.MailUtil;
import com.itheima.shop.util.Md5Util;
import com.itheima.shop.util.UuidUtil;

/**
 * 用户相关操作处理
 * 
 * @author Ulric
 * 
 */

public class UserService {

	public UserService() {
	}

	/**
	 * 用户登录
	 * 
	 * 不存在该用户则返回false
	 * 
	 * @throws Exception
	 */
	public User login(String username, String password) throws Exception {
		System.out.println("======UserService--login()");

		// 把密码加密
		password = Md5Util.encodeByMd5(password);

		UserDao dao = new UserDao();

		return dao.login(username, password);
	}

	/**
	 * 激活账号
	 * @throws SQLException 
	 */
	public void active(String code) throws SQLException {
		System.out.println("======UserService--active()");

		new UserDao().active(code);
	}

	/**
	 * 用户注册
	 * 
	 * @throws Exception
	 */
	public void register(User user) throws Exception {
		System.out.println("======UserService--register()");

		/**
		 * 在servlet层未进行的操作：1)加密密码; 2)设置激活状态state; 3)设置激活码uuid
		 */
		// 加密
		String pwdMD5 = Md5Util.encodeByMd5(user.getPassword());
		user.setPassword(pwdMD5);

		// 设置激活状态为N
		user.setState("N");

		// 设置激活码
		String uuid = UuidUtil.getUuid();
		user.setCode(uuid);

		UserDao dao = new UserDao();
		dao.register(user);

		// 使用工具类发送邮件/短信
		String toEmail = "13428894211@163.com";// 邮箱，这里写自己的邮箱
		String emailMsg = user.getName() + "您好！您已注册成功,请点击"
				+ "<a href='http://127.0.0.1:8080/heimashop/UserServlet"
				+ "?action=active&code=" + user.getCode() + "'>链接</a>激活";
		MailUtil.sendMail(toEmail, emailMsg);
	}

}
