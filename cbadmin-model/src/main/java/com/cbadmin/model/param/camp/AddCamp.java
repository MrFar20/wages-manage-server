package com.cbadmin.model.param.camp;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddCamp implements Serializable {


    private static final long serialVersionUID = 8338963121377725156L;

    @NotNull
    @Length(max = 12)
    private String name;

    @Length(max = 16)
    private String color;
}
