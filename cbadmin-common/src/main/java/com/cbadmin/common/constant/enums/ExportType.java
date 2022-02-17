package com.cbadmin.common.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExportType {

    PHOTO("photo", "图片"),
    EXCEL("excel", "表格"),
    PHOTO_AND_EXCEL("photo-excel", "表格和图片")
    ;

    private String type;
    private String desc;


    public static ExportType getExportType(String type) {
        for (ExportType et : values()) {
            if (et.type.equals(type)) return et;
        }
        return null;
    }

}
