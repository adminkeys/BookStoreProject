package com.hasaki_w_c.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 * @author hasaki_w_c
 * @version 1.0
 * @date 2021/1/4 17:03
 */
public class Cart {

    /**
     * key 是商品编号，value 是商品信息
     */
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    /**
     * 添加商品项
     * @param cartItem 购物车项
     */
    public void addItem(CartItem cartItem) {
        //先查看购物车中是否添加过此商品，如果已经添加，则数量累加，总金额更新，如果没有添加过，则直接放到集合中
        CartItem item = items.get(cartItem.getId());

        if (item == null) {
            // 之前没有添加过此商品
            items.put(cartItem.getId(),cartItem);
        }else {
            // 已经添加过的情况
            //数量累加
            item.setCount(item.getCount() + 1);
            //更新总金额
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    /**
     * 删除商品项
     * @param id 商品编号
     */
    public void deleteItem(Integer id) {
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear() {
        items.clear();
    }

    /**
     * 修改商品数量，更新金额
     * @param id 商品编号
     * @param count 商品数量
     */
    public void updateCount(Integer id, Integer count) {
        //先查看购物车中是否有此商品，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if (cartItem != null) {
            cartItem.setCount(count);
            //更新总金额
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem> entry :
                items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }


    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CartItem> entry :
                items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }


    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
