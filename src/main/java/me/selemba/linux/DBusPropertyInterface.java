package me.selemba.linux;

import org.freedesktop.dbus.types.Variant;

import java.util.Map;

interface DBusPropertyInterface {
    Map<String, Variant<?>> getVariantMap();

    String getFieldName();
}
