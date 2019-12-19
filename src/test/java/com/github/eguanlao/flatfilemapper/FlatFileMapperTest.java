package com.github.eguanlao.flatfilemapper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

public class FlatFileMapperTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    public void should() throws IOException {
        // Given
        FlatFileMapper flatFileMapper = FlatFileMapper.builder()
                .headerType(Header.class)
                .recordType(Account.class)
                .trailerType(Trailer.class)
                .build();

        Header expectedHeader = new Header("H", LocalDate.parse("20191219", dateTimeFormatter));
        List<Account> expectedAccounts = Arrays.asList(
                new Account(1, "john@gmail.com", LocalDate.parse("19700101", dateTimeFormatter)),
                new Account(2, "jane@yahoo.com", LocalDate.parse("19811231", dateTimeFormatter))
        );
        Trailer expectedTrailer = new Trailer("T", 2);

        InputStream inputStream = ofNullable(this.getClass().getClassLoader().getResourceAsStream("com/github/eguanlao/flatfilemapper/file.txt")).orElseThrow(NullPointerException::new);

        // When
        Object[] mappingResult = flatFileMapper.map(inputStream, StandardCharsets.UTF_8);

        // Then
        assertThat(mappingResult[0]).isEqualTo(expectedHeader);
        assertThat(mappingResult[1]).isEqualTo(expectedAccounts);
        assertThat(mappingResult[2]).isEqualTo(expectedTrailer);
    }

}
