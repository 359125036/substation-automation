package com.substation.utils;

/**
 * @ClassName: WebPathUtil
 * @Author: zhengxin
 * @Description:
 * @Date: 2020/12/7 10:33
 * @Version: 1.0
 */
public class WebPathUtil {
    /**
     * 获取项目webapp目录
     * @return
     */
    public static String getWebPath(){
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        path=path.replace("substation-common","substation-api")+ "HKLlibraries";
        path=path.replace("target/classes","src/main/resources");
        if("/".equals(path.substring(0,1)))
            path=path.substring(1,path.length());
        path= path.replaceAll("/","\\\\") ;
        if(path.contains("!"))
            path="C:\\hk\\HKLlibraries";
        return path;
    }
}
