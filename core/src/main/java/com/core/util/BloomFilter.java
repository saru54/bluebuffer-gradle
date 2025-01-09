package com.core.util;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.zip.CRC32;

public class BloomFilter {
    private int size;
    private int hashFunctionCount;
    private BitSet bitSet;

    public BloomFilter(int size, int hashFunctionCount) {
        this.size = size;
        this.hashFunctionCount = hashFunctionCount;
        this.bitSet = new BitSet(size);
    }

    public void add(String value){
        int[] result = getHashes(value);
        for (int index: result
             ) {
            bitSet.set(Math.abs(index%size));
        }
    }
    public boolean mightContain(String value){
        int[] result = getHashes(value);
        for (int index: result
             ) {
            if(!bitSet.get(Math.abs(index%size))){
                return false;
            }
        }
        return true;
    }
    public int[] getHashes(String value){
        int[] hashValues = new int[hashFunctionCount];
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        for( int i=0;i<hashFunctionCount;i++){
            CRC32 crc32 = new CRC32();
            crc32.update(bytes);
            crc32.update(i);
            hashValues[i] =(int) crc32.getValue();
        }

        return hashValues;
    }





}
