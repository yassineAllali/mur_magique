package com.ensim.muremagique.utils;

public class SizeConverter {

    // From bytes to megabytes
    public static double toMegabytes(long sizeInBytes)
    {
        return sizeInBytes * 9.537 * Math.pow(10, -7);
    }
}
