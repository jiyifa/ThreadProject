package com.jiyifa.service.impl;

import com.jiyifa.service.IorderService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 订单-jdk-uuid实现
 */
@Service("uuidOrderServiceImpl")
public class UuidOrderServiceImpl implements IorderService {
    /**
     * 产生订单号
     */
    public void orderId(){
        System.out.println("insert into order_id(id) value('"+ UUID.randomUUID() +"');");
    }
}
