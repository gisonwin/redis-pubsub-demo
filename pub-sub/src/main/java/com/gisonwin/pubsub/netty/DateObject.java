package com.gisonwin.pubsub.netty;

import java.io.Serializable;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description:
 * @Date 2021/9/9 13:50
 */
public class DateObject implements Serializable {
    private short year; // 2
    private byte month; // 1
    private byte day; // 1
    private byte hour; // 1
    private byte minute;// 1
    private byte second;// 1
    private short millionSecond;// 2

    public DateObject() {
    }

    public DateObject(short year, byte month, byte day, byte hour, byte minute, byte second, short millionSecond) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millionSecond = millionSecond;
    }

    public short getYear() {
        return year;
    }

    public byte getMonth() {
        return month;
    }

    public byte getDay() {
        return day;
    }

    public byte getHour() {
        return hour;
    }

    public byte getMinute() {
        return minute;
    }

    public byte getSecond() {
        return second;
    }

    public short getMillionSecond() {
        return millionSecond;
    }

    @Override
    public String toString() {
        return "DateObject{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", millionSecond=" + millionSecond +
                '}';
    }
}
