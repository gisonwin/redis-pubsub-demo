package com.gisonwin.pubsub.netty.server;

import com.gisonwin.pubsub.netty.TBOMDecoder;
import com.gisonwin.pubsub.netty.TBOMEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/2/17 17:17
 */
public class NettyTcpServerBootstrap {
    static final Logger logger = LoggerFactory.getLogger(NettyTcpServerBootstrap.class);
    private int port;
    private SocketChannel socketChannel;

    public NettyTcpServerBootstrap(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //设置NoDelay禁用Nagel,消息会立即发送出去,不用等到一定数量才发送出去
                    .childOption(ChannelOption.TCP_NODELAY, true).handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
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
                            p.addLast(new TBOMDecoder());
                            p.addLast(new TBOMEncoder());
                            p.addLast(new IdleStateHandler(0, 0, 6, TimeUnit.SECONDS));
                            p.addLast(new NettyQueryHandler());
                        }
                    });
            //启动服务器并绑定一个端口且同步生成一个ChannelFuture对象
            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                logger.info("Server start up on port {}", port);
            }
            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        } catch (Exception ex) {

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws Exception {
        int port = 9999;
        NettyTcpServerBootstrap bootstrap = new NettyTcpServerBootstrap(port);
        bootstrap.start();
    }
}
