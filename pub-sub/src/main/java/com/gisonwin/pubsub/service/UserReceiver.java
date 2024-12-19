package com.gisonwin.pubsub.service;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/15 16:25
 */
public class UserReceiver extends AbstractReceiver{
    @Override
    void receiveMessage(Object message) {
        System.out.println(" user message == "+message.toString());

    }
}
