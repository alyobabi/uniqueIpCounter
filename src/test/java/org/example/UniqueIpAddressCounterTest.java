package org.example;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UniqueIpAddressCounterTest {

    @Test
    public void shouldGetUniqueIPCount() throws IOException {
        assertEquals(5, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/someIpsFile"));
    }

    @Test
    public void shouldGetUniqueIPCountWhenFileIsBig() throws IOException {
        assertEquals(255, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/bigFile"));
    }

    @Test
    public void shouldGetUniqueIPCountWhenNoAddresses() throws IOException {
        assertEquals(0, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/emptyFile"));
    }

    @Test
    public void shouldGetUniqueIPCountWhenFileContainsNonAddress() throws IOException {
        assertEquals(1, UniqueIpAddressCounter.getUniqueIPCount("src/test/resources/containsNoIpFile"));
    }

}