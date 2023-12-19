package me.selemba.linux;

import org.freedesktop.dbus.connections.impl.DBusConnection;

interface SignaledDBusPropertyInterface extends DBusPropertyInterface {

    void signal();

    void ProvideConnection(DBusConnection connection);
}
