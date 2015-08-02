package com.dcw.framework.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/2/12
 */
public class DCWAnnotation {

    private static Map<Class, String> sSetListenerMethodMap = new HashMap<Class, String>();

    private DCWAnnotation() {
        throw new AssertionError("No instances.");
    }

    /**
     * DO NOT USE: Exposed for generated code.
     */
    @SuppressWarnings("UnusedDeclaration") // Used by generated code.
    public static enum Finder {
        VIEW {
            @Override
            protected View findView(Object source, int id) {
                return ((View) source).findViewById(id);
            }

            @Override
            protected Context getContext(Object source) {
                return ((View) source).getContext();
            }

            @Override
            protected LayoutInflater getLayoutInflate(Object source) {
                return LayoutInflater.from(getContext(source));
            }
        },
        ACTIVITY {
            @Override
            protected View findView(Object source, int id) {
                return ((Activity) source).findViewById(id);
            }

            @Override
            protected Context getContext(Object source) {
                return (Activity) source;
            }

            @Override
            protected LayoutInflater getLayoutInflate(Object source) {
                return LayoutInflater.from(getContext(source));
            }
        },
        DIALOG {
            @Override
            protected View findView(Object source, int id) {
                return ((Dialog) source).findViewById(id);
            }

            @Override
            protected Context getContext(Object source) {
                return ((Dialog) source).getContext();
            }

            @Override
            protected LayoutInflater getLayoutInflate(Object source) {
                return LayoutInflater.from(getContext(source));
            }
        },
        LAYOUT_INFLATER {
            @Override
            protected View findView(Object source, int id) {
                return ((View) source).findViewById(id);
            }

            @Override
            protected Context getContext(Object source) {
                return ((LayoutInflater) source).getContext();
            }

            @Override
            protected LayoutInflater getLayoutInflate(Object source) {
                return (LayoutInflater)source;
            }
        };

        protected abstract View findView(Object source, int id);
        protected abstract Context getContext(Object source);
        protected abstract LayoutInflater getLayoutInflate(Object source);

    }
    private static final String TAG = DCWAnnotation.class.getSimpleName();

    private static boolean debug = false;

    /**
     * Control whether debug logging is enabled.
     */
    public static void setDebug(boolean debug) {
        DCWAnnotation.debug = debug;
    }

    /**
     * Inject annotated fields and methods in the specified {@link Activity}. The current content
     * view is used as the view root.
     *
     * @param target Target activity for field injection.
     */
    public static void inject(Activity target) {
        inject(target, target, Finder.ACTIVITY);
    }

    /**
     * Inject annotated fields and methods in the specified {@link View}. The view and its children
     * are used as the view root.
     *
     * @param target Target view for field injection.
     */
    public static void inject(View target) {
        inject(target, target, Finder.VIEW);
    }

    /**
     * Inject annotated fields and methods in the specified {@link Dialog}. The current content
     * view is used as the view root.
     *
     * @param target Target dialog for field injection.
     */
    public static void inject(Dialog target) {
        inject(target, target, Finder.DIALOG);
    }

    /**
     * Inject annotated fields and methods in the specified {@code target} using the {@code source}
     * {@link Activity} as the view root.
     *
     * @param target Target class for field injection.
     * @param source Activity on which IDs will be looked up.
     */
    public static void inject(Object target, Activity source) {
        inject(target, source, Finder.ACTIVITY);
    }

    /**
     * Inject annotated fields and methods in the specified {@code target} using the {@code source}
     * {@link View} as the view root.
     *
     * @param target Target class for field injection.
     * @param source View root on which IDs will be looked up.
     */
    public static void inject(Object target, View source) {
        inject(target, source, Finder.VIEW);
    }

    /**
     * Inject annotated fields and methods in the specified {@code target} using the {@code source}
     * {@link Dialog} as the view root.
     *
     * @param target Target class for field injection.
     * @param source Dialog on which IDs will be looked up.
     */
    public static void inject(Object target, Dialog source) {
        inject(target, source, Finder.DIALOG);
    }

    /**
     * Inject annotated fields and methods in the specified {@code target} using the {@code source}
     * {@link android.view.LayoutInflater} as the view root.
     *
     * @param target Target class for field injection.
     * @param source View root on which IDs will be looked up.
     */
    public static View inject(Object target, LayoutInflater source) {
       return inject(target, source, Finder.LAYOUT_INFLATER);
    }

