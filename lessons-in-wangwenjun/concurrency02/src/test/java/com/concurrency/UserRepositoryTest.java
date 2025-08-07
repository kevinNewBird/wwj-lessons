package com.concurrency;

import com.controller.entity.UserTest;
import com.controller.repository.UserRepository;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Optional;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/9/3 20:54
 * @version: 1.0
 ***********************/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        final Optional<UserTest> userTestOption = userRepository.findById(1);
        if (userTestOption.isPresent()) {
            final UserTest userTest = userTestOption.get();
            final JSONObject jsonObject = JSONObject.fromObject(userTest.getUsername());
            System.out.println(jsonObject);
            for (Object configKey : jsonObject.keySet()) {
                if (configKey == null || StringUtils.isEmpty(configKey.toString().trim())) {
                    continue;
                }
                String sConfigKey = configKey.toString();
                String sConfigValue = jsonObject.getString(sConfigKey);
                System.out.println(sConfigKey + "-" + sConfigValue);
            }
        }
    }
}
