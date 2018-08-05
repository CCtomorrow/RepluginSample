package com.cctomo.hostapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <b>Project:</b> RepluginSample <br>
 * <b>Create Date:</b> 2018/8/5 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 工具类 <br>
 */
public class DateUtil {

    /**
     * 获取现在的时间
     *
     * @return yyyyMMddHHmmss格式时间
     */
    public static String getNow() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(new Date(System.currentTimeMillis()));
    }

}
