package com.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/7 17:24
 * @version: 1.0
 */
public class BeanPropertyUtils {

    public static void main(String[] args) throws IntrospectionException {
        UserBean target = new UserBean(11, "sss", 120);
        UserBean source = new UserBean(12, "", 130);
        copyNotNullProperties(source, target);
        System.out.println(target);
    }


    public static void copyNotNullProperties(Object source, Object target) throws IntrospectionException {
        Assert.notNull(source, "source object is null!");
        Assert.notNull(target, "target object is null!");

        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass(), Object.class);
        PropertyDescriptor[] targetPds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);

                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (value != null && !StringUtils.isEmpty(value.toString())) {
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }

    }

    private static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) throws BeansException {
        return org.springframework.beans.BeanUtils.getPropertyDescriptor(clazz, propertyName);
    }
/*    Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

    Class<?> actualEditable = target.getClass();
		if (editable != null) {
        if (!editable.isInstance(target)) {
            throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                    "] not assignable to Editable class [" + editable.getName() + "]");
        }
        actualEditable = editable;
    }
    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
        Method writeMethod = targetPd.getWriteMethod();
        if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
            PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
            if (sourcePd != null) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod != null &&
                        ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    }
                    catch (Throwable ex) {
                        throw new FatalBeanException(
                                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                    }
                }
            }
        }
    }*/
}
