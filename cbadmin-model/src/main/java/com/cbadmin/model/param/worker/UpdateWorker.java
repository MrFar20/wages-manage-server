package com.cbadmin.model.param.worker;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdateWorker implements Serializable {

    private static final long serialVersionUID = 7216261182166580672L;

    @NotBlank
    @Length(max = 40)
    private String id;

    /**
     * 姓名
     */
    @Length(max = 20)
    private String name;

    /**
     * 身份证号
     */
    @Length(max = 20)
    private String idNumber;

    /**
     * 银行卡号
     */
    @Length(max = 28)
    private String bankcardNumber;

    /**
     * 开户行
     */
    @Length(max = 28)
    private String bankOrg;

    /**
     * 联系方式
     */
    @Length(max = 28)
    private String contractInfo;


    /**
     * 照片id
     */
    @Length(max = 64)
    private String photoId;

    /**
     * 阵营id
     */
    private Long campId;

}
