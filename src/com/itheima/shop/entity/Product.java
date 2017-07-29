package com.itheima.shop.entity;

/**
 * 商品
 */
public class Product implements java.io.Serializable {
	private int pid;// 编号
	private String pname;// 商品名
	private double market_price;// 市场价
	private double shop_price;// 商城价
	private String pimage;// 图片路径
	private java.util.Date pdate;// 上架时间
	private int is_hot;// 是否热门，1表示热门，0表示不热门
	private String pdesc;// 商品描述
	private int pflag;// 是否上架，1表示上架，0表示下架
	private Category category;// 商品所属类别

	public Product() {
	}

	/****** get set *****/
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	public double getShop_price() {
		return shop_price;
	}

	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public java.util.Date getPdate() {
		return pdate;
	}

	public void setPdate(java.util.Date pdate) {
		this.pdate = pdate;
	}

	public int getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public int getPflag() {
		return pflag;
	}

	public void setPflag(int pflag) {
		this.pflag = pflag;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
