package com.cbadmin.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SpwdInput implements Serializable {

    private static final long serialVersionUID = -9094852599540574702L;

    @NotBlank
    @Length(min = 32, max = 32)
    private String spwd;

}
