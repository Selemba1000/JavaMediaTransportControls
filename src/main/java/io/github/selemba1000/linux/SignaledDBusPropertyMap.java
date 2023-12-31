package io.github.selemba1000.linux;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.Properties;
import org.freedesktop.dbus.types.Variant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SignaledDBusPropertyMap<V> implements SignaledDBusPropertyInterface, DBusPropertyInterface {
    private final HashMap<String, V> field = new HashMap<>();

    private final String fieldName;
    private final String objectPath;
    private final String interfaceName;
    private DBusConnection connection;

    SignaledDBusPropertyMap(String fieldName, String objectPath, String interfaceName) {
        this.fieldName = fieldName;
        this.objectPath = objectPath;
        this.interfaceName = interfaceName;
    }

    @Override
    public void ProvideConnection(DBusConnection connection) {
        this.connection = connection;
    }

    V getAt(String index) {
        return field.get(index);
    }

    void setAt(String index, V value) {
        field.put(index, value);
        signal();
    }

    void setAll(Map<String, V> map) {
        field.putAll(map);
        signal();
    }

    @SuppressWarnings("unused")
    void removeAt(String index) {
        field.remove(index);
        signal();
    }

    void clear() {
        field.clear();
        signal();
    }

    @Override
    public Map<String, Variant<?>> getVariantMap() {
        HashMap<String, Variant<?>> map = new HashMap<>();
        for (Map.Entry<String, V> entry : field.entrySet()) {
            map.put(entry.getKey(), new Variant<>(entry.getValue()));
        }
        return Map.of(fieldName, new Variant<>(map, "a{sv}"));
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void signal() {
        try {
            Properties.PropertiesChanged msg = new Properties.PropertiesChanged(objectPath, interfaceName, this.getVariantMap(), List.of());
            connection.sendMessage(msg);
        } catch (DBusException e) {
            //TODO logging
        }
    }

}
