package com.example.jpamvc.myUtil;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PrettyTimeStringTest {

    @Test
    void convert() {
        String now = PrettyTimeString.convert(LocalDateTime.now());
        System.out.println("now = " + now);

        System.out.println("PrettyTimeString.get(LocalDateTime.of(2023,1,22,00,27,10)) = " +
                            PrettyTimeString.convert(LocalDateTime.of(2023, 1, 22, 00, 27, 10)));
        System.out.println("PrettyTimeString.get(LocalDateTime.of(2023,1,21,00,27,10)) = " +
                            PrettyTimeString.convert(LocalDateTime.of(2023, 1, 21, 00, 27, 10)));
        System.out.println("PrettyTimeString.get(LocalDateTime.of(2022,12,21,00,27,10)) = " +
                            PrettyTimeString.convert(LocalDateTime.of(2022, 12, 21, 00, 27, 10)));
        System.out.println("PrettyTimeString.get(LocalDateTime.of(2021,12,21,00,27,10)) = " +
                            PrettyTimeString.convert(LocalDateTime.of(2021, 12, 21, 00, 27, 10)));
        System.out.println("PrettyTimeString.get(LocalDateTime.of(2020,12,21,00,27,10)) = " +
                            PrettyTimeString.convert(LocalDateTime.of(2020, 12, 21, 00, 27, 10)));


    }
}