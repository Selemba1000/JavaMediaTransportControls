package io.github.selemba1000.linux;

import org.freedesktop.dbus.types.Variant;

import java.util.Map;

class DBusProperty<T> implements DBusPropertyInterface {

    private final String fieldName;
    private T field;

    DBusProperty(T field, String fieldName) {
        this.field = field;
        this.fieldName = fieldName;
    }

    protected T getValue() {
        return field;
    }

    public void setValue(T value) {
        field = value;
    }

    @Override
    public Map<String, Variant<?>> getVariantMap() {
        if (field == null) return null;
        return Map.of(fieldName, new Variant<>(field));
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
