package io.github.selemba1000.linux;

import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.annotations.DBusProperty;
import org.freedesktop.dbus.annotations.DBusProperty.Access;
import org.freedesktop.dbus.interfaces.DBusInterface;

/**
 * Representation for the MPRIS2 MadiaPlayer DBus Interface
 */
@DBusInterfaceName("org.mpris.MediaPlayer2")
@DBusProperty(name = "Identity", type = String.class, access = Access.READ)
@DBusProperty(name = "DesktopEntry", type = String.class, access = Access.READ)
@DBusProperty(name = "SupportedMimeTypes", type = String[].class, access = Access.READ)
@DBusProperty(name = "SupportedUriSchemes", type = String[].class, access = Access.READ)
@DBusProperty(name = "HasTrackList", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanQuit", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "CanSetFullscreen", type = Boolean.class, access = Access.READ)
@DBusProperty(name = "Fullscreen", type = Boolean.class)
@DBusProperty(name = "CanRaise", type = Boolean.class, access = Access.READ)
public interface MPRISPlayer2 extends DBusInterface {

    /**
     * Request to raise the program to the foreground.
     */
    void Raise();

    /**
     * Request to quit the program.
     */
    void Quit();

}
