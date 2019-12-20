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

    @Field(index = 44, length = 4)
    private int somePrimitiveInteger;

    @Field(index = 48, length = 4)
    private Integer someInteger;

    @Field(index = 52, length = 6)
    private Long someLongValue;

    @Field(index = 58, length = 5)
    private Boolean someFlag;

    private String ignoredField;

    private int anotherIgnoredField;

    @Field(index = 63, length = 1)
    private AccountType accountType;

    @Field(index = 64, length = 20, handler = FirstNameHandler.class)
    private FirstName firstName;

    @Field(index = 84, length = 30, handler = LastNameHandler.class)
    private LastName lastName;

}
