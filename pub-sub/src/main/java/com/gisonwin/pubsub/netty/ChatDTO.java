package com.gisonwin.pubsub.netty;

import java.io.Serializable;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/2/17 15:03
 */
public class ChatDTO implements Serializable,Cloneable {
    private String clitneID;//客户端的唯一ID
    private String msg;//发送的消息

    public String getClitneID() {
        return clitneID;
    }

    public void setClitneID(String clitneID) {
        this.clitneID = clitneID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
