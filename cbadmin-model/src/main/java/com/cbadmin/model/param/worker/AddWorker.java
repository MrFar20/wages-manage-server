package com.cbadmin.model.param.worker;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AddWorker implements Serializable {

    private static final long serialVersionUID = -5362408115841454606L;


    /**
     * 姓名
     */
    @NotBlank
    @Length(max = 20)
    private String name;

    /**
     * 身份证号
     */
    @NotBlank
    @Length(max = 20)
    private String idNumber;

    /**
     * 银行卡号
     */
    @NotBlank
    @Length(max = 28)
    private String bankcardNumber;

    /**
     * 开户行
     */
    @NotBlank
    @Length(max = 28)
    private String bankOrg;

    /**
     * 联系方式
     */
    @NotBlank
    @Length(max = 28)
    private String contractInfo;


    /**
     * 照片id
     */
    @NotBlank
    @Length(max = 64)
    private String photoId;


    /**
     * 阵营id
     */
    private Long campId;

}
