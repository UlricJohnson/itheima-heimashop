package com.itheima.shop.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 */
public class Order implements java.io.Serializable {
	private int oid;// 编号
	private java.util.Date ordertime;// 下单时间
	private double total;// 订单中商品总价
	private int state;// 订单状态，1表示已支付，0表示未支付
	private String address;// 送货地址
	private String name;// 收货人
	private String telephone;// 手机号
	private User user;// 订单所属用户
	private List<OrderItem> orderItemList = new ArrayList<OrderItem>();// 订单项集合

	public Order() {
	}

	/******* get set *******/
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public java.util.Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(java.util.Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
