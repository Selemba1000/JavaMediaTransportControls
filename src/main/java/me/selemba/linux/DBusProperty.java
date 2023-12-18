package me.selemba.linux;

import org.freedesktop.dbus.types.Variant;

import java.util.Map;

public class DBusProperty<T> implements DBusPropertyInterface {

    private final String fieldName;

    DBusProperty(T field, String fieldName){
        this.field=field;
        this.fieldName = fieldName;
    }

    private T field;

    public T getValue(){
        return field;
    }

    @Override
    public Map<String,Variant<?>>getVariantMap(){
        if (field==null)return null;
        return Map.of(fieldName,new Variant<>(field));
    }

    @Override
    public String getFieldName(){
        return fieldName;
    }

    public void setValue(T value){
        field = value;
    }
}
