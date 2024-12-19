package com.gisonwin.pubsub.netty.client;

import com.gisonwin.pubsub.netty.NettyMessage;
import com.gisonwin.pubsub.netty.TBOMDecoder;
import com.gisonwin.pubsub.netty.TBOMEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.SZDFHSQLDEV;
import protobuf.SZDFHTST;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.gisonwin.pubsub.netty.CONSTANTS.*;
import static com.gisonwin.pubsub.netty.utils.NettyMessageUtils.generateNettyMessage;


/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a> <br>
 * @Description 连接 device 资源管理器 <br>
 * @Date 2021/8/19 16:16 <br>
 */
public class OnceDeviceClient {
    static final Logger logger = LoggerFactory.getLogger(OnceDeviceClient.class);
    final private String host;
    final private int port;
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;
    private LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    private List<String> devList = new ArrayList<String>();

    public OnceDeviceClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @return channelFuture
     */
    public ChannelFuture start() {

        try {
            InetSocketAddress address = new InetSocketAddress(host, port);
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group).channel(NioSocketChannel.class).handler(LOGGING_HANDLER)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //编解码
                            pipeline.addLast(new TBOMDecoder());
                            pipeline.addLast(new TBOMEncoder());
                            pipeline.addLast(new SimpleChannelInboundHandler<NettyMessage>() {

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    logger.error(cause.getMessage(), cause);
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, NettyMessage nm) throws Exception {
                                    //从服务端接收到的消息
                                    //TODO 协议可能返回多次 启动响应包，需要遍历等待所有包应答完成 此处代码逻辑需要修改20210928
                                    int kind = nm.getKind();
                                    logger.debug(" 资源管理服务 返回消息类型kind == {}", kind);
//                                    switch (kind) {
//                                        case DEV_START_RESPONSE://device资源管理器的返回协议
//
//                                            logger.debug("client receive data from server {},客户端接收,并返回给服务端应答 {} ", ctx.channel().remoteAddress(), nm);
//                                            byte[] bodyData = nm.getBodyData();
//
//                                            SZDFHTSTDEV.TST_DEVM_START_RET tdsr = SZDFHTSTDEV.TST_DEVM_START_RET.parseFrom(bodyData);
//                                            String sAskSoftGuid = tdsr.getSAskSoftGuid();
//                                            logger.debug("sAskSoftGuid == {} ", sAskSoftGuid);
//
//                                            Map<String, Object> maps = new ConcurrentHashMap<>();
//                                            int index = 1;
//                                            List<SZDFHTSTDEV.TST_DEVM_START_RET.TST_DEVM_STATE> devStateList2 = tdsr.getDevStateList();
//                                            for (SZDFHTSTDEV.TST_DEVM_START_RET.TST_DEVM_STATE tst_devm_state : devStateList2) {
//                                                String sdevsip = tst_devm_state.getSDEVSIP();
//                                                int idevPort = tst_devm_state.getIDEVPort();
//                                                String sdevsGuid = tst_devm_state.getSDEVSGuid();
//                                                logger.debug("sdevsip {} ,port {},sdevsGuid  {}", sdevsip, idevPort, sdevsGuid);
//                                                maps.put(String.valueOf(index), new HostObject(sdevsip, idevPort));
//                                                index++;
//                                                List<SZDFHTSTDEV.TST_DEVM_START_RET.TST_DEVM_STATE._STATE> devStateList = tst_devm_state.getDevStateList();
//
//                                                for (SZDFHTSTDEV.TST_DEVM_START_RET.TST_DEVM_STATE._STATE state : devStateList) {
//                                                    String strDevCode = state.getStrDevCode();
//                                                    if (!devList.contains(strDevCode)) {
//                                                        devList.add(strDevCode);
//                                                    }
//
//                                                    SZDFHPublic.PUB_EDevState iDevState = state.getIDevState();
//                                                    int number = iDevState.getNumber();
//                                                    logger.debug("strDevCode =={} ,dev state desc {},state value {}", strDevCode, iDevState.getValueDescriptor(), number);
//                                                }
//                                                String host = sdevsip + ":" + idevPort;
//                                                List<String> strList = devListMap.get(host);
//                                                if (strList == null) {
//                                                    devListMap.put(host, devList);
//                                                } else {
//                                                    strList.addAll(devList);
//                                                    devListMap.put(host, strList);
//                                                }
//                                            }
//                                            //遍历map然后将真实设备服务地址 返回给redis。
//                                            redisService.hmset(DEV_RESOURCE_ADDRESS, maps);
////                                            logger.debug("latch == {}", latch.getCount());
//                                            latch.countDown();
//                                            logger.debug("latch == {}", latch.getCount());
////                                                MTPObject mtpObject = new MTPObject(userCode, starCode, mtpResourceIp, mtpResourcePort, mtpResourceCode, command);
////                                                String value = JSONUtils.objectToJsonString(mtpObject);
////                                                redisService.set(MTP_RESOURCE_ADDRESS, value);
////                                                latch.countDown();
////                                            } else {
////                                                //结果标志  0=成功,其他值失败
////                                                logger.error("mtp link error,result == {}", result);
////                                            }
//
//                                            break;
//
//                                        default:
//                                            break;
//                                    }
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) {
                                    InetSocketAddress address = ((InetSocketAddress) ctx.channel().remoteAddress());
                                    logger.warn("客户端[ {} ]被服务端[ {} ]断开 @ {}", ctx.channel().localAddress(), address, LocalDateTime.now());
                                    ctx.fireChannelInactive();
                                    ctx.close();
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    logger.debug("连接到服务器{}成功", ctx.channel().remoteAddress());
                                    //连接资源管理成功以后 发送登录协议
                                    String guid = "7890234";
                                    NettyMessage msg = generateNettyMessage(guid);
                                    //连接上以后主动发送消息去device 资源管理器
                                    msg.setKind(DEV_LOGIN_REQUEST);
//                                    List<String> list = new ArrayList<>();
//                                    list.add(devCode);
                                    SZDFHTST.TST_LOGIN.Builder builder = SZDFHTST.TST_LOGIN.newBuilder().setStrSoftCode(FROC).setStrSoftGuid(guid);
                                    byte[] byteBody = builder.build().toByteArray();
                                    msg.setLength(PROTOCOL_HEADER_LENGTH + byteBody.length);
                                    msg.setBodyData(byteBody);
                                    ctx.writeAndFlush(msg);
                                    logger.debug("{} 发送资源管理器登录协议成功 至 {}", ctx.channel().localAddress(), ctx.channel().remoteAddress());
                                    //延迟几秒后发送启动设备指令
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    NettyMessage nm = generateNettyMessage(guid);
                                    nm.setKind(DEVM_SQL_CFG);
                                    SZDFHSQLDEV.SQL_GET_CFG build = SZDFHSQLDEV.SQL_GET_CFG.newBuilder().setStrkey("1").build();
                                    byte[] data = build.toByteArray();
                                    nm.setLength(PROTOCOL_HEADER_LENGTH + data.length);
                                    nm.setBodyData(data);
                                    ctx.writeAndFlush(nm);
                                    logger.debug("{} 发送全局查询协议成功 至 {}", ctx.channel().localAddress(), ctx.channel().remoteAddress());
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.connect(address).sync();
            channel = future.channel();
            return future;

        } catch (Exception ex) {
            logger.error(ex.toString(), ex);
        }

        return null;
    }

    /***
     * stop current server.
     */
    public void stop(String guid) {
        if (channel == null) {
            return;
        }
        //发送登出协议
        NettyMessage nm = generateNettyMessage(guid);
        nm.setKind(DEV_LOGOFF_RESPONSE);
        SZDFHTST.TST_LOGOFF logoff = SZDFHTST.TST_LOGOFF.newBuilder().setStrSoftCode(FROC).setStrSoftGuid(guid).build();
        byte[] bytes = logoff.toByteArray();
        nm.setLength(bytes.length + PROTOCOL_HEADER_LENGTH);
        nm.setBodyData(bytes);
        channel.writeAndFlush(nm);
        try {
            TimeUnit.MICROSECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        new OnceDeviceClient("192.168.1.192", 9999).start();

    }

}

