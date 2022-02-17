package com.cbadmin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
public class WorkerV implements Serializable {

    private static final long serialVersionUID = 1774759821885607540L;


    /**
     * id
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 银行卡号
     */
    private String bankcardNumber;

    /**
     * 开户行
     */
    private String bankOrg;

    /**
     * 联系方式
     */
    private String contractInfo;

    /**
     * photoId
     */
    private String photoId;

    /**
     * 照片
     */
    private String photoUrl;


    /**
     * 阵营id
     */
    private Long campId;

    /**
     * 阵营
     * */
    private String campName;

    /**
     * 阵营颜色
     */
    private String campColor;


}
