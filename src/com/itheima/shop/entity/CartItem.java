package com.itheima.shop.entity;

/**
 * 购物项实体类
 * 
 * 一个购物项对象代表购物车页面中的一种商品
 * 
 * @author Ulric
 * 
 */

public class CartItem {
	private Product product;// 商品
	private int num;// 商品数量
	private double subTotal;// 本类商品小计

	public CartItem() {
	}

	/****** get set ******/
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

}
