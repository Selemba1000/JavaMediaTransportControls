package me.selemba.linux;

import org.freedesktop.dbus.TypeRef;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.interfaces.DBusInterface;

import java.util.List;

@DBusInterfaceName("org.mpris.MediaPlayer2")
@DBusProperty(name = "Identity", type = String.class, access = Access.READ)
@DBusProperty(name = "DesktopEntry", type = String.class, access = Access.READ)
@DBusProperty(name = "SupportedMimeTypes", type = MPRISPlayer2.PropertySupportedMimeTypesType.class, access = Access.READ)
@DBusProperty(name = "SupportedUriSchemes", type = MPRISPlayer2.PropertySupportedUriSchemesType.class, access = Access.READ)
@DBusProperty(name = "HasTrackList", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanQuit", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanSetFullscreen", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "Fullscreen", type = Boolean.class, access = Access.READ_WRITE)
@DBusProperty(name = "CanRaise", type = Boolean.class, access = Access.READ)
public interface MPRISPlayer2 extends DBusInterface {

    void Raise();

    void Quit();

    interface PropertySupportedMimeTypesType extends TypeRef<List<String>> {

    }

    interface PropertySupportedUriSchemesType extends TypeRef<List<String>> {

    }

}
