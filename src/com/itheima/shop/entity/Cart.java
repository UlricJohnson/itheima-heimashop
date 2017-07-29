package com.itheima.shop.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 购物车实体类
 * 
 * @author Ulric
 * 
 */

public class Cart {
	// 购物项集合<商品编号，购物项>，使用Map是为了方便对购物项进行增删操作
	private Map<Integer, CartItem> items;
	private int num;// 商品数量总计
	private double total;// 商品价格总计

	public Cart() {
		/**
		 * 一开始就初始化购物项集合的原因： 当新用户第1次购物时需要创建Cart对象，
		 * 而引用变量的默认初始化值为null，而当进行加入购物车操作时需要操作items
		 */

		items = new HashMap<Integer, CartItem>();
	}

	/**
	 * 更新购物车的商品总数和总价
	 */
	public void updateCart() {
		System.out.println("===========Cart类--updateCart()");

		int count = 0;// 统计商品总数
		double tot = 0D;// 统计商品总价

		for (Entry<Integer, CartItem> itemEntry : items.entrySet()) {
			CartItem item = itemEntry.getValue();// 获得每一个购物项

			count += item.getNum();// 购物项的商品数量
			tot += item.getSubTotal();// 购物项小计
		}

		// 更新总数量和总价
		this.setNum(count);
		this.setTotal(tot);
	}

	/****** get set *****/
	public Map<Integer, CartItem> getItems() {
		return items;
	}

	public void setItems(Map<Integer, CartItem> items) {
		this.items = items;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
