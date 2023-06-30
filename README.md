# Unique IP address counter


## Problem Statement
Given a simple text file with IPv4 addresses. One line is one address  
It's needed to count unique IP addresses

## Solution

**InetAddress**

IP address is contained 32 bits  
&
Integer stores 32 bits

_java.net.Inet4Address.hashCode_ uses _java.net.InetAddress.InetAddressHolder.address_ 

        /**
         * Holds a 32-bit IPv4 address.
         */
        int address;

        int getAddress() {
            return address;
        }

So in this case hashCode is unique for every IP address as hashcode return address

**BitSet**  
Total of IP addresses 2^32  
Integer range is from -2,147,483,648 to 2,147,483,647

two BitSet objects are used, `positiveHashCodeSet` and `negativeHashCodeSet`, are used to track the occurrence of unique IP addresses. The bits in these BitSet objects are set based on the hash codes of the IP addresses encountered in the input file