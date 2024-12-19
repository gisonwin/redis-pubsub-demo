package com.gisonwin.pubsub.netty;

import java.io.Serializable;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/16 11:10
 */
public class NettyDTO implements Serializable, Cloneable {

    private int magicNumber;//同步字 0xFEDCBA98 4
    private int length;//数据包长度  4
    private int kind;//数据包类型 编号从1开始 4

    private short year; // 2
    private byte month; // 1
    private byte day; // 1
    private byte hour; // 1
    private byte minute;// 1
    private byte second;// 1
    private short millionSecond;// 2
    private String guidStr;//16 字节,数据包唯一标识
    private long remark;//备用,以0填充  8字节
    private String bodyDataStr; //N字节

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public byte getMonth() {
        return month;
    }

    public void setMonth(byte month) {
        this.month = month;
    }

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public byte getHour() {
        return hour;
    }

    public void setHour(byte hour) {
        this.hour = hour;
    }

    public byte getMinute() {
        return minute;
    }

    public void setMinute(byte minute) {
        this.minute = minute;
    }

    public byte getSecond() {
        return second;
    }

    public void setSecond(byte second) {
        this.second = second;
    }

    public short getMillionSecond() {
        return millionSecond;
    }

    public void setMillionSecond(short millionSecond) {
        this.millionSecond = millionSecond;
    }

    public String getGuidStr() {
        return guidStr;
    }

    public void setGuidStr(String guidStr) {
        this.guidStr = guidStr;
    }

    public long getRemark() {
        return remark;
    }

    public void setRemark(long remark) {
        this.remark = remark;
    }

    public String getBodyDataStr() {
        return bodyDataStr;
    }

    public void setBodyDataStr(String bodyDataStr) {
        this.bodyDataStr = bodyDataStr;
    }
}
