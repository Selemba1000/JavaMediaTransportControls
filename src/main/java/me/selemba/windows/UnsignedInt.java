package me.selemba.windows;

import com.sun.jna.IntegerType;

class UnsignedInt extends IntegerType {
    UnsignedInt() {
        super(4, true);
    }

    UnsignedInt(int value){
        super(4,value,true);
    }
}