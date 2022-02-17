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
@Table(name = "camp", indexes = {
        @Index(columnList = "name"),
})
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Camp implements Serializable {


    private static final long serialVersionUID = 2764490734211716433L;

    /**
     * 阵营id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


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
     * 阵营名
     */
    @Column(columnDefinition = "varchar(12) not null comment '阵营名'")
    private String name;


    /**
     * 颜色
     */
    @Column(columnDefinition = "varchar(16) comment '颜色'")
    private String color;
}
