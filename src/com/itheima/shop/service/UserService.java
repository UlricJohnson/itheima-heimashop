package com.itheima.shop.service;

import com.itheima.shop.dao.UserDao;
import com.itheima.shop.entity.User;
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
	}

}
