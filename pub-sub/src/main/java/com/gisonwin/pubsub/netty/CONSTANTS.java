package com.gisonwin.pubsub.netty;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description: 常量声明。
 * @Date 2021/7/15 16:43
 */
public class CONSTANTS {
    // self protocol constant variable
    public static final int MAGIC_NUMBER = 0xFEDCBA98;
    //
    public static final long REMARK = 0x00;
    //device 占用该字段为超时时间 ，1500 ms
    public static final long TIMEOUT = 0x5DC;

    //self protocol header length  45
    public static final int PROTOCOL_HEADER_LENGTH = 0x2D;
    //////////////////////////////////////////////    mtp kind define          ////////////////////////////////////////////
    //define protocol kind    mtp
    public static final int MTP_RESOURCE_REQUEST = 0x01;
    public static final int MTP_RESOURCE_RESPONSE = 0x02;
    public static final int MTP_LOGON_REQUEST = 0x03;
    public static final int MTP_LOGON_RESPONSE = 0x04;
    public static final int MTP_INIT_REQUEST = 0x05;
    public static final int MTP_INIT_RESPONSE = 0x06;
    public static final int MTP_COMMAND_SEND = 0x07;
    public static final int MTP_COMMAND_BACK = 0x08;
    public static final int MTP_TM_FRAME = 0x0B;//11 遥测原码帧
    public static final int MTP_TM_PACKAGE = 0x0C;//12 遥测分包
    public static final int MTP_PARAM_ITEM = 0x0D;//13 参数项
    public static final int MTP_PARAM_ITEM_MAP = 0x0E;//14 参数项集合Map
    public static final int MTP_PARAM_ITEM_ARRAY = 0x0F;//15 参数项集合Array

    //   延时实时标识(默认0x30实时)  0x30实时  0x31延时
    public static final int MTP_PARAM_ITEM_REALTIME = 0x30;//15 参数项集合Array
    public static final int mtp_param_item_delay = 0x31;//15 参数项集合Array


    //////////////////////////////////////////////     device kind define          ////////////////////////////////////////////
    //device kind //start 启动 303  改为 305
    public static final int DEV_START_REQUEST = 0x12F;
    //    public static final int DEV_START_REQUEST = 0x131;
    //  返回 真实服务IP kink 304  改为 308
    public static final int DEV_START_RESPONSE = 0x130;
    //    public static final int DEV_START_RESPONSE = 0x134;
    //stop   停止
    public static final int DEV_STOP_REQUEST = 0x02;
    public static final int DEV_STOP_RESPONSE = 0x03;
    //cmd send  发送指令
    public static final int DEV_CMD_REQUEST = 0x04;
    public static final int DEV_CMD_RESPONSE = 0x05;
    //property query 属性值查询
    public static final int DEV_PROPERTY_QUERY_REQUEST = 0x06;
    public static final int DEV_PROPERTY_QUERY_RESPONSE = 0x07;

    //param subscribe  设备属性订阅
    public static final int DEV_PARAM_SUBSCRIBE_REQUEST = 0x08;
    public static final int DEV_PARAM_SUBSCRIBE_RESPONSE = 0x09;
    //推送设备属性
    public static final int DEV_PROPERTY_PUSH_RESPONSE = 0x0A;

    //login  logoff 登录 301
    public static final int DEV_LOGIN_REQUEST = 0x12D;
    //    退出  302
    public static final int DEV_LOGOFF_RESPONSE = 0x12E;
    //#define DEVA_SQL_1          DEVM_PRO_BEGIN+20
//            #define DEVA_SQL_1_RET      DEVM_PRO_BEGIN+21   //代理发送查询请求步骤1返回
//            #define DEVA_SQL_2          DEVM_PRO_BEGIN+22   //代理发送查询请求步骤2
//            #define DEVA_SQL_2_RET      DEVM_PRO_BEGIN+23   //代理发送查询请求步骤2返回
//            #define DEVA_SQL_3          DEVM_PRO_BEGIN+24   //代理发送查询请求步骤3
//            #define DEVA_SQL_3_RET      DEVM_PRO_BEGIN+25   //代理发送查询请求步骤3返回
//            #define DEVM_SQL_CFG        DEVM_PRO_BEGIN+26   //管理发送查询配置请求
//            #define DEVM_SQL_CFG_RET    DEVM_PRO_BEGIN+27   //管理发送查询配置请求返回
    //代理发送查询请求步骤1   320
    public static final int DEVA_SQL_1 = 0x140;
    // 代理发送查询请求步骤1返回 321
    public static final int DEVA_SQL_1_RET = 0x141;
    //代理发送查询请求步骤2  322
    public static final int DEVA_SQL_2 = 0x142;
    //代理发送查询请求步骤2返回 323
    public static final int DEVA_SQL_2_RET = 0x143;
    //代理发送查询请求步骤3    324
    public static final int DEVA_SQL_3 = 0x144;
    //代理发送查询请求步骤3返回  325
    public static final int DEVA_SQL_3_RET = 0x145;
    //管理发送查询配置请求  326
    public static final int DEVM_SQL_CFG = 0x146;
    //    管理发送查询配置请求返回 327
    public static final int DEVM_SQL_CFG_RET = 0x147;


    //设备服务中前端服务的唯一标识
    public static final String FROC = "FROC";

}