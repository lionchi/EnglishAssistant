package com.gavrilov.commons;

import com.gavrilov.exeptions.IllegalOperationException;
import com.google.common.base.Strings;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class Guard {
    @SuppressWarnings("ConstantConditions")
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String text, String message) {
        if (Strings.isNullOrEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void that(boolean condition, String code) {
        that(condition, code, () -> code);
    }

    public static void fail(String code) {
        that(false, code, () -> code);
    }

    public static void that(boolean condition, String code, Supplier<String> messageSupplier) {
        if (!condition) {
            throw new IllegalOperationException(code, messageSupplier.get());
        }
    }
    public static void that(boolean condition, String code, String message) {
        that(condition, code, () -> message);
    }
}
