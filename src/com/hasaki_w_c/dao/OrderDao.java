package com.hasaki_w_c.dao;

import com.hasaki_w_c.pojo.Order;

import java.util.List;

/**
 * @author hasaki_w_c
 * @version 1.0
 * @date 2021/1/5 18:55
 */
public interface OrderDao {
    /**
     * 保存订单
     * @param order 订单对象
     * @return 受影响的行数
     */
    int saveOrder(Order order);

    /**
     * 查询全部订单
     * @return 订单列表
     */
    List<Order> queryOrder();

    /**
     * 修改订单状态
     * @param orderId 订单号
     * @param status 订单状态
     * @return 受影响的行数
     */
    int changeOrderStatus(String orderId, Integer status);

    /**
     * 根据用户编号查询订单信息
     * @param userId 用户编号
     * @return 订单列表
     */
    List<Order> queryOrderByUserId(Integer userId);
}
