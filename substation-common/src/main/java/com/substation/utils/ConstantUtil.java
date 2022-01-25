package com.substation.utils;

/**
 * @ClassName: ConstantUtil
 * @Author: zhengxin
 * @Description: 常量
 * @Date: 2020/11/11 14:09
 * @Version: 1.0
 */
public class ConstantUtil {

    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * redis-key-前缀-user:cache:
     */
    public static final String PREFIX_USER_CACHE = "user:cache";

    /**
     * JWT-account:
     */
    public static final String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;


    /**
     * AES密码加密私钥(Base64加密)
     */
    public static final  String encryptAESKey="V2FuZzkyNjQ1NGRTQkFQSUpXVA==";

    /**
     * JWT认证加密私钥(Base64加密)
     */
    public static final  String encryptJWTKey="U0JBUElKV1RkV2FuZzkyNjQ1NA==";
    /**
     * AccessToken过期时间-5分钟-5*60(秒为单位)
     */
    public static final  String accessTokenExpireTime="300";
    /**
     * RefreshToken过期时间-30分钟-30*60(秒为单位)
     */
    public static final  String refreshTokenExpireTime="1800";

    /**
     * Shiro缓存过期时间-5分钟-5*60(秒为单位)(一般设置与AccessToken过期时间一致)
     */
    public static final  String shiroCacheExpireTime="300";


    public static final  String LOGIN_SUCCESS="登录成功";

    public static final  String LOGIN_OUT_SUCCESS="登出成功";

    //历史视频存放本地目录
    public static final String HISTORY_VIDEO_FILE_PATH="HISTORY_VIDEO";

    //抓拍图片存放本地目录
    public static final String PHOTO_CATALOGUE="抓拍图片";

    //上传图片存放位置
    public static final String UPLOAD_PATH="youdi/uploadPath/avatar/";

    public static final String BASE64_IMG="data:image/png;base64,";
}
