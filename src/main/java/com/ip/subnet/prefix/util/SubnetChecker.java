package com.ip.subnet.prefix.util;

import com.googlecode.ipv6.IPv6Address;
import com.googlecode.ipv6.IPv6Network;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;

@Slf4j
public class SubnetChecker {

    public static boolean isInRange(String subnetCIDR, String ipAddressToCheck) {
        try {
            if(subnetCIDR.contains(".") && ipAddressToCheck.contains(".")) {
                SubnetUtils subnetUtils =  new SubnetUtils(subnetCIDR);
                subnetUtils.setInclusiveHostCount(true);
                return subnetUtils.getInfo().isInRange(ipAddressToCheck);
            } else if(subnetCIDR.contains(":") && ipAddressToCheck.contains(":")) {
                return IPv6Network.fromString(subnetCIDR).contains(IPv6Address.fromString(ipAddressToCheck));
            }
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }
}
