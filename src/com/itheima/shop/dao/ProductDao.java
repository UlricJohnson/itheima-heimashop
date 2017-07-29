package com.itheima.shop.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.shop.entity.Category;
import com.itheima.shop.entity.Product;
import com.itheima.shop.util.JdbcUtil;

/**
 * 商品相关操作
 * 
 * 展示
 * 
 * @author Ulric
 * 
 */

public class ProductDao {
	private QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());

	public ProductDao() {
	}

	/**
	 * 展示商品详情
	 * 
	 * 根据pid查找商品
	 * 
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> getProductByPid(String pid) throws SQLException {
		System.out.println("======ProductDao--getProductByPid()");

		String sql = "select * from product p,category c"
				+ " where p.`pid` = ?"
				+ " and p.`cid` = c.`cid`;";

		Object[] params = { pid };
		return runner.query(sql, new MapListHandler(), params);

	}

	/**
	 * 根据cid获取相应商品的数量
	 * 
	 * @throws SQLException
	 */
	public int countProductByCid(int cid) throws SQLException {
		String sql = "select count(p.`pid`) from product p where p.`cid`=?;";

		Object[] params = { cid };
		int total = ((Long) runner.query(sql, new ScalarHandler(1), params))
				.intValue();
		return total;
	}

	/**
	 * 根据商品类别cid获取全部商品
	 * 
	 * @param cid
	 *            -商品类别id
	 * @param start
	 *            -从索引为start的数据开始查找
	 * @param length
	 *            -查询length条数据
	 * 
	 * @throws SQLException
	 */
	public List<Product> getProductByCid(int cid, int start, int length)
			throws SQLException {
		System.out.println("======ProductDao--getProductByCid()");

		String sql = "select p.`pid`,p.`pname`,p.`market_price`,p.`shop_price`,p.`pimage`,"
				+ " p.`pdate`,p.`is_hot`,p.`pdesc`,p.`pflag`"
				+ " from category c,product p"
				+ " where p.`cid` = c.`cid`"
				+ " and c.`cid`=?" + " limit ?,?;";

		Object[] params = { cid, start, length };

		return runner.query(sql, new BeanListHandler<Product>(Product.class),
				params);
	}

	/**
	 * 获取导航条中的分类信息
	 */
	public List<Category> getNavBar() throws ServletException, IOException,
			SQLException {
		System.out.println("======ProductDao--getNavBar()");

		String sql = "select *" + " from category c" + " order by c.`cid`;";

		Object[] params = null;
		return runner.query(sql, new BeanListHandler<Category>(Category.class),
				params);

	}

	/**
	 * 查找热门商品
	 */
	public List<Product> getHotProducts() throws SQLException {
		System.out.println("======ProductDao--getHotProducts()");

		String sql = "select p.`cid`,p.`pname`,p.`market_price`,p.`shop_price`,p.`pimage`,p.`pdate`,"
				+ "p.`is_hot`,p.`pdesc`,p.`pflag` "
				+ "from product p,category c "
				+ "where p.`cid`=c.`cid` "
				+ "and p.`is_hot`=1 " + "and p.`pflag`=1;";

		Object[] params = null;

		return runner.query(sql, new BeanListHandler<Product>(Product.class),
				params);

	}

	/**
	 * 查找最新商品
	 */
	public List<Product> getLatestProducts() throws SQLException {
		System.out.println("======ProductDao--getLatestProducts()");

		String sql = "select p.`pid`,p.`pname`,p.`market_price`,p.`shop_price`,p.`pimage`,p.`pdate`,p.`is_hot`,p.`pdesc`,p.`pflag`"
				+ " from product p,category c"
				+ " where p.`cid`=c.`cid`"
				+ " and p.`pflag`=1" + " order by p.`pdate` desc;";

		Object[] params = null;

		List<Product> allProductsList = runner.query(sql,
				new BeanListHandler<Product>(Product.class), params);

		return allProductsList;
	}

}
