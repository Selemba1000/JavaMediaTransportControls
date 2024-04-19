package io.github.selemba1000.windows;

import com.sun.jna.IntegerType;

/**
 * Class to represent unsigned integers correctly.
 */
public class UnsignedInt extends IntegerType {
    /**
     * Constructor for unsigned 0 int
     */
    @SuppressWarnings("unused")
    public UnsignedInt() {
        super(4, true);
    }

    /**
     * Constructor for unsigned int with value.
     * @param value the value of the int
     */
    UnsignedInt(int value) {
        super(4, value, true);
    }
}