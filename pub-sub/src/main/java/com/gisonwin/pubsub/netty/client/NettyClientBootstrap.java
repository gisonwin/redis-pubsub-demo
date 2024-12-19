package com.gisonwin.pubsub.netty.client;


import com.gisonwin.pubsub.netty.TBOMDecoder;
import com.gisonwin.pubsub.netty.TBOMEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/2/18 14:40
 */
public class NettyClientBootstrap {
    static final Logger logger = LoggerFactory.getLogger(NettyClientBootstrap.class);
    private int port;
    private String host;
    public SocketChannel socketChannel;

    static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public NettyClientBootstrap(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .remoteAddress(host, port)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * This method will be called once the {@link Channel} was registered. After the method returns this instance
                         * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
                         *
                         * @param ch the {@link Channel} which was registered.
                         * @throws Exception is thrown if an error occurs. In that case it will be handled by
                         *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
                         *                   the {@link Channel}.
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new TBOMEncoder());
                            p.addLast(new TBOMDecoder());
                            p.addLast(new IdleStateHandler(5, 3, 0, TimeUnit.SECONDS));
                            p.addLast(new QueryClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                logger.info("connect to server {}:{} success", host, port);
            }
            socketChannel.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
            if (Objects.nonNull(socketChannel))
                socketChannel.closeFuture();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyClientBootstrap bootstrap = new NettyClientBootstrap(9999, "192.168.1.192");
    }

}

