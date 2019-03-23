package com.jiyifa.service.impl;

import com.jiyifa.service.IorderService;
import com.jiyifa.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service("redisOrderServiceImpl")
public class RedisOrderServiceImpl implements IorderService {
    @Autowired
    private RedisUtils redis;

    /**
     * 产生订单号
     */
    public void orderId() {
        // key = 系统名: + 模块：+功能： + key
        String key = "system:order:id";
        String prefix = getPreFix(new Date());
        long id = redis.getIncr(key,-1);
        //加工
        System.out.println("insert into order_id(id) value('"+ prefix + String.format("%1$05d",id) +"');");
    }

    /**
     *  通过时间生成前缀 - 可根据业务自定义
     * @param date
     * @return
     */
    private String getPreFix(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String dayfmt = String.format("%1$03d",day);
        String hourfmt = String.format("%1$02d",hour);
        return (year - 2000) + dayfmt + hourfmt;
    }
}
