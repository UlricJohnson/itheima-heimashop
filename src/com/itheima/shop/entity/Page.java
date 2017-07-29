package com.itheima.shop.entity;

import java.util.List;

/**
 * 商品展示分页
 * 
 * @author Ulric
 * 
 */

public class Page {
	private List<Product> prodList; // 要展示的商品，默认展示数量为6个
	private int currPage; // 当前页数
	private int totPage; // 总页数
	private int recordPerPage;// 每页要显示的记录数
	private int totRecords;// 总记录数

	public Page() {
	}

	/***** get set *****/
	public List<Product> getProdList() {
		return prodList;
	}

	public void setProdList(List<Product> prodList) {
		this.prodList = prodList;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotPage() {
		return totPage;
	}

	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public int getTotRecords() {
		return totRecords;
	}

	public void setTotRecords(int totRecords) {
		this.totRecords = totRecords;
	}

}
