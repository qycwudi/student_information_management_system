package com.qyc.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * InnoDB free: 9216 kB
 * </p>
 *
 * @author testjava
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@ApiModel(value="BkStaff对象", description="InnoDB free: 9216 kB")
public class BkStaff implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    //    @ApiModelProperty(value = "员工电话")
    private String staffPhone;

    @TableField(fill = FieldFill.INSERT)
    private String role;
    @TableField(fill = FieldFill.INSERT)
    private String staffPassword;

    //    @ApiModelProperty(value = "员工姓名")
    private String staffName;

    //    @ApiModelProperty(value = "博客数量")
    @TableField("staff_bkNum")
    private Integer staffBknum;



    //    @ApiModelProperty(value = "头像url")
    @TableField("staff_headPath")
    private String staffHeadpath;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

}