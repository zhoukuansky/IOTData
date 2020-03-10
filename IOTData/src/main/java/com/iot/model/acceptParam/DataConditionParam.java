package com.iot.model.acceptParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Data
@ApiModel(value = "查询某个传感器下的数据")
public class DataConditionParam<T>{

    @ApiModelProperty(value = "传感器id")
    private int id;

    @ApiModelProperty(value = "开始时间,默认值为系统开始运行时间。模型：2020-3-9 18:06:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "结束时间，默认值为当前时间。模型：2020-3-9 18:06:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


    public DataConditionParam(){
        //给开始时间设置默认值，即系统开始运行的时间
        Calendar cal = Calendar.getInstance();
        cal.set(2020,2,9);
        startTime=cal.getTime();
        endTime=new Date();
    }
}
