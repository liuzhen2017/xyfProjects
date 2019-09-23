package com.xinyunfu.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.microservice.Runner;
import com.rnmg.jace.utils.SftpUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Runner.class )
public class SFTPTest {
	
	@Autowired
	SftpUtil sftutil;
	@Test
	public void testUpload() {
		sftutil.connect();
		boolean isupload =sftutil.uploadFile("/20190703", "ThisTest2.txt", new File("C:\\Users\\pc\\Desktop\\基础应用需求.txt"));
		System.out.println(isupload);
	}
	@Test
	public void testDown() {
		try {
			sftutil.connect();
			boolean isupload =sftutil.downloadFile("/20190703/", "ThisTest.txt",new FileOutputStream(new File("C:\\Users\\pc\\Desktop\\test11.txt")));
			System.out.println(isupload);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
