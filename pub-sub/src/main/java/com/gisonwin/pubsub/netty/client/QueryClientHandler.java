package com.gisonwin.pubsub.netty.client;

import com.gisonwin.pubsub.netty.NettyMessage;

import com.gisonwin.pubsub.netty.utils.NettyMessageUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.SZDFH_MTP2.SZDFHMTP2;


import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.gisonwin.pubsub.netty.CONSTANTS.MTP_INIT_RESPONSE;
import static com.gisonwin.pubsub.netty.CONSTANTS.PROTOCOL_HEADER_LENGTH;


public class QueryClientHandler extends SimpleChannelInboundHandler<NettyMessage> {

    static final Logger logger = LoggerFactory.getLogger(QueryClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
//        logger.info("client received data {} from server @ {}", msg, LocalDateTime.now());
//        int kind = msg.getKind();
        logger.info("channelRead0() method");
        logger.info("client received data {} from server @ {}", msg, LocalDateTime.now());

        int kind = msg.getKind();
        logger.info("得到服务端返回的消息类型为{}", kind);

        /**
         *   发送消息
         */
        for (int i = 0; i < 5; i++) {
//        for (; ; ) {
            SZDFHMTP2.msgCommandCack msgCommandCack = SZDFHMTP2.msgCommandCack.newBuilder().setWaterId("7890234").setStarCode(1).setCommandResult(0).setCackDesc("success")
                    .setCackCommandTime(LocalDateTime.now().toString().replaceAll("T", " ")).build();
            byte[] bytes = msgCommandCack.toByteArray();
            NettyMessage nm = NettyMessageUtils.generateNettyMessage("7890234");
            nm.setKind(MTP_INIT_RESPONSE);
            nm.setBodyData(bytes);
            nm.setLength(bytes.length + PROTOCOL_HEADER_LENGTH);
            /**
             * json字符串发送
             */
            ctx.writeAndFlush(nm);
            logger.info("client send protobuf data to server {}", LocalDateTime.now());
            TimeUnit.MICROSECONDS.sleep(5000);
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    logger.info("send heart beat to server");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("socket connected");
//        super.channelActive(ctx);


    }
//    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("socket closed");
//        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        logger.error(cause.getMessage());
        ctx.fireChannelActive();
        ctx.close();
    }
}