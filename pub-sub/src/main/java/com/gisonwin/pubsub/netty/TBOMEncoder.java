package com.gisonwin.pubsub.netty;


import com.gisonwin.pubsub.netty.utils.EndianUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/****
 *序号	字段名称	字节数	备注     <br>
 * 1.	同步字	无符号整数（4字节）	0xFEDCBA98 <br>
 * 2.	数据包长度	无符号整数（4字节）	包含所有字段的字节长度<br>
 * 3.	数据包类型	无符号整数（4字节）	一个类型对应一个ProtoBuf消息，ProtoBuf消息根据业务定义，编号从1开始<br>
 * 4.	时间(年)	无符号整数（2字节）<br>
 * 5.	时间(月)	无符号整数（1字节）<br>
 * 6.	时间(日)	无符号整数（1字节）<br>
 * 7.	时间(时)	无符号整数（1字节）<br>
 * 8.	时间(分)	无符号整数（1字节）<br>
 * 9.	时间(秒)	无符号整数（1字节）<br>
 * 10.	时间(毫秒)	无符号整数（2字节）<br>
 * 11.	数据包唯一标识	16字节	GUID表示的数据块，用于数据包追踪<br>
 * 12.	备用	无符号整数（8字节）	默认填0<br>
 * 13.	消息体(消息序列化数据块)	数据块（N字节）	长度由数据包类型决定<br>
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a><br>
 * @Date 2019/8/29 11:07<br>
 */
public class TBOMEncoder extends MessageToByteEncoder<NettyMessage> {
//    static final Logger log = LoggerFactory.getLogger(TBOMEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) {
        writeEncoder(msg, out);
    }

    /***
     *
     * @param msg  消息封装体
     * @param out   写出对象
     */
    private void writeEncoder(NettyMessage msg, ByteBuf out) {
        out.writeIntLE(msg.getMagicNumber());//同步字
        out.writeIntLE(msg.getLength());//长度
        out.writeIntLE(msg.getKind());//包类型
        out.writeShortLE(msg.getYear());//年
        out.writeByte(msg.getMonth());//月
        out.writeByte(msg.getDay());//日
        out.writeByte(msg.getHour());//时
        out.writeByte(msg.getMinute());//分
        out.writeByte(msg.getSecond());//秒
        out.writeShortLE(msg.getMillionSecond());//毫秒
        //将guid byte array 修改为小端 写出去
        byte[] data = EndianUtils.BigEndian16BytesToLittleEndianBytes(msg.getGuid());
        out.writeBytes(data);//guid little endian
        out.writeLongLE(msg.getRemark());//remark
        out.writeBytes(msg.getBodyData());//写的proto buff的字节流
    }
}