package com.visualart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // вибір конфігурації для тестів
class VisualartServiceApplicationTest {

    @Test
    void contextLoads() {}
}
