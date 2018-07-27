package com.jk.ptk;

import java.util.Base64;

import com.jk.ptk.util.RandomUtil;

public class Test {
	public static void main(String[] args) {
		try {
			byte[] rand = RandomUtil.getRandomBytes(6);
			String s1 = Base64.getEncoder().encodeToString(rand);
			String s2 = RandomUtil.toNoramlizedHexString(rand);
			System.out.println(s1);
			System.out.println(s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
