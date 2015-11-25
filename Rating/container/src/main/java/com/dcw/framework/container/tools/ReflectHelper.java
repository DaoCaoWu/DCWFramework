package com.dcw.framework.container.tools;

import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ****************************************************************************
 * Copyright @ 2009 - 2015 www.9game.cn All Rights Reserved
 * <p/>
 * Creation    : 2015-04-07
 * Author      : lihq@ucweb.com
 * Description : Tell me what does this class do
 * ****************************************************************************
 */
public final class ReflectHelper {

    public static <T> T loadClass(String classFullName) {
        if (TextUtils.isEmpty(classFullName)) {
            return null;
        }

        Object object = null;
        try {
            Class<?> clazz = Class.forName(classFullName);
            if(clazz == null) {
                return null;
            }

            Constructor<?> constructor = clazz.getDeclaredConstructor();
            object = constructor.newInstance();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return (T)object;
    }

    public static Field findField(Object instance, String name)
            throws NoSuchFieldException
    {
        for (Class clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(name);

                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                return field;
            }
            catch (NoSuchFieldException e)
            {
            }
        }
        throw new NoSuchFieldException(new StringBuilder().append("Field ").append(name).append(" not found in ").append(instance.getClass()).toString());
    }

    public static Method findMethod(Object instance, String name, Class<?>[] parameterTypes)
            throws NoSuchMethodException
    {
        for (Class clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);

                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                return method;
            }
            catch (NoSuchMethodException e)
            {
            }
        }
        throw new NoSuchMethodException(new StringBuilder().append("Method ").append(name).append(" with parameters ").append(Arrays.asList(parameterTypes)).append(" not found in ").append(instance.getClass()).toString());
    }
}
