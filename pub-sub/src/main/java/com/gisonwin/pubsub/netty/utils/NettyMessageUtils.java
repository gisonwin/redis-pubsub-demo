package com.gisonwin.pubsub.netty.utils;



import com.gisonwin.pubsub.netty.DateObject;
import com.gisonwin.pubsub.netty.NettyDTO;
import com.gisonwin.pubsub.netty.NettyMessage;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.gisonwin.pubsub.netty.CONSTANTS.MAGIC_NUMBER;
import static com.gisonwin.pubsub.netty.CONSTANTS.REMARK;


/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description:
 * @Date 2021/9/23 14:18
 */
public final class NettyMessageUtils {
    private static final Logger logger = LoggerFactory.getLogger(NettyMessageUtils.class);

    /**
     * 将参数 时间 转换为 DateObject对象，封装一下，让其他调用。
     *
     * @param ldt LocalDateTime
     * @return DateObject
     */
    public static DateObject now(LocalDateTime ldt) {

        short year = (short) ldt.getYear();
        byte month = (byte) ldt.getMonthValue();
        byte day = (byte) ldt.getDayOfMonth();
        byte hour = (byte) ldt.getHour();
        byte minute = (byte) ldt.getMinute();

        byte second = (byte) ldt.getSecond();
        int nano = ldt.getNano();
        //转换纳秒为毫秒
        short millionSecond = (short) (nano / 1000000L);
        logger.debug("nano == {},million second == {}", nano, millionSecond);

        return new DateObject(year, month, day, hour, minute, second, millionSecond);
    }

    /**
     * @param guid 流水号
     * @return NettyMessage 对象
     */
    @NotNull
    public static NettyMessage generateNettyMessage(String guid) {
        return generateNettyMessage(guid, REMARK);
    }

    /***
     *
     * @param guid 流水号
     * @param timeout  设备服务中某些需要超时时间,一般设置为
     * @return NettyMessage 对象
     */
    @NotNull
    public static NettyMessage generateNettyMessage(String guid, long timeout) {
        DateObject dateObject = now(LocalDateTime.now());
        byte[] bytes = guid.getBytes(StandardCharsets.UTF_8);
        return new NettyMessage(MAGIC_NUMBER, dateObject, bytes, timeout);
    }


    @NotNull
    public static NettyDTO getNettyDTO(NettyMessage msg) {
        NettyDTO dto = new NettyDTO();
        dto.setDay(msg.getDay());
        dto.setKind(msg.getKind());
        dto.setLength(msg.getLength());
        dto.setMagicNumber(msg.getMagicNumber());
        String hexGuid = NettyUtils.bytes2hex(msg.getGuid());
        dto.setGuidStr(hexGuid);
        String bytes2hex = NettyUtils.bytes2hex(msg.getBodyData());
        dto.setBodyDataStr(bytes2hex);
        dto.setHour(msg.getHour());
        dto.setMinute(msg.getMinute());
        dto.setMonth(msg.getMonth());
        dto.setRemark(msg.getRemark());
        dto.setSecond(msg.getSecond());
        dto.setYear(msg.getYear());
        dto.setMillionSecond(msg.getMillionSecond());
        return dto;
    }
}
