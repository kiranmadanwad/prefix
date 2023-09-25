package com.ip.subnet.prefix.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubnetCheckerTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIPv4InRange() {
        String subnetCIDR = "192.168.1.0/24";
        String ipAddressToCheck = "192.168.1.5";
        assertTrue(SubnetChecker.isInRange(subnetCIDR, ipAddressToCheck));
    }

    @Test
    void testIPv4NotInRange() {
        String subnetCIDR = "192.168.1.0/24";
        String ipAddressToCheck = "10.0.0.5";
        assertFalse(SubnetChecker.isInRange(subnetCIDR, ipAddressToCheck));
    }

    @Test
    void testIPv6InRange() {
        String subnetCIDR = "2001:db8:1:2::/64";
        String ipAddressToCheck = "2001:db8:1:2::5";
        assertTrue(SubnetChecker.isInRange(subnetCIDR, ipAddressToCheck));
    }

    @Test
    void testIPv6NotInRange() {
        String subnetCIDR = "2001:db8:1:2::/64";
        String ipAddressToCheck = "2001:db8:3:4::5";
        assertFalse(SubnetChecker.isInRange(subnetCIDR, ipAddressToCheck));
    }

    @Test
    void testInvalidInput() {
        String subnetCIDR = "invalid";
        String ipAddressToCheck = "192.168.1.5";
        assertFalse(SubnetChecker.isInRange(subnetCIDR, ipAddressToCheck));
    }

}
