package com.iot.model.acceptParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="以数组形式输入需要删除的id")
public class IdsOfDelete<T> {
    @ApiModelProperty(value = "在此处测试时，请注意中英文逗号！")
    int[] ids;
}
