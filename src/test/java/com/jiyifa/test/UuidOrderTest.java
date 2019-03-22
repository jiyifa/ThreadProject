package com.jiyifa.test;

import com.jiyifa.service.IorderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟并发操作(使用UUID无法控制并发)
 * @Rollback和@commit替代@TransactionConfiguration（defaultRollback=true）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
@Rollback
@Commit
public class UuidOrderTest {

    @Autowired
//    @Qualifier("uuidOrderServiceImpl")
    @Qualifier("snowflakeOrderServiceImpl")
//    @Qualifier("redisOrderServiceImpl")
    private IorderService orderService;

    private static final int threadNum = 100;

    /**
     * 线程同步工具
     * 100 -1 = 99 -98 --- =0
     */
    private static CountDownLatch cdl = new CountDownLatch(threadNum);
    @Test
    public void test() throws InterruptedException {
        System.out.println("===============================test start");
        for (int i = 0; i < threadNum; i++) {
            new Thread(new orderThread()).start();
            //计数,threadNum-1, =0，唤醒所有线程
            cdl.countDown();
        }
        Thread.currentThread();
        Thread.sleep(3000);
        System.out.println("================================test end");
    }
    class orderThread implements Runnable{

        public void run() {
            try {
                //等待线程同步执行
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            orderService.orderId();
        }
    }
}
