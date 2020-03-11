package com.iot.framework.aop;

import com.iot.model.Log;
import com.iot.model.User;
import com.iot.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 切点类
 */
@Slf4j
@Aspect
@Component
public class SystemLogAspect {

    //private static final Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    private LogService logService;

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Log getControllerAndServiceMethodDescription(
            JoinPoint joinPoint, int i) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        SystemControllerLog log;
        SystemServiceLog log2;
        Log logM = new Log();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    //SystemControllerLog的切点获取方法描述
                    if (i == 1) {
                        log = method.getAnnotation(SystemControllerLog.class);
                        logM.setAction(log.logAction());
                        logM.setContent(log.logContent());
                        break;
                        //SystemServiceLog的切点获取方法描述
                    } else {
                        log2 = method.getAnnotation(SystemServiceLog.class);
                        logM.setAction(log2.logAction());
                        logM.setContent(log2.logContent());
                        break;
                    }
                }
            }
        }
        return logM;
    }

    /**
     * Controller层切点,针对在业务模块标注SystemControllerLog注解记录日志
     */
    @Pointcut("@annotation( com.iot.framework.aop.SystemControllerLog )")
    public void controllerAspect() {
    }

    /**
     * Service层切点,针对在业务模块标注SystemControllerLog注解记录日志
     */
    @Pointcut("@annotation( com.iot.framework.aop.SystemServiceLog )")
    public void serviceAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     * 原本是Before("controllerAspect()")，但日志应该是完成后记录。所以改成 @AfterReturning("controllerAspect()")，函数名没改
     *
     * @param joinPoint 切点
     */
    @AfterReturning("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        try {
            // 请求的IP
            String logIP = request.getRemoteAddr();

            Log slm = getControllerAndServiceMethodDescription(joinPoint, 1);

            Map userID = (Map) request.getAttribute("currentUser");
            if (userID != null) {
                slm.setUserId((int) userID.get("id"));
            }

            slm.setIp(logIP);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());

            slm.setCreateTime(date);

            // *========控制台输出=========*//
            log.debug("=====注解参数获取开始=====");
            log.debug("请求方法:"
                    + (joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "()"));
            log.debug("操作方法:" + slm.getAction());
            log.debug("操作内容:" + slm.getContent());
            log.debug("请求IP:" + slm.getIp());
            log.debug("用户ID:" + slm.getId());
            // *========数据库日志=========*//
            int res = logService.insert(slm);
            if (res > 0) {
                System.out.println("SystemLogAspect保存日志成功");
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>保存日志成功");
            }
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("后置通知异常,保存日志异常信息:{}", e.getMessage());
        }
    }


    /**
     * 后置通知 用于拦截Service层记录用户的操作
     * 这里主要用作记录登录和注册日志
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "serviceAspect()", returning = "ret")
    public void doAfter(JoinPoint joinPoint, Map ret) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        try {
            // 请求的IP
            Log slm = getControllerAndServiceMethodDescription(joinPoint, 2);

            User user = (User) ret.get("user");
            slm.setUserId(user.getId());

            String logIP = request.getRemoteAddr();
            slm.setIp(logIP);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            slm.setCreateTime(date);

            int res = logService.insert(slm);

            if (res > 0) {
                System.out.println("SystemLogAspect保存日志成功");
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>保存日志成功");
            }
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("后置通知异常,保存日志异常信息:{}", e.getMessage());
        }
    }

}