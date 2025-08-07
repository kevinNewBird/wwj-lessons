package com.controller.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/7/20 9:46
 * @version: 1.0
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
@Setter
@Getter
public class UserTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String username;

    @Column(name = "age")
    private Integer age;

}
