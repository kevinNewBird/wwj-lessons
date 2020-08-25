package com.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
@Table(name = "tb_user")
//@Setter
//@Getter
public class UserDemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private Integer age;

}
