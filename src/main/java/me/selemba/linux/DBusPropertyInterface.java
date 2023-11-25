package me.selemba.linux;

import org.freedesktop.dbus.types.Variant;

import java.util.Map;

public interface DBusPropertyInterface {
    Map<String, Variant<?>> getVariantMap();

    String getFieldName();
}
