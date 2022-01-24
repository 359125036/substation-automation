package com.substation.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PowerDataAnalysisUtil
 * @Author: zhengxin
 * @Description: 电能数据解析
 * @Date: 2020/12/1 14:43
 * @Version: 1.0
 */
public class PowerDataAnalysisUtil {

    private static String[] electricCapacityParam= new String[]{"","uan", "uab","","ia","pa","pfa",
    "qa","sa","ubn","ubc","","ib","pb","pfb","qb","sb","ucn","uca","","ic","pc","pfc","qc","","sc",
    "uavg","lavg","frequency","total_active_power","pf","total_reactive_power","total_apparent_power"};

    /**
     * @Method powerDataAnalysis
     * @Author zhengxin
     * @Description 电能数据解析
     * @Param: [param] 服务端返回的参数
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2020/12/1 14:57
     * @Version  1.0
     */
    public static Map<String,Object> powerDataAnalysis(String param){
        if(param==null&&param.length()<1)
            return null;
        Map<String,Object> map=new HashMap<String,Object>();
        int len=param.length()/4;
        for (int i = 1; i < len ; i++) {
            int indexBegin=i * 4+2>param.length()?param.length():i*4+2;
            int index=(i+1)*4+2>param.length()?param.length():(i+1)*4+2;
            String substring = param.substring(indexBegin, index);
            if(i!=3&&i!=11&&i!=19&&i!=24&&electricCapacityParam.length>i){

                map.put(electricCapacityParam[i],StringToHexUtil.decodeHEX(substring));
            }
            if(i==42){
                map.put("voltage_unbalance",substring);
            }
            if(i==43){
                map.put("current_unbalance",substring);
            }

        }

        return map;
    }


}
