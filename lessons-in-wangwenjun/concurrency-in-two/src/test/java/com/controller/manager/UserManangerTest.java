package com.controller.manager;

import com.concurrency2.controller.manager.UserManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/20 10:00
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserManangerTest {


    @Autowired
    private UserManager userManager;

    @Test
    public void transactionalTest() {
        userManager.testSaveWithTransaction();
    }
}
