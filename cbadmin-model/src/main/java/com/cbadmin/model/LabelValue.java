package com.cbadmin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelValue<T> implements Serializable {


    private static final long serialVersionUID = 5308264018671812989L;

    private String label;

    private T value;
}
