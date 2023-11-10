package me.selemba.windows;

import com.sun.jna.IntegerType;

public class UnsignedInt extends IntegerType {
    public UnsignedInt() {
        super(4, true);
    }

    public UnsignedInt(int value){
        super(4,value,true);
    }
}