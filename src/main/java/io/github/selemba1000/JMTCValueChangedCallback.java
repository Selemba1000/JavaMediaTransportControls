package io.github.selemba1000;

/**
 * Interface for callback when value of property changes.
 * @param <T> the Type of value that changed
 */
public interface JMTCValueChangedCallback<T> {

    /**
     * Callback for when property changes
     * @param property the property that changed
     */
    void callback(T property);

}
