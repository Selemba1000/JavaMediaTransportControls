package io.github.selemba1000.windows;

import com.sun.jna.IntegerType;

class UnsignedInt extends IntegerType {
    @SuppressWarnings("unused")
    UnsignedInt() {
        super(4, true);
    }

    UnsignedInt(int value) {
        super(4, value, true);
    }
}