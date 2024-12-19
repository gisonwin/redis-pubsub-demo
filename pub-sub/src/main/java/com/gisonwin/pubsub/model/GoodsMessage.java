package com.gisonwin.pubsub.model;

import java.util.Objects;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/15 15:55
 */
public class GoodsMessage extends RedisMessage{
    private String goodsType;
    private String number;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsMessage that = (GoodsMessage) o;
        return Objects.equals(goodsType, that.goodsType) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsType, number);
    }
}
