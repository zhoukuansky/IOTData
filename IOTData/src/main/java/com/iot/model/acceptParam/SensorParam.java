package com.iot.model.acceptParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="接收传感器详细信息")
public class SensorParam<T> {
    @ApiModelProperty(value = "这里是传感器id，新建时不填，在更新时需要填写")
    private int id;

    @ApiModelProperty(value = "传感器名字")
    private String name;

    @ApiModelProperty(value = "描述信息(新建时，非必填，其余必填)")
    private String description;

    @ApiModelProperty(value = "数据类型，此项必须在datatype表中，类型为String")
    private String dataType;

    @ApiModelProperty(value = "开关状态，1开，0关。不填默认为关")
    private int status;

    @ApiModelProperty(value = "新建时必填，更新时不填")
    private int systemId;
}
