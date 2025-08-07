package com.concurreny.demo;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.expression.ExpressionException;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/7 15:12
 * @Version: 1.0
 */
public class TryFunctionProgramDemo {

    public static void main(String[] args) throws Exception {
        /*Integer orElseThrow = testTry().getOrElseThrow(()->{
            throw new RuntimeException("ssssss");});
        System.out.println(orElseThrow);*/

        Either orElseGet = testEither(2);
        boolean left = orElseGet.isLeft();
        Exception ex = (Exception) orElseGet.getLeft();
        System.out.println(ex.getMessage());
        Integer orElseThrow2 = testTry().getOrElseThrow((e) -> {
            throw new RuntimeException(e.getMessage());
        });
        System.out.println(orElseThrow2);


    }


    public static Try<Integer> testTry() throws Exception {
        return Try.of(() -> {
            return 1 / 0;
        });
    }

    public static Either<Exception, Integer> testEither(Integer num) {
        if (num == 1) {
            return Either.right(num);
        }
        return Either.left(new Exception("sssss77777s"));
    }

}
