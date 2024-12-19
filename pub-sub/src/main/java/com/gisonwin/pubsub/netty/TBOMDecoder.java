package com.gisonwin.pubsub.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.gisonwin.pubsub.netty.CONSTANTS.MAGIC_NUMBER;


/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @Date 2019/8/29 11:02
 */
public class TBOMDecoder extends ReplayingDecoder<Void> {
    static final Logger log = LoggerFactory.getLogger(TBOMDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int BASE_LENGTH = 4 + 4; //HEADER+length 4+4
        int readableBytes = in.readableBytes();
        if (readableBytes > BASE_LENGTH) {  //HEADER+length 4+4 ,可读字节要大于header和包体长度才会解码
            //读取Header
            int beginReader;
            //一直读数据,直到讲到的数据是header以后,再向后读取数据
            int header;
            while (true) {
                beginReader = in.readerIndex();
                in.markReaderIndex();//标记包头开始的位置
                int readInt = in.readIntLE();

                if ((header = readInt) == MAGIC_NUMBER) {
                    //读到了header, 开始读后面的数据
                    break;
                }
                in.resetReaderIndex();//重置reader index
                in.readByte();
                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                // 当略过，一个字节之后，
                // 数据包的长度，又变得不满足
                // 此时，应该结束。等待后面的数据到达
                if (in.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }
            //判断header是否正确 读取了同步字
            log.debug("read HEADER is " + header);
            //读包体长度
            int length = in.readIntLE();
            log.debug("package length =" + length);
            //读包类型
            int kind = in.readIntLE();
            //年
            short year = in.readShortLE();
            //月
            byte month = in.readByte();
            //日
            byte day = in.readByte();
            //时
            byte hour = in.readByte();
            //分
            byte minute = in.readByte();
            //秒
            byte second = in.readByte();
            //毫秒
            short millionSecond = in.readShortLE();
            //guid 16 bytes
            byte[] guid = new byte[16];
            in.readBytes(guid);
            //备用 无符号整数 8 字节
            long remark = in.readLongLE();

            int dataLength = length - BASE_LENGTH;//后续数据的长度
            // 判断请求数据包数据是否到齐
            if (in.readableBytes() < dataLength) {
                // 还原读指针
                in.readerIndex(beginReader);
                return;
            }
            DateObject dateObject = new DateObject(year, month, day, hour, minute, second, millionSecond);
            //读取数据
            NettyMessage protocol = new NettyMessage(header, dateObject, guid, remark);
            protocol.setKind(kind);
            protocol.setLength(length);
            //data
            byte[] data = new byte[dataLength - 13 - 16 - 8];
            in.readBytes(data);//将后续内容读入content 数组中
            log.debug("decoder content = " + new String(data, CharsetUtil.UTF_8));
            protocol.setBodyData(data);

            out.add(protocol);
        }
    }
}