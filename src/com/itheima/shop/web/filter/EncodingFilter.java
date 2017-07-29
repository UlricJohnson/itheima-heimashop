package com.itheima.shop.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 对全网站的编码进行过滤
 * 
 * @author Ulric
 * 
 */

public class EncodingFilter implements Filter {

	public EncodingFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("======EncodingFilter");

		// 把请求和响应对象转成Http类型
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;

		// 获取请求方式
		String method = httpReq.getMethod();

		// 根据请求方式的不同采用不同方法进行编码统一
		if (method.equalsIgnoreCase("get")) {
			// 通过用自定义请求类对象代替原请求对象的方式实现编码过滤
			chain.doFilter(new MyHttpServletRequest(httpReq), httpResp);
		} else if (method.equalsIgnoreCase("post")) {
			// 重置请求体中的内容的编码
			httpReq.setCharacterEncoding("UTF-8");

			// 通过请求
			chain.doFilter(httpReq, httpResp);
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}

/**
 * 自定义请求类
 */
class MyHttpServletRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);// 不能删
		this.request = request;
	}

	/**
	 * 重写getParameter()方法
	 */
	@Override
	public String getParameter(String name) {
		// 获取请求属性
		String value = request.getParameter(name);

		if (value != null && value.trim().length() > 0) {
			try {
				value = value.trim();
				byte[] buf = value.getBytes("ISO8859-1");
				return new String(buf, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		} else {
			return null;
		}
	}

	/*@Override
	public Map<String, String[]> getParameterMap() {
		// 声明Map
		HashMap<String, String[]> paramsMap = new HashMap<String, String[]>();

		// 获取属性名集合
		Enumeration<String> names = request.getParameterNames();

		int count=0;
		while (names.hasMoreElements()) {
			//获取属性名
			String name = names.nextElement();
			//利用原来的request获取属性值，此处为ISO-8859-1编码
			String value=request.getParameter(name);
			paramsMap.put(name, value);
		}

		return super.getParameterMap();
	}*/
	
	
	@Override
	public Map<String, String[]> getParameterMap() {
		//Map的value是乱码
		Map<String, String[]> map = request.getParameterMap();
		//迭代
		for(Entry<String,String[]> entry : map.entrySet()){
			//values = {"??","??","??"}
			String[] values = entry.getValue();
			//迭代
			for(int i=0;i<values.length;i++){
				try {
					//乱码
					String value = values[i];
					byte[] buf = value.getBytes("ISO8859-1");
					//正码
					value = new String(buf,"UTF-8");
					values[i] = value;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}//in for
		}//out for
		return map;
	}
}
