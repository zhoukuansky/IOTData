package com.iot.model.acceptParam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "接收系统详细信息")
public class SystemParam<T> {
    @ApiModelProperty(value = "这里是系统id，新建时不填，在更新时需要填写")
    private int id;

    @ApiModelProperty(value = "系统名字")
    private String name;

    @ApiModelProperty(value = "描述信息,非必填")
    private String description;

    @ApiModelProperty(value = "方向选择，此项必须在direction表中，类型为String")
    private String direction;

    @ApiModelProperty(value = "操作系统选择，此项必须在operation表中，类型为String")
    private String operation;

    @JsonIgnore
    private Integer userId;
}
