package com.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/***********************
 * Description: TODO <BR>
 * @author: zhao.song
 * @date: 2020/8/17 15:17
 * @version: 1.0
 ***********************/
public class RegixPatternUtils {


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String source = "（经济）（3）智能高铁“新标杆”！京雄城际铁路全线轨道贯通(2)";
        String regex = "[（|(][0-9]{1,2}[）|)]";

        source = source.replaceAll(regex, "");
        System.out.println(source);
        List<String> rightTypes = Arrays.asList("a", "b", "c");
        List<String> cloneRightTypes = new ArrayList<>();
        CollectionUtils.addAll(cloneRightTypes, new Object[rightTypes.size()]);
        Collections.copy(cloneRightTypes, rightTypes);
        List<UserDemo> srcList = new ArrayList<>();
        UserDemo kev = new UserDemo(1, "kev", 122);
        srcList.add(kev);
        Object o1 = BeanUtils.cloneBean(srcList);
        System.out.println(o1);
        System.out.println("============before copy===========");
        System.out.println("->source:" + rightTypes);
        System.out.println("->target:" + cloneRightTypes);
        cloneRightTypes.add("new");
        System.out.println("============after copy===========");
        System.out.println("->source:" + rightTypes);
        System.out.println("->target:" + cloneRightTypes);

    }
}
