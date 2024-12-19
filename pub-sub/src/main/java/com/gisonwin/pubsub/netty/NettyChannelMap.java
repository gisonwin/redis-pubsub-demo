package com.gisonwin.pubsub.netty;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/2/17 15:10
 */
public class NettyChannelMap {
    private static Map<String, SocketChannel> map = new ConcurrentHashMap<>();

    public static void add(String clientID, SocketChannel socketChannel) {
        map.put(clientID, socketChannel);
    }

    public static Channel get(String clientID) {
        return map.get(clientID);
    }

    public static void remove(SocketChannel socketChannel) {
//        map = map.entrySet().stream().filter(e -> e.getValue() != socketChannel).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        map.entrySet().removeIf(e -> e.getValue() == socketChannel);
    }
//    public static void main(String[] args) {
//        NioSocketChannel nsc = new NioSocketChannel();
//        map.put("1", nsc);
//        map.put("2", nsc);
//        map.put("3", new NioSocketChannel());
//        map.put("4", new NioSocketChannel());
//        System.out.println(map.toString());
//        remove(nsc);
//        System.out.println(map.toString());
//
//    }
}
