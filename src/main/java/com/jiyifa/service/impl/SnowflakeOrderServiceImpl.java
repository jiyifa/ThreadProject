package com.jiyifa.service.impl;

import com.jiyifa.service.IorderService;
import com.jiyifa.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("snowflakeOrderServiceImpl")
public class SnowflakeOrderServiceImpl implements IorderService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 产生订单号
     */
    public void orderId() {
        System.out.println("insert into order_id(id) value("+ snowflakeIdWorker.nextId() +");");
    }
}
