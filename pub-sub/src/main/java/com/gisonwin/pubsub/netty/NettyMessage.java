package com.gisonwin.pubsub.netty;



import java.io.Serializable;
import java.util.Arrays;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description:
 * @Date 2021/7/15 16:43
 */
public class NettyMessage implements Serializable, Cloneable {
//序号	字段名称	字节数	备注
//1.	同步字	无符号整数（4字节）	0xFEDCBA98
// 2.	数据包长度	无符号整数（4字节）	包含所有字段的字节长度
//3.	数据包类型	无符号整数（4字节）	一个类型对应一个ProtoBuf消息，ProtoBuf消息根据业务定义，编号从1开始
//4.	时间(年)	无符号整数（2字节）
//5.	时间(月)	无符号整数（1字节）
// 6.	时间(日)	无符号整数（1字节）
//7.	时间(时)	无符号整数（1字节）
//8.	时间(分)	无符号整数（1字节）
//9.	时间(秒)	无符号整数（1字节）
// 10.	时间(毫秒)	无符号整数（2字节）
// 11.	数据包唯一标识	16字节	GUID表示的数据块，用于数据包追踪
//12.	备用	无符号整数（8字节）	默认填0
//13.	消息体(消息序列化数据块)	数据块（N字节）	长度由数据包类型决定


    private final int magicNumber;//同步字 0xFEDCBA98 4
    private int length;//数据包长度  4
    private int kind;//数据包类型 编号从1开始 4

    //    private final short year; // 2
//    private final byte month; // 1
//    private final byte day; // 1
//    private final byte hour; // 1
//    private final byte minute;// 1
//    private final byte second;// 1
//    private final short millionSecond;// 2
    private DateObject dateObject;
    private byte[] guid;//16 字节,数据包唯一标识
    private long remark;//备用,以0填充  8字节
    private byte[] bodyData; //N字节

    public NettyMessage(int magicNumber, DateObject dateObject,/*short year, byte month, byte day, byte hour, byte minute, byte second, short millionSecond,*/ byte[] guid, long remark) {
        this.magicNumber = magicNumber;
//        this.year = year;
//        this.month = month;
//        this.day = day;
//        this.hour = hour;
//        this.minute = minute;
//        this.second = second;
//        this.millionSecond = millionSecond;
        this.dateObject = dateObject;
        this.guid = guid;
        this.remark = remark;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public int getLength() {
        return length;
    }

    public int getKind() {
        return kind;
    }

    public short getYear() {
        return dateObject.getYear();
    }

    public byte getMonth() {
        return dateObject.getMonth();
    }

    public byte getDay() {
        return dateObject.getDay();
    }

    public byte getHour() {
        return dateObject.getHour();
    }

    public byte getMinute() {
        return dateObject.getMinute();
    }

    public byte getSecond() {
        return dateObject.getSecond();
    }

    public short getMillionSecond() {
        return dateObject.getMillionSecond();
    }

    public byte[] getGuid() {
        return guid;
    }

    public long getRemark() {
        return remark;
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    public void setBodyData(byte[] bodyData) {
        this.bodyData = bodyData;
    }

    public void setGuid(byte[] guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "magicNumber=" + magicNumber +
                ", length=" + length +
                ", kind=" + kind +
                ", dateObject=" + dateObject +
                ", guid=" + Arrays.toString(guid) +
                ", remark=" + remark +
                ", bodyData=" + Arrays.toString(bodyData) +
                '}';
    }
}
