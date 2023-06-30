package org.example;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UniqueIpAddressCounterTest {

    @Test
    public void shouldGetUniqueIPCount() throws IOException {
        assertEquals(5, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/ips.txt"));
    }

    @Test
    public void shouldGetUniqueIPCountWhenNoAddresses() throws IOException {
        assertEquals(0, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/emptyFile.txt"));
    }

    @Test
    public void shouldGetUniqueIPCountWhenFileContainsNonAddress() throws IOException {
        assertEquals(1, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/ipsWithNoIp.txt"));
    }

}