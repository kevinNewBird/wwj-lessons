package com.tree;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 树状数据结构Stream流封装 <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/22 9:46
 * @Version: 1.0
 */
public class UmsPermissionTest {

    public static void main(String[] args) {
        //构建测试数据{@link:com.tree.UmsPermissionTest.MockList}

        //初始化结果返回
        List<UmsPermissionNode> list = new ArrayList<>();

        //转化为树状结构数据
        MockList.parenPermissionList.stream().forEach(parent -> list.add(convert(parent, MockList.permissionList)));

        System.out.println(JSON.toJSONString(list));
        System.out.println("====================");
        list.stream().forEach(ele -> System.out.println(ele));
    }

    private static UmsPermissionNode convert(UmsPermission permission, List<UmsPermission> permissionList) {
        UmsPermissionNode node = new UmsPermissionNode();
        BeanUtils.copyProperties(permission, node);
        List<UmsPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> convert(subPermission, permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * Description: 定义获取树形结构的方法 <BR>
     * 我们先过滤出pid为0的顶级权限，然后给每个顶级权限设置其子级权限
     * ，covert方法的主要用途就是从所有权限中找出相应权限的子级权限。
     *
     * @param :
     * @author zhao.song    2020/5/22 9:53
     * @return java.util.List<com.tree.UmsPermissionNode>
     */
    /*public List<UmsPermissionNode> treeList() {
        List<UmsPermission> permissionList = permissionMapper.selectByExample(new UmsPermissionExample());
        List<UmsPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission, permissionList)).collect(Collectors.toList());
        return result;
    }*/


    //Mock数据类
    private static class MockList {
        public static List<UmsPermission> permissionList;
        public static List<UmsPermission> parenPermissionList;

        static {
            UmsPermission pUmsPermission1 = UmsPermission.builder().id(1l).pid(0l).build();
            UmsPermission pUmsPermission2 = UmsPermission.builder().id(2l).pid(0l).build();
            UmsPermission cUmsPermission1 = UmsPermission.builder().id(4l).pid(1l).build();
            UmsPermission cUmsPermission2 = UmsPermission.builder().id(6l).pid(1l).build();
            UmsPermission ccUmsPermission1 = UmsPermission.builder().id(8l).pid(4l).build();

            permissionList = Stream.of(pUmsPermission1, ccUmsPermission1, cUmsPermission1, cUmsPermission2)
                    .collect(Collectors.toList());

            parenPermissionList = Stream.of(pUmsPermission1, pUmsPermission2).collect(Collectors.toList());
        }
    }
}
