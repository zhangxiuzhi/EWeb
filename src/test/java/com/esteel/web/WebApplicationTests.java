package com.esteel.web;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esteel.web.service.ContactClient;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {
	 @Autowired
		ContactClient contactClient;
	 	@Test
	    public void testSendMail() {
	    	boolean sendMail = contactClient.sendMail("1091871263","hello world","今天居然20号了");
	    	System.out.println(sendMail);
	    }
}
