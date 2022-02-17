package com.cbadmin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CampV implements Serializable {

    private static final long serialVersionUID = -3421547550277551208L;


    /**
     * 阵营id
     */
    private Long id;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 阵营名
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

}
