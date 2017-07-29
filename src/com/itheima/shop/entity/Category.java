package com.itheima.shop.entity;

/**
 * 类别
 */
public class Category implements java.io.Serializable {
	private int cid;// 编号
	private String cname;// 类别名

	public Category() {
	}

	/***** get set ****/
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
}