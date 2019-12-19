package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.annotations.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trailer {

    @Field(index = 0, length = 1)
    private String indicator;

    @Field(index = 1, length = 6)
    private int totalRecords;

}
