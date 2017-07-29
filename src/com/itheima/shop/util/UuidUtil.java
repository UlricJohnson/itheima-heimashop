package com.itheima.shop.util;

import java.util.UUID;

/**
 * 生成激活码
 * 
 * @author Ulric
 * 
 */

public final class UuidUtil {
	private UuidUtil() {
	}

	public static String getUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 测试
	 */
	public static void main(String[] args) {
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
	}
}
