package com.itheima.shop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.shop.entity.User;
import com.itheima.shop.util.JdbcUtil;

/**
 * 用户相关操作处理
 * 
 * @author Ulric
 * 
 */

public class UserDao {
	private QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());

	public UserDao() {
	}

	/**
	 * 用户登录
	 * 
	 * 存在该用户则返回该用户，否则返回null
	 * 
	 * @throws SQLException
	 */
	public User login(String username, String password) throws SQLException {
		System.out.println("======UserDao--login()");

		String sql = "select * from user " + "where username = '" + username
				+ "' and password = '" + password + "';";

		Object[] params = null;

		return runner.query(sql, new BeanHandler<User>(User.class), params);

	}

	/**
	 * 激活账号
	 * @throws SQLException 
	 */
	public void active(String code) throws SQLException {
		System.out.println("======UserDao--active()");

		String sql = "update `user` set state='Y' where `code`=?";

		Object[] params = { code };

		runner.update(sql, params);

	}

	/**
	 * 用户注册
	 * 
	 * @throws SQLException
	 */
	public void register(User user) throws SQLException {
		System.out.println("======UserDao--register()");

		String sql = "insert into user "
				+ "(username,password,name,email,telephone,birthday,sex,state,code) "
				+ "values (?,?,?,?,?,?,?,?,?);";

		Object[] params = { user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), user.getState(),
				user.getCode() };

		runner.update(sql, params);
	}

}
