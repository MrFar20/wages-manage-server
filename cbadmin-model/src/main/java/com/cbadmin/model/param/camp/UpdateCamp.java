package com.cbadmin.model.param.camp;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateCamp implements Serializable {

    private static final long serialVersionUID = 1338945520585652885L;

    @NotNull
    private Long id;

    @NotBlank
    @Length(max = 12)
    private String name;


    @Length(max = 16)
    private String color;
}