    static View inject(Object target, Object source, Finder finder) {
        Class<?> targetClass = target.getClass();
        try {
            if (finder == Finder.LAYOUT_INFLATER) {
                if (source != null  && targetClass.isAnnotationPresent(InjectLayout.class)) {
                    source = finder.getLayoutInflate(source).inflate((targetClass.getAnnotation(InjectLayout.class)).value(), null);
                }
                if (source == null) {
                    throw new IllegalArgumentException("Must specify a layout resource with the @InjectLayout annotation on " + targetClass.getName());
                }
            }
            initAnnotatedType(target, source, finder);
            initAnnotatedFields(targetClass, target, source, finder);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject views for " + target, e);
        }
        if (source instanceof View) {
            return (View)source;
        } else {
            return null;
        }
    }

    public static void initAnnotatedType(Object target, final Object source, Finder finder) throws InvocationTargetException
            , IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<?> targetClass = target.getClass();
        switch (finder) {

            case ACTIVITY:
                if (source != null && ((Activity) source).getWindow() != null && targetClass.isAnnotationPresent(InjectLayout.class)) {
                    ((Activity) source).setContentView(finder.getLayoutInflate(source).inflate((targetClass.getAnnotation(InjectLayout.class)).value(), null));
                }
                if (source == null || ((Activity) source).getWindow() == null) {
                    throw new IllegalArgumentException("Must specify a layout resource with the @InjectLayout annotation on " + targetClass.getName());
                }
                break;
            case DIALOG:
                if (source != null && ((Dialog) source).getWindow() != null && targetClass.isAnnotationPresent(InjectLayout.class)) {
                    ((Dialog) source).setContentView(finder.getLayoutInflate(source).inflate((targetClass.getAnnotation(InjectLayout.class)).value(), null));
                }
                if (source == null || ((Dialog) source).getWindow() == null) {
                    throw new IllegalArgumentException("Must specify a layout resource with the @InjectLayout annotation on " + targetClass.getName());
                }
                break;
        }


        if (source == null) {
            throw new IllegalArgumentException("Must specify a layout resource with the @InjectLayout annotation on " + targetClass.getName());
        }
    }

    public static void initAnnotatedFields(Class clazz, Object target, Object source, Finder finder) throws InvocationTargetException
            , IllegalAccessException, NoSuchMethodException, InstantiationException {

        Field fields[] = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            Annotation[] annotations = field.getAnnotations();

            if (annotations == null || annotations.length == 0) {
                continue;
            }

            for (int j = 0; j < annotations.length; ++j) {
                Annotation anno = annotations[j];

                if (InjectView.class.isAssignableFrom(anno.getClass())) {
                    InjectView annotation = (InjectView) anno;
                    View view = finder.findView(source, annotation.value());
                    field.setAccessible(true);
                    field.set(target, view);

                    if (annotation.listeners().length > 0) {

                        setListenersForView(clazz, annotation, view, target);
                    }
                }
            }
        }
    }

    public static void setListenersForView(Class clazz, InjectView annotation, View view, Object listener) throws InvocationTargetException
            , IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class[] listeners = annotation.listeners();

        for (int j = 0; j < listeners.length; ++j) {
            Class listenerClass = listeners[j];

            if (!listenerClass.isAssignableFrom(clazz)) {
                return;
            }

            String methodName = sSetListenerMethodMap.get(listenerClass);
            if (methodName == null) {
                methodName = listenerClass.getSimpleName();

                // for interfaces from android.support.v4.**, Class.getSimpleName() may return names that contain the dollar sign
                // I have no idea whether this is a bug, the following workaround fixes the problem
                int index = methodName.lastIndexOf('$');
                if (index != -1) {
                    methodName = methodName.substring(index + 1);
                }
                methodName = "set" + methodName;

                sSetListenerMethodMap.put(listenerClass, methodName);
            }

            try {
                Method method = view.getClass().getMethod(methodName, listenerClass);
                method.invoke(view, listener);
            } catch (NoSuchMethodException e) {
                throw new NoSuchMethodException("No such method: " + listenerClass.getSimpleName() + "." + methodName
                        + ", you have to manually add the set-listener method to sSetListenerMethodMap.");
            }
        }
    }

    /**
     * Simpler version of {@link View#findViewById(int)} which infers the target type.
     */
    @SuppressWarnings({"unchecked", "UnusedDeclaration"}) // Checked by runtime cast. Public API.
    public static <T extends View> T findById(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * Simpler version of {@link Activity#findViewById(int)} which infers the target type.
     */
    @SuppressWarnings({"unchecked", "UnusedDeclaration"}) // Checked by runtime cast. Public API.
    public static <T extends View> T findById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    /**
     * Simpler version of {@link Dialog#findViewById(int)} which infers the target type.
     */
    @SuppressWarnings({"unchecked", "UnusedDeclaration"}) // Checked by runtime cast. Public API.
    public static <T extends View> T findById(Dialog dialog, int id) {
        return (T) dialog.findViewById(id);
    }
}
