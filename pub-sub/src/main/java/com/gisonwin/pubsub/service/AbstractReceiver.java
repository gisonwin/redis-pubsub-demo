package com.gisonwin.pubsub.service;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/15 16:23
 */
public abstract class AbstractReceiver {
    abstract void receiveMessage(Object message);
}
