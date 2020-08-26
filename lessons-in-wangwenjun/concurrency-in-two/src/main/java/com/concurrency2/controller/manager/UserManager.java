package com.concurrency2.controller.manager;

import com.concurrency2.controller.entity.UserTest;
import com.concurrency2.controller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/20 9:58
 * @version: 1.0
 */
@Component
public class UserManager {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void testSaveWithTransaction() {
        UserTest userTest = new UserTest(1,"kevin",19);
        userRepository.save(userTest);

        ExecutorService executor= Executors.newSingleThreadExecutor();
        executor.submit(()->{
            UserTest tom = new UserTest(2, "tom", 21);
            userRepository.save(tom);
            throw new IllegalArgumentException("测试异常事务回滚");
        });

        //新开线程中的不会回滚
//        throw new IllegalArgumentException("测试异常事务回滚");
    }


    public static void main(String[] args) throws InterruptedException {
        /*Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("->循环中...");
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.stop();*/
        java.util.List<String> l1 = Stream.of("alf", "sdd2").collect(Collectors.toList());
//        java.util.List<String> l2 = Stream.of("alfa", "sdd2a").collect(Collectors.toList());
        ArrayList<Object> l2 = new ArrayList<>();
        l2.retainAll(l1);
        System.out.println(l2);
        System.out.println(l1);

    }
}
