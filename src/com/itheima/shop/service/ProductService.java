package com.itheima.shop.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.BeanUtils;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.shop.dao.ProductDao;
import com.itheima.shop.entity.Category;
import com.itheima.shop.entity.Page;
import com.itheima.shop.entity.Product;
import com.itheima.shop.util.JedisUtil;

/**
 * 商品相关操作
 * 
 * 展示
 * 
 * @author Ulric
 * 
 */

public class ProductService {

	public ProductService() {
	}

	/**
	 * 展示商品详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Product getProductByPid(String pid) throws SQLException,
			IllegalAccessException, InvocationTargetException {
		System.out.println("======ProductService--getProductByPid()");

		List<Map<String, Object>> properties = new ProductDao()
				.getProductByPid(pid);

		//根据属性封装成两个Product和Category对象，若属性不符合的赋值为null
		Product product = new Product();
		Category category = new Category();
		BeanUtils.populate(product, properties.get(0));
		BeanUtils.populate(category, properties.get(0));
		
		//把Category对象封装进product中
		product.setCategory(category);

		return product;
	}

	/**
	 * 根据商品类别cid获取相应的商品
	 * 
	 * @param cid
	 *            -商品类别id
	 * @param start
	 *            -开始显示的起始数
	 * @param length
	 *            -显示的数据条数
	 * @throws SQLException
	 */
	public Page getProductByCid(int cid, int start, int length)
			throws SQLException {
		System.out.println("======ProductService--getProductByCid()");

		ProductDao dao = new ProductDao();

		// 根据cid获取全部商品
		List<Product> prodList = dao.getProductByCid(cid, start, length);

		// 创建page对象并赋值
		Page page = new Page();
		// 1. 当前页数 = 数据库中数据起始数(从0开始) %每页显示的数据数+1
		page.setCurrPage(start % length + 1);
		// 2. 本页面要显示的商品集合
		page.setProdList(prodList);
		// 3. 本页面显示的商品数
		page.setRecordPerPage(length);
		// 4. 本类商品的总数
		page.setTotRecords(dao.countProductByCid(cid));
		// 5. 总页数 = (总记录数 / 每页显示的商品数) --> 向上取整
		int totPage = (int) Math.ceil(page.getTotRecords() * 1.0
				/ page.getRecordPerPage());
		page.setTotPage(totPage);

		return page;
	}

	public static void main(String[] args) {
		System.out.println(1 % 6);
	}

	/**
	 * 获取导航条中的分类信息
	 */
	public List<Category> getNavBar() throws ServletException, IOException,
			SQLException {

		System.out.println("======ProductService--getNavBar()");

		// 商品分类集合
		List<Category> cateList = new ArrayList<Category>();

		// 从redis中查找，使用默认的0号库
		Jedis jedis = JedisUtil.getJedis();
		// 查看服务是否运行
		System.out.println(jedis.ping());// PONG
		// 获取导航条数据
		String data = jedis.get("categories");

		System.out.println("===========redis中导航条数据：" + data);

		Gson gson = new Gson();

		// 如果redis数据库中存在导航条信息则把json字符串转成对象集合
		if (data != null) {
			cateList = gson.fromJson(data, new TypeToken<List<Category>>() {
			}.getType());

		} else {
			// 没找到就去mysql数据库找
			ProductDao dao = new ProductDao();
			cateList = dao.getNavBar();// 商品分类对象集合

			// 转成json字符串再存到redis中，取出来的时候也可以转为对象
			String cateJsonStr = gson.toJson(cateList);

			// 把json字符串存到redis中
			jedis.set("categories", cateJsonStr);
		}
		return cateList;
	}

	/**
	 * 查找热门商品
	 */
	public List<Product> getHotProducts() throws SQLException {
		System.out.println("======ProductService--getHotProducts()");

		List<Product> hotProducts = new ProductDao().getHotProducts();

		// 限制个数，最多显示12个
		if (hotProducts.size() > 12) {
			for (int i = 12; i < hotProducts.size(); i++) {
				hotProducts.remove(i);
				i--;
			}
		}

		return hotProducts;
	}

	/**
	 * 查找最新商品
	 */
	public List<Product> getLatestProducts() throws SQLException {
		System.out.println("======ProductService--getLatestProducts()");

		List<Product> latestProducts = new ProductDao().getLatestProducts();

		// 限制个数，最多显示12个
		if (latestProducts.size() > 12) {
			for (int i = 12; i < latestProducts.size(); i++) {
				latestProducts.remove(i);
				i--;
			}
		}

		return latestProducts;
	}

}
