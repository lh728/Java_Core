package com_second.Annotation;

import java.awt.event.ActionListener;
import java.lang.reflect.*;

public class ActionListenerInstaller {
    public static void processAnnotations(Object obj){
        try {
            Class<?> cl = obj.getClass();
            for (Method m : cl.getDeclaredMethods()){
                ActionListenerFor a = m.getAnnotation(ActionListenerFor.class);
                if (a != null){
                    Field f = cl.getDeclaredField(a.source());
                    f.setAccessible(true);
                    addListener(f.get(obj),obj,m);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addListener(Object source, final Object param, final Method m) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var handler = new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return m.invoke(param);
            }
        };

        Object listener = Proxy.newProxyInstance(null, new Class[]{ActionListener.class}, handler);
        Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
        adder.invoke(source,listener);

    }
}


