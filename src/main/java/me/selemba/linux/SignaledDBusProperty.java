package me.selemba.linux;

import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.Properties;

import java.util.List;

class SignaledDBusProperty<T> extends DBusProperty<T> implements SignaledDBusPropertyInterface {

    private final String objectPath;
    private final String interfaceName;
    private DBusConnection connection;

    @Override
    public void ProvideConnection(DBusConnection connection){
        this.connection = connection;
    }

    SignaledDBusProperty(T field, String fieldName, String objectPath, String interfaceName) {
        super(field,fieldName);
        this.objectPath = objectPath;
        this.interfaceName = interfaceName;
    }

    @Override
    public void setValue(T value){
        super.setValue(value);
        signal();
    }

    @Override
    public void signal(){
        try {
            Properties.PropertiesChanged msg = new Properties.PropertiesChanged(objectPath, interfaceName, this.getVariantMap(), List.of());
            connection.sendMessage(msg);
        }catch (DBusException e){
            //TODO logging
        }
    }

}
