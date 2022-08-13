package com.iot.util.exception;

import com.iot.model.resultAndPage.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(Exception e) {
        /**java 中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
         *在编译状态中，class可以是object对象的父类，自身类，子类。在这三种情况下Java编译时不会报错。
         * 在运行转态中，class可以是object对象的父类，自身类，不能是子类。在前两种情况下result的结果为true，最后一种为false。但是class为子类时编译不会报错。运行结果为false。
         */
        if (e instanceof DescribeException) {
            DescribeException myException = (DescribeException) e;
            return ResultUtil.error(myException.getCode(), myException.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResultUtil.error(ExceptionEnum.METHOD_FAILED);
        } else if (e instanceof MissingServletRequestParameterException) {
            return ResultUtil.error(ExceptionEnum.PARAMETER_ERROR);
        } else if (e instanceof DataIntegrityViolationException) {
            return ResultUtil.error(ExceptionEnum.DATABASE_ERROR);
        }
        LOGGER.error("【系统异常】{}", e);
        return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
    }
}
