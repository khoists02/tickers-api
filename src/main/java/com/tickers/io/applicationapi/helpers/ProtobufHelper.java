package com.tickers.io.applicationapi.helpers;

import com.google.protobuf.Message;

import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProtobufHelper {
    public static <I, T> void mapIfNotNull(Supplier<I> getter, Consumer<T> setter, Function<I, T> function) {
        I value = getter.get();
        if (value == null)
            return;
        setter.accept(function.apply(value));
    }

    public static <I, T> void setNullIfDefault(Supplier<I> getter, Consumer<T> setter, Function<I, T> function) {
        I value = getter.get();
        if(value == null || isDefault(value))
        {
            setter.accept(null);
        }else{
            setter.accept(function.apply(value));
        }
    }

    public static <T, I> void setIfNotDefault(Supplier<I> getter, Consumer<T> setter, Function<I, T> function) {
        I value = getter.get();
        if (value == null)
            return;
        if(!isDefault(value))
            setter.accept(function.apply(value));
    }

    private static boolean isDefault(Object value)
    {
        if (value instanceof String && ((String) value).isBlank()){
            return true;
        }
        return false;
    }

    public static Class<?> iterableType(Class<?> type, String field) throws NoSuchMethodException {
        return adder(type, field).getParameterTypes()[0];
    }

    public static Method adder(Class<?> type, String field) throws NoSuchMethodException {
        String methodName = "add" + formatMethodName(field);

        Method[] methods = type.getMethods();
        int length = methods.length;

        Method primitiveMethod = null;
        for (int i = 0; i < length; ++i) {
            Method method = methods[i];
            if (isSetterForBuilder(method, methodName))
                return method;
            if(isSetterForPrimitive(method, methodName))
                primitiveMethod = method;
        }
        if(primitiveMethod != null)
            return primitiveMethod;

        throw new NoSuchMethodException(methodName);
    }

    private static String formatMethodName(String str) {
        return str.contains("_") ? formatSnakeCaseMethodName(str) : str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String formatSnakeCaseMethodName(String str) {
        StringBuilder methodName = new StringBuilder();

        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '_' && i + 1 < str.length()) {
                ++i;
                char c = str.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    methodName.append((char) (c ^ 32));
                } else {
                    methodName.append(c);
                }
            } else {
                methodName.append(str.charAt(i));
            }
        }

        return methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
    }

    private static boolean isSetterForPrimitive(Method method, String methodName) {
        if (!method.getName().equalsIgnoreCase(methodName)) {
            return false;
        } else {
            Class<?>[] parameterTypes = method.getParameterTypes();
            return parameterTypes.length == 1 && !Message.Builder.class.isAssignableFrom(parameterTypes[0]);
        }
    }

    private static boolean isSetterForBuilder(Method method, String methodName) {
        if (!method.getName().equalsIgnoreCase(methodName)) {
            return false;
        } else {
            Class<?>[] parameterTypes = method.getParameterTypes();
            return parameterTypes.length == 1 && Message.Builder.class.isAssignableFrom(parameterTypes[0]);
        }
    }
}
