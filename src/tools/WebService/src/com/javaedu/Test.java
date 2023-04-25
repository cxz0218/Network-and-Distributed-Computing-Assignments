package com.javaedu;

public class Test {

	/**
	 * wximport自动生成代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		HelloService hs = new HelloService();
		Hello hello = hs.getHelloPort();
		System.out.println(hello.hello("Peter"));
		
		System.out.println(hello.sayHi("Mary", 2));
	}

}
