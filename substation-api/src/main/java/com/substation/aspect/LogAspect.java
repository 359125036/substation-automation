package com.substation.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.substation.entity.system.SysLoginInfo;
import com.substation.entity.system.SysOperLog;
import com.substation.enums.DelFlagEnum;
import com.substation.enums.OperLogTypeEnum;
import com.substation.mapper.SysOperLogMapper;
import com.substation.service.LoginUserService;
import com.substation.service.system.ISysLoginInfoService;
import com.substation.utils.AddressUtils;
import com.substation.utils.ConstantUtil;
import com.substation.utils.ServletUtils;
import com.substation.utils.UuidUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;


/**
 * @ClassName: LogAspect
 * @Description: 方法日志（aop）
 * @Author: zhengxin
 * @Date: 2020/10/15 19:04
 * @Version: 1.0
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private ISysLoginInfoService loginInfoService;

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /** 定义一个切点 */
    @Pointcut("execution(public * com.substation.controller.system..*Controller.*(..))")
    public void controllerPointcut() {}


    /** 定义一个切点 */
    @Pointcut("execution(public * com.substation.controller.auth.SysLoginController.*(..))")
    public void loginPointcut() {}

//    @Before("controllerPointcut()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
//        SysOperLog log=new SysOperLog();
//        String logCode= UuidUtil.getShortUuid();
//        // 日志编号
//        MDC.put("UUID", logCode);
//        log.setOperId(logCode);
//
//        // 开始打印请求日志
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        Signature signature = joinPoint.getSignature();
//        String name = signature.getName();
//
//        // 打印业务操作
//        String nameCn = "";
//        if (name.contains("update") || name.contains("edit")) {
//            nameCn = OperLogTypeEnum.UPDATE_ENUM.value;
//            log.setBusinessType(OperLogTypeEnum.UPDATE_ENUM.type);
//        } else if (name.contains("save")) {
//            nameCn = OperLogTypeEnum.INSERT_ENUM.value;
//            log.setBusinessType(OperLogTypeEnum.INSERT_ENUM.type);
//        } else if (name.contains("delete")) {
//            nameCn = OperLogTypeEnum.DELETE_ENUM.value;
//            log.setBusinessType(OperLogTypeEnum.DELETE_ENUM.type);
//        } else {
//            nameCn = OperLogTypeEnum.OTHER_ENUM.value;
//            log.setBusinessType(OperLogTypeEnum.OTHER_ENUM.type);
//        }
//
//        // 使用反射，获取业务名称
//        Class clazz = signature.getDeclaringType();
//        Field field;
//        String businessName = "";
//        try {
//            field = clazz.getField("BUSINESS_NAME");
//            if (!StringUtils.isEmpty(field)) {
//                businessName = (String) field.get(clazz);
//                log.setTitle(businessName);
//            }
//        } catch (NoSuchFieldException e) {
//            LOG.error("未获取到业务名称");
//        } catch (SecurityException e) {
//            LOG.error("获取业务名称失败", e);
//        }
//
//        // 打印请求信息
//        LOG.info("------------- 【{}】{}开始 -------------", businessName, nameCn);
//        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
//        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
//        LOG.info("远程地址: {}", request.getRemoteAddr());
//        //请求方式
//        log.setRequestMethod(request.getMethod());
//        //类名方法
//        log.setMethod(signature.getDeclaringTypeName()+"."+name);
//        //远程地址
//        log.setOperIp(request.getRemoteAddr());
//        //操作人员
//        log.setOperName(loginUserService.getUsername());
//        //请求地址
//        log.setOperUrl(request.getRequestURL().toString());
//        // 打印请求参数
//        Object[] args = joinPoint.getArgs();
//        Object[] arguments  = new Object[args.length];
//        for (int i = 0; i < args.length; i++) {
//            if (args[i] instanceof ServletRequest
//                    || args[i] instanceof ServletResponse
//                    || args[i] instanceof MultipartFile) {
//                continue;
//            }
//            arguments[i] = args[i];
//        }
//        // 排除字段，敏感字段或太长的字段不显示
//        String[] excludeProperties = {"shard"};
//        PropertyPreFilters filters = new PropertyPreFilters();
//        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
//        excludefilter.addExcludes(excludeProperties);
//        LOG.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter)); // 为空的会不打印，但是像图片等长字段也会打印
//        //请求参数
//        String operParam=JSONObject.toJSONString(arguments, excludefilter);
//        operParam=operParam.substring(0,operParam.length()-1 > 2000 ? 2000:operParam.length()-1);
//        log.setOperParam(operParam);
//        log.setStatus(0);
//        log.setOperTime(new Date());
//        if(OperLogTypeEnum.OTHER_ENUM.type!=log.getBusinessType())
//            //保存日志
//            operLogMapper.insert(log);
//    }

    @Around("loginPointcut()")
    public Object loginAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String userName=null;
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = proceedingJoinPoint.getSignature();
        String name = signature.getName();
        if("logout".equals(name)){
            userName=loginUserService.getUsername();
        }
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        Object result = proceedingJoinPoint.proceed();
        SysLoginInfo loginInfo=new SysLoginInfo();

        // 打印业务操作
        String nameCn = "";
        if ("login".equals(name)||"logout".equals(name)) {
            // 打印请求参数
            Object[] args = proceedingJoinPoint.getArgs();
            Object[] arguments  = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest
                        || args[i] instanceof ServletResponse
                        || args[i] instanceof MultipartFile) {
                    continue;
                }
                arguments[i] = args[i];
            }


            //ip
            String ip=request.getRemoteAddr();
            loginInfo.setIpAddr(ip);
            //登录地点
            //TODO 可以设置不查询真实地址
            loginInfo.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();
            loginInfo.setBrowser(browser);
            // 获取客户端操作系统
            String os = userAgent.getOperatingSystem().getName();
            loginInfo.setOs(os);
            // 排除字段，敏感字段或太长的字段不显示
            String[] excludeProperties = {"password"};
            PropertyPreFilters filters = new PropertyPreFilters();
            PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
            excludefilter.addExcludes(excludeProperties);
            if("login".equals(name)){
                String operParam=JSONObject.toJSONString(arguments, excludefilter);
                JSONArray objects = JSON.parseArray(operParam);
                JSONObject obj = (JSONObject) JSON.parse(objects.getString(0));
                userName = (String) obj.get("username");
                loginInfo.setMsg(ConstantUtil.LOGIN_SUCCESS);
            }else{
                loginInfo.setMsg(ConstantUtil.LOGIN_OUT_SUCCESS);
            }
            //获取登录用户名称
            loginInfo.setUserName(userName);
            //状态
            loginInfo.setStatus(DelFlagEnum.EXIST_FLAG_ENUM.type);

            loginInfo.setLoginTime(new Date());

            loginInfoService.insertLoginInfo(loginInfo);
            LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        }
        return result;
    }



    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        SysOperLog log=new SysOperLog();
        String logCode= UuidUtil.getShortUuid();
        // 日志编号
        MDC.put("UUID", logCode);
        log.setOperId(logCode);
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = proceedingJoinPoint.getSignature();
        String name = signature.getName();
        // 打印业务操作
        String nameCn = "";
        if (name.contains("update") || name.contains("edit")) {
            nameCn = OperLogTypeEnum.UPDATE_ENUM.value;
            log.setBusinessType(OperLogTypeEnum.UPDATE_ENUM.type);
        } else if (name.contains("save")) {
            nameCn = OperLogTypeEnum.INSERT_ENUM.value;
            log.setBusinessType(OperLogTypeEnum.INSERT_ENUM.type);
        } else if (name.contains("delete")) {
            nameCn = OperLogTypeEnum.DELETE_ENUM.value;
            log.setBusinessType(OperLogTypeEnum.DELETE_ENUM.type);
        } else {
            nameCn = OperLogTypeEnum.OTHER_ENUM.value;
            log.setBusinessType(OperLogTypeEnum.OTHER_ENUM.type);
        }

        // 使用反射，获取业务名称
        Class clazz = signature.getDeclaringType();
        Field field;
        String businessName = "";
        try {
            field = clazz.getField("BUSINESS_NAME");
            if (!StringUtils.isEmpty(field)) {
                businessName = (String) field.get(clazz);
                log.setTitle(businessName);
            }
        } catch (NoSuchFieldException e) {
            LOG.error("未获取到业务名称");
        } catch (SecurityException e) {
            LOG.error("获取业务名称失败", e);
        }

        // 打印请求信息
        LOG.info("------------- 【{}】{}开始 -------------", businessName, nameCn);
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("远程地址: {}", request.getRemoteAddr());
        //请求方式
        log.setRequestMethod(request.getMethod());
        //类名方法
        log.setMethod(signature.getDeclaringTypeName()+"."+name);
        String ip=request.getRemoteAddr();
        //远程地址
        log.setOperIp(ip);
        //操作人员
        log.setOperName(loginUserService.getUsername());
        //请求地址
        log.setOperUrl(request.getRequestURL().toString());
        // 打印请求参数
        Object[] args = proceedingJoinPoint.getArgs();
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter)); // 为空的会不打印，但是像图片等长字段也会打印
        //请求参数
        String operParam=JSONObject.toJSONString(arguments, excludefilter);
        operParam=operParam.substring(0,operParam.length()-1 > 2000 ? 2000:operParam.length());
        log.setOperParam(operParam);
        log.setStatus(0);
        log.setOperTime(new Date());
        Object result = proceedingJoinPoint.proceed();
        String jsonResult=null;
        if(result!=null) {
            jsonResult =JSONObject.toJSONString(result, excludefilter);
            jsonResult = jsonResult.substring(0, jsonResult.length()-1>2000?2000:jsonResult.length());
        }
        log.setJsonResult(jsonResult);
        //获取真实地址
        log.setOperLocation(AddressUtils.getRealAddressByIP(ip));
        if(OperLogTypeEnum.OTHER_ENUM.type!=log.getBusinessType())
            //保存日志
            operLogMapper.insert(log);
        LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }

}