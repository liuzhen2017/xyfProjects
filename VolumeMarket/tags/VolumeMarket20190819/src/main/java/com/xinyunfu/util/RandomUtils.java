package com.xinyunfu.util;

import java.util.Random;

/**
 * @author jace
 *	随机数
 */
public class RandomUtils {

	
	
	public static int count1 = 0; 
	public static int count2 = 0; 
	public static int count3 = 0; 
	public static long counter1 = System.currentTimeMillis();
	public static long counter2 = System.currentTimeMillis();
	public static long counter3 = System.currentTimeMillis();
	
	
	static {
		count1 = random(4);
		count2 = random(5);
		count3 = random(6);
	}

	public static int random(int length) {
		return (int) Math.floor(new Random().nextDouble() * Math.pow(4,length));
	}
	
	
	public static int getCount1() {
		
		return count1+getSymbol();
	}
	
	
	public static int getCount2() {
		return count2+getSymbol();
	}
	
	
	public static int getCount3() {
		return count3+getSymbol();
	}
	
	
	public static int getSymbol() {
		int symbol = 0;
		if(new Random().nextInt(9)%2 == 0) {
			symbol = 1;
		}else {
			symbol = -1;
		}
		return symbol * random(2);
	}
	
}
