## 1.Synchronized锁
使用synchronized，当多个线程尝试获取锁时，未获取到锁的线程会不断的尝试获取锁，而不会发生中断，这样会造成性能消耗。
而ReentranLock的lockInterruptibly()可以优先相应中断。举例：两个线程A，B，A获得了锁（A.lockInterruptibly（）），B在请求锁的时候发生阻塞，如果调用B.interrupt()，会中断B的阻塞。