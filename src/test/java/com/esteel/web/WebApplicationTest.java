package com.esteel.web;

import com.esteel.web.service.ContactClient;
import com.taobao.common.tfs.TfsManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-12-01
 * Time: 11:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTest {

    @Autowired
    TfsManager tfsManager;
    @Autowired
	ContactClient contactClient;

    @Test
    public void tfsPutTest(){

        String file = tfsManager.saveFile("e:\\2.jpg", null, "jpg");
        System.out.println(file);
        boolean b1 = tfsManager.fetchFile("T11RZTB5dT1R4bAZ6K", "jpg", "e:\\3.jpg");
        Assert.assertEquals(b1,true);
    }
}
