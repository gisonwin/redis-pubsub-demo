package com.gisonwin.pubsub.netty.server;

import com.gisonwin.pubsub.netty.NettyChannelMap;
import com.gisonwin.pubsub.netty.NettyMessage;
import com.gisonwin.pubsub.netty.utils.NettyMessageUtils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.SZDFH_MTP2.SZDFHMTP2;

import java.time.LocalDateTime;

import static com.gisonwin.pubsub.netty.CONSTANTS.PROTOCOL_HEADER_LENGTH;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/2/18 10:10
 */
@ChannelHandler.Sharable
public class NettyQueryHandler extends SimpleChannelInboundHandler<NettyMessage> {

    static final Logger logger = LoggerFactory.getLogger(NettyQueryHandler.class);
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 服务端接收到客户端发送来的数据.
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("服务端接收到客户端的消息:{}", msg);
        if (msg instanceof NettyMessage) {
            NettyMessage nm = ((NettyMessage) msg);

            int kind = nm.getKind();
            logger.info("get msg kind == {}", kind);
            switch (kind) {
                case 1:
                    break;
                default:
                    break;
            }
        } else {
            logger.warn("得到的不是NettyMessage消息{}", msg.toString());
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage nettyMessage) throws Exception {


        int kind = nettyMessage.getKind();
        logger.info("get msg kind == {}", kind);
        switch (kind) {
            case 1:
                break;
            default:
                break;
        }
        for (; ; ) {

            SZDFHMTP2.msgCommandCack msgCommandCack = SZDFHMTP2.msgCommandCack.newBuilder().setWaterId("7890234").setStarCode(1).setCommandResult(0).setCackDesc("success")
                    .setCackCommandTime(LocalDateTime.now().toString().replaceAll("T", " ")).build();
            byte[] bytes = msgCommandCack.toByteArray();
            NettyMessage nm = NettyMessageUtils.generateNettyMessage("78901234");
            nm.setKind(100);
            nm.setBodyData(bytes);
            nm.setLength(bytes.length + PROTOCOL_HEADER_LENGTH);
            ctx.writeAndFlush(nm);
            logger.info("server received client connection,and return data to client @ {}", LocalDateTime.now());
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        logger.error(cause.getMessage());
        ctx.fireChannelActive();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        logger.info("connected by remote {}", channel.remoteAddress().getHostString());
        String clientIP = channel.remoteAddress().getHostString();
        logger.info("{}", clientIP);
        channelGroup.add(channel);
        NettyChannelMap.add(clientIP, channel);
//        for (int i = 0; i < 2; i++) {
//
//            SZDFHMTP2.msgCommandCack msgCommandCack = SZDFHMTP2.msgCommandCack.newBuilder().setWaterId("7890234").setStarCode(1).setCommandResult(0).setCackDesc("success")
//                    .setCackCommandTime(LocalDateTime.now().toString().replaceAll("T", " ")).build();
//            byte[] bytes = msgCommandCack.toByteArray();
//            NettyMessage nm = NettyMessageUtils.generateNettyMessage("78901234");
//            nm.setKind(100);
//            nm.setBodyData(bytes);
//            nm.setLength(bytes.length + PROTOCOL_HEADER_LENGTH);
//            ctx.writeAndFlush(nm);
//            logger.info("server received client connection,and return data to client @ {}", LocalDateTime.now());
//        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        channelGroup.remove(channel);
        NettyChannelMap.remove(channel);
    }
}
