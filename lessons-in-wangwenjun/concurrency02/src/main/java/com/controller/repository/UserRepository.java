package com.controller.repository;

import com.controller.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/20 9:54
 * @version: 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserTest, Integer>, JpaSpecificationExecutor<UserTest> {

}
