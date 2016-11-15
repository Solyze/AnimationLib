package me.megamichiel.animationlib.bukkit;

import java.util.ArrayList;
import java.util.List;

public class NBTList {

    private final NBTUtil util;
    private final Object handle;
    private final List<Object> list;

    public NBTList() {
        util = NBTUtil.getInstance();
        NBTUtil.Modifier<List> modifier = util.modifier(List.class);
        handle = modifier.wrap(new ArrayList<>());
        list = modifier.unwrap(handle);
    }

    public NBTList(Object handle) {
        list = (util = NBTUtil.getInstance()).modifier(List.class).unwrap(this.handle = handle);
    }

    public void set(int index, Object value) {
        if (value == null) list.remove(index);
        else list.set(index, util.wrap(value));
    }

    public void add(Object value) {
        list.add(util.wrap(value));
    }

    public Object get(int index) {
        Object o = list.get(index);
        return util.resolve(o.getClass()).unwrap(o);
    }

    public String getString(int index) {
        Object o = get(index);
        return isPrimitive(o) ? o.toString() : (String) o;
    }

    public Number getNumber(int index) {
        return (Number) get(index);
    }

    public boolean getBoolean(int index) {
        return getNumber(index).intValue() != 0;
    }

    public byte getByte(int index) {
        return getNumber(index).byteValue();
    }

    public short getShort(int index) {
        return getNumber(index).shortValue();
    }

    public int getInt(int index) {
        return getNumber(index).intValue();
    }

    public long getLong(int index) {
        return getNumber(index).longValue();
    }

    public float getFloat(int index) {
        return getNumber(index).floatValue();
    }

    public double getDouble(int index) {
        return getNumber(index).doubleValue();
    }

    public byte[] getByteArray(int index) {
        Object o = get(index);
        return o instanceof byte[] ? (byte[]) o : null;
    }

    public int[] getIntArray(int index) {
        Object o = get(index);
        return o instanceof int[] ? (int[]) o : null;
    }
    
    public NBTTag getTag(int index) {
        try {
            return new NBTTag(get(index));
        } catch (IllegalStateException ex) {
            throw new ClassCastException();
        }
    }

    public NBTList getList(int index) {
        try {
            return new NBTList(get(index));
        } catch (IllegalStateException ex) {
            throw new ClassCastException();
        }
    }

    public Object getHandle() {
        return handle;
    }

    private static boolean isPrimitive(Object o) {
        return o instanceof Number || o instanceof Character || o instanceof Boolean;
    }
}
