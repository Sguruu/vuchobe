package ru.vuchobe.util.loaders;import java.lang.reflect.Field;import java.lang.reflect.Method;import java.util.List;import androidx.annotation.NonNull;import androidx.annotation.Nullable;public interface Loader<ValueOne, Value> extends UpdateDataUI, CallbackResult<ValueOne, Value> {    Status getStatusValue();    Status getStatusValue(Params position);    void load(Params params);    void start();    void pause();    void stop();    @Nullable Loader getLongLoader();    default void save(        @Nullable Loader loader,        @Nullable Loader.Params params,        @Nullable ValueOne valueOne,        @Nullable List<Value> value    ){}    @NonNull    BuildParams buildParams();    interface Params extends Cloneable{        @Nullable Params getParent();        void setParent(Params parent);        @Nullable Params getChildren();        void setChildren(Params children);        Params clone();    }    interface BuildParams{        BuildParams setObject(Object object);        BuildParams setKeyMethod(Method keyMethod);        BuildParams setSetMethod(Method setMethod);        BuildParams setParent(Params parent);        Params build();    }    enum Status{        NOT_LOAD, IN_QUEUE, LOAD, DONE, OUTDATED;    }    interface ResultClonable extends Cloneable{        Object clone();        static <Clone> Clone clone(Clone object){            if(object instanceof ResultClonable){                return (Clone) ((ResultClonable)object).clone();            }            return object;        }    }}