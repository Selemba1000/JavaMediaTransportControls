package io.github.selemba1000;

/**
 * Interface for callback when value of property changes.
 */
public interface JMTCValueChangedCallback<T> {

    void callback(T property);

}
