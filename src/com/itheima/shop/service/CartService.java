package com.itheima.shop.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.shop.dao.ProductDao;
import com.itheima.shop.entity.Cart;
import com.itheima.shop.entity.CartItem;
import com.itheima.shop.entity.Category;
import com.itheima.shop.entity.Product;

/**
 * 本项目中的购物车暂时只保存在session中
 * 
 * 而不写入数据库
 * 
 * 故没有Cart的Dao层
 * 
 * @author Ulric
 * 
 */

public class CartService {

	public CartService() {
	}

	/**
	 * 清空购物车
	 */
	public void clearCart(Cart cart) {
		// 清空Cart里面的Map
		cart.getItems().clear();

		// 更新Cart
		cart.updateCart();
	}

	/**
	 * 删除商品项
	 */
	public void deleteProd(int pid, Cart cart) {
		System.out.println("=======CartService--deleteProd()");

		// 删除集合中的购物项
		cart.getItems().remove(pid);

		// 更新购物车的商品总数和总价
		cart.updateCart();
	}

	/**
	 * 添加商品到购物车
	 * 
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void addProduct(int pid, int quantity, Cart cart)
			throws SQLException, IllegalAccessException,
			InvocationTargetException {
		System.out.println("=======CartService--addProduct()##pid=" + pid
				+ "##quantity=" + quantity);

		// 获取购物车中的购物项集合
		Map<Integer, CartItem> items = cart.getItems();
		CartItem item = null;// 本类商品的购物项

		// 若购物车中已经存在该类商品，则更新数量和小计
		if (items.containsKey(pid)) {
			System.out.println("购物车中已存在pid为" + pid + "的商品");
			item = items.get(pid);

			// 更新本商品数量
			item.setNum(item.getNum() + quantity);

			// 更新本商品小计，数量(更新后) * 单价
			item.setSubTotal(item.getNum() * item.getProduct().getShop_price());
		} else {
			// 如果购物车中没有该类商品则创建添加该商品类
			System.out.println("购物车中不存在pid为" + pid + "的商品");

			// 根据 pid 联合 product 表和 category 表查询该商品和商品类别数据
			Map<String, Object> datas = new ProductDao().getProductByPid(
					String.valueOf(pid)).get(0);// 查询到的是List<Map<>>，获取第1个Map

			// 根据查询到的 product 表和 category 表的数据封装成 product 类和 category 类
			Category cate = new Category();
			Product product = new Product();
			BeanUtils.populate(cate, datas);
			BeanUtils.populate(product, datas);
			product.setCategory(cate);

			// 新建一个本类商品的购物项
			item = new CartItem();
			item.setNum(quantity);// 商品数量设置为quantity个
			item.setProduct(product);// 设置商品
			item.setSubTotal(product.getShop_price() * item.getNum());// 设置小计，单价*数量

			// 把新建的购物项添加到购物车
			items.put(product.getPid(), item);
		}

		// 更新购物车中的商品数量和总价
		int num = 0;// 商品总数
		double tot = 0D;// 商品总价
		for (Integer pid1 : items.keySet()) {
			// 累加全部购物项的商品数量
			num += items.get(pid1).getNum();

			// 把全部购物项的小计累加
			tot += items.get(pid1).getSubTotal();
		}
		cart.setNum(num);
		cart.setTotal(tot);

		System.out.println("=======CartService--addProduct()~~结束##pid=" + pid
				+ "##quantity=" + quantity + "##购物车num:" + cart.getNum()
				+ "##total:" + cart.getTotal());

		// return cart;
	}

}
