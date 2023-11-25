package me.selemba.linux;

import org.freedesktop.dbus.connections.impl.DBusConnection;

public interface SignaledDBusPropertyInterface extends DBusPropertyInterface {

    void signal();

    public void ProvideConnection(DBusConnection connection);
}
