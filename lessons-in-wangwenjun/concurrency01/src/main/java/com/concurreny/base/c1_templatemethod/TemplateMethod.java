package com.concurreny.base.c1_templatemethod;

/**
 * Description: 模板方法示例--线程的启动采用了该种方式 <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/7 13:42
 * @Version: 1.0
 */
public class TemplateMethod {

    /**
     * Description: 这个就是类似于start方法 <BR>
     * <p>
     * 为什么使用final修饰 ?
     * 子类可以继承而无法修改, 这是为了确保父类上的一些固定逻辑不被子类重写覆盖掉
     *
     * @param message:
     * @return void
     * @author zhao.song    2020/5/7 13:43
     */
    public final void print(String message) {
        System.out.println("#################");
        wrapPrint(message);
        System.out.println("#################");
    }

    /**
     * Description: 这个方式类似于thread的run方法--供给用户实现自己的逻辑 <BR>
     *
     * @param message:
     * @return void
     * @author zhao.song    2020/5/7 13:45
     */
    protected void wrapPrint(String message) {

    }


    /**
     * Description: 线程的模板方法 <BR>
     *
     * @param args:
     * @return void
     * @author zhao.song    2020/5/7 13:49
     */
    public static void main(String[] args) {
        TemplateMethod template = new TemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println(message);
            }
        };

        template.print("*Hello Thread*");
    }
}
