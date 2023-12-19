package io.github.selemba1000.linux;

import org.freedesktop.dbus.connections.impl.DBusConnection;

interface SignaledDBusPropertyInterface extends DBusPropertyInterface {

    void signal();

    void ProvideConnection(DBusConnection connection);
}
