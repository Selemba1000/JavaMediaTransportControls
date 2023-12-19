package io.github.selemba1000.windows;

import com.sun.jna.IntegerType;

public class UnsignedInt extends IntegerType {
    @SuppressWarnings("unused")
    public UnsignedInt() {
        super(4, true);
    }

    UnsignedInt(int value) {
        super(4, value, true);
    }
}