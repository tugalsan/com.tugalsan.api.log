package com.tugalsan.api.log.server;

import com.tugalsan.api.log.client.*;
import com.tugalsan.api.string.client.*;
import java.util.*;
import java.util.stream.*;
import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTU;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTU_OutTyped;
import java.util.function.Supplier;

public class TS_Log implements TGS_LogInterface {

    public String className() {
        return classNameSupplier.get();
    }
    final private Supplier<String> classNameSupplier;
    public boolean infoEnable = false;

    private TS_Log(boolean infoEnable, Class clazz) {
        this.classNameSupplier = StableValue.supplier(() -> TGS_Log.isFullNamed(clazz) ? clazz.getName() : clazz.getSimpleName());
        this.infoEnable = infoEnable;
    }

    private TS_Log(boolean infoEnable, CharSequence className) {
        this.classNameSupplier = StableValue.supplier(() -> className.toString());
        this.infoEnable = infoEnable;
    }

    public static TS_Log of(Class clazz) {
        return of(false, clazz);
    }

    public static TS_Log of(boolean infoEnable, Class clazz) {
        return new TS_Log(infoEnable, clazz);
    }

    public static TS_Log of(String clazzName) {
        return of(false, clazzName);
    }

    public static TS_Log of(boolean infoEnable, String clazzName) {
        return new TS_Log(infoEnable, clazzName);
    }

    @Override
    public void cl(CharSequence funcName, CharSequence text, CharSequence url) {
        debug(TGS_Log.TYPE_LNK(), className(), funcName, text, url);
    }

    @Override
    public void ci(CharSequence funcName, TGS_FuncMTU_OutTyped<Object> callable) {
        if (!infoEnable) {
            return;
        }
        ci(funcName, callable.call());
    }

    @Override
    public void ci(CharSequence funcName, Object... oa) {
        if (!infoEnable) {
            return;
        }
        debug(TGS_Log.TYPE_INF(), className(), funcName, oa);
    }

    @Override
    public void cr(CharSequence funcName, Object... oa) {
        debug(TGS_Log.TYPE_RES(), className(), funcName, oa);
    }

    @Override
    public void ct(CharSequence funcName, Throwable t) {
        TGS_FuncMTCUtils.run(() -> debug(TGS_Log.TYPE_THR(), className(), funcName, t), e -> TGS_FuncMTU.empty.run());
    }

    @Override
    public void ce(CharSequence funcName, Object... oa) {
        debug(TGS_Log.TYPE_ERR(), className(), funcName, oa);
    }

    @Deprecated //DONT FORGET TO ADD d.className
    public static void debug(int type, Object... oa) {
        if (oa == null || oa.length == 0) {
            return;
        }
        var sjMain = new StringJoiner("}, {", "{", "}");
        Arrays.stream(oa).forEachOrdered(o -> {
            String str;
            if (o == null) {
                str = String.valueOf(o);
            } else if (o instanceof Throwable thr) {
                str = TGS_StringUtils.cmn().toString(thr);
            } else if (o instanceof Stream stream) {
                var sjList = new StringJoiner("], [", "[", "]");
                stream.forEachOrdered(oi -> sjList.add(String.valueOf(oi)));
                str = sjList.toString();
            } else if (o instanceof List lst) {
                var sjList = new StringJoiner("], [", "[", "]");
                lst.stream().forEachOrdered(oi -> sjList.add(String.valueOf(oi)));
                str = sjList.toString();
            } else if (o instanceof Object[] arr) {
                var sjList = new StringJoiner("], [", "[", "]");
                Arrays.stream(arr).forEachOrdered(oi -> sjList.add(String.valueOf(oi)));
                str = sjList.toString();
            } else {
                str = String.valueOf(o);
            }
            sjMain.add(str);
        });
        if (Objects.equals(type, TGS_Log.TYPE_LNK())) {
            TS_LogUtils.link(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_INF())) {
            TS_LogUtils.info(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_RES())) {
            TS_LogUtils.result(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_THR())) {
            TS_LogUtils.error(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_ERR())) {
            TS_LogUtils.error(sjMain.toString());
            return;
        }
        TS_LogUtils.plain(sjMain.toString());
    }

    @Deprecated //TOO COMPLEX
    public Result_withLog createFuncBoolean(CharSequence funcName) {
        return Result_withLog.of(className() + "." + funcName, false, "init");
    }

    @Deprecated //TOO COMPLEX
    public static class Result_withLog {

        private Result_withLog(String classNameDotfuncName, boolean result, CharSequence log) {
            this.classNameDotfuncName = classNameDotfuncName;
            this.result = result;
            this.log = log;
        }

        public static Result_withLog of(String classNameDotfuncName, boolean result, CharSequence log) {
            return new Result_withLog(classNameDotfuncName, result, log);
        }

        public String classNameDotfuncName;//val0
        public boolean result;//val1
        public CharSequence log;//val2

        public Result_withLog mutate2Error(CharSequence errText) {
            log = errText;
            debug(TGS_Log.TYPE_ERR(), classNameDotfuncName, log);
            return this;
        }

        public Result_withLog mutate2True() {
            result = true;
            return this;
        }

    }
}
