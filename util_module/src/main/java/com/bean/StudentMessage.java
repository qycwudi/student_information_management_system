package com.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Lang;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2020-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ID_WORKER)
    private long id;

    private String stuName;

    private String stuSex;

    private String stuNation;

    private String stuGrade;

    private String stuMajor;

    private String stuClassId;

    private String stuPoliticsStatus;

    private String stuIdCard;

    private String stuImgUrl;

    private String stuDuty;

    private String stuPhoneNumber;

    private String stuInsId;

    private String stuStateId;

    private String stuStateInfoId;

    private String stuPassword;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;


}
