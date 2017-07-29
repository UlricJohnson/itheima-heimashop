package com.itheima.shop.entity;

/**
 * 订单项
 */
public class OrderItem implements java.io.Serializable {
	private Product product;// 商品
	private int num;// 商品数量
	private double subTotal;// 商品小计
	private Order order;// 所属订单

	public OrderItem() {
	}

	/******* get set *******/
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
