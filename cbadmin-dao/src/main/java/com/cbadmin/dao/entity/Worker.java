package com.cbadmin.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "idNumber"),
        @Index(columnList = "bankcardNumber")
})
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Worker implements Serializable {


    private static final long serialVersionUID = -7927007928269465119L;


    /**
     * id
     */
    @Id
    @Column(columnDefinition = "varchar(32)")
    private String id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(columnDefinition = "datetime(3) not null comment '创建时间'")
    private Date createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(columnDefinition = "datetime(3) not null comment '更新时间'")
    private Date updateTime;


    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(20) not null comment '姓名'")
    private String name;

    /**
     * 身份证号
     */
    @Column(columnDefinition = "varchar(20) not null comment '身份证号'")
    private String idNumber;

    /**
     * 银行卡号
     */
    @Column(columnDefinition = "varchar(28) not null comment '银行卡号'")
    private String bankcardNumber;

    /**
     * 开户行
     */
    @Column(columnDefinition = "varchar(28) not null comment '开户行'")
    private String bankOrg;

    /**
     * 联系方式
     */
    @Column(columnDefinition = "varchar(28) not null comment '联系方式'")
    private String contractInfo;

    /**
     * 照片id
     */
    @Column(columnDefinition = "varchar(64) not null comment '照片id'")
    private String photoId;

    /**
     * 阵营id
     */
    @Column(columnDefinition = "bigint comment '阵营id'")
    private Long campId;

}
