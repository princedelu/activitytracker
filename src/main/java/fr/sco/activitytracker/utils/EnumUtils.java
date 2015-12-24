package fr.sco.activitytracker.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by stiffler on 16/03/15.
 */
public class EnumUtils {

    public static <E extends Enum<E>> boolean enumTypeContains(Class<E> e, String s) {
        try {
            Method enumDir = Class.class.getDeclaredMethod("enumConstantDirectory");
            enumDir.setAccessible(true);
            Map<?,?> dir = (Map<?,?>)enumDir.invoke(e);
            return dir.containsKey(s);
        }
        catch(NoSuchMethodException ex) {
            throw new Error(ex);
        }
        catch(IllegalAccessException ex) {
            throw new Error(ex);
        }
        catch(InvocationTargetException ex) {
            throw new Error(ex.getCause());
        }
    }
}
