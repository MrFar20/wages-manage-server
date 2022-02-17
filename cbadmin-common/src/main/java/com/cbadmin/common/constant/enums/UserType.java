package com.cbadmin.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户类型
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {

    USER("user", "用户"),
    ADMIN("admin", "管理员");

    private String value;
    private String desc;

    public static UserType getUserType(String value) {
        for (UserType ut : UserType.values()) {
            if (ut.getValue().equals(value)) {
                return ut;
            }
        }
        return null;
    }

}
