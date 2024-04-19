package io.github.selemba1000;

/**
 * Interface for seek lambda callback.
 */
public interface JMTCSeekCallback {
    /**
     * The callback function.
     * @param position The position that was seeked to in ms.
     */
    void callback(Long position);
}
