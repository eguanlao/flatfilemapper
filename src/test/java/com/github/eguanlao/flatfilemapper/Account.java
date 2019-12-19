package com.github.eguanlao.flatfilemapper;

import com.github.eguanlao.flatfilemapper.annotations.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Field(index = 0, length = 6)
    private long id;

    @Field(index = 6, length = 30)
    private String emailAddress;

    @Field(index = 36, length = 8)
    private LocalDate dateOfBirth;

}
