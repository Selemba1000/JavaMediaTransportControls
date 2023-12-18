package me.selemba.linux;

import org.freedesktop.dbus.connections.impl.DBusConnection;

public interface SignaledDBusPropertyInterface extends DBusPropertyInterface {

    void signal();

    void ProvideConnection(DBusConnection connection);
}
