package com.iot.model.acceptParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="接收设备详细信息")
    public class DeviceParam<T> {
    @ApiModelProperty(value = "这里是设备id，新建时不填，在更新时需要填写")
    private int id;

    @ApiModelProperty(value = "设备名字")
    private String name;

    @ApiModelProperty(value = "描述信息(新建时，非必填，其余必填)")
    private String description;

    @ApiModelProperty(value = "开关状态，1开，0关。不填默认为关")
    private int status;

    @ApiModelProperty(value = "新建时必填，更新时不填")
    private int systemId;

}
