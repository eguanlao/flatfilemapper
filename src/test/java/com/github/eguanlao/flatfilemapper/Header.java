package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.annotations.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header {

    @Field(index = 0, length = 1)
    private String indicator;

    @Field(index = 1, length = 8, pattern = "yyyyMMdd")
    private LocalDate fileDate;

}
