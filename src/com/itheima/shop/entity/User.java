package com.itheima.shop.entity;

/**
 * 用户
 */
public class User implements java.io.Serializable {
	private int uid;// 编号
	private String username;// 登陆用户名zhaojun
	private String password;// 登陆密码
	private String name;// 姓名赵君
	private String email;// 邮件
	private String telephone;// 手机号
	private String birthday;// 生日
	private String sex;// 性别
	private String state;// 激活码状态，Y表示激活，N表示未激活，只有激活后，方可登陆
	private String code;// 激活码

	public User() {
	}

	/***** get set *****/
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
