package com.tugalsan.api.log.client;

import com.google.web.bindery.event.shared.UmbrellaException;
import com.tugalsan.api.compiler.client.*;
import com.tugalsan.api.string.client.*;
import java.util.*;
import java.util.stream.*;

public class TGC_Log implements TGS_LogInterface {

    public static TGC_Log of(Class clazz) {
        return new TGC_Log(clazz);
    }

    public static TGC_Log of(boolean infoEnable, Class clazz) {
        return new TGC_Log(infoEnable, clazz);
    }

    public TGC_Log(Class clazz) {
        this(false, clazz);
    }

    public TGC_Log(boolean infoEnable, Class clazz) {
        this(infoEnable, TGS_Log.isFullNamed(clazz) ? clazz.getName() : clazz.getSimpleName());
    }

    private TGC_Log(boolean infoEnable, CharSequence className) {
        this.className = className.toString();
        this.infoEnable = infoEnable;
    }
    final public String className;
    public static TGS_LogInterface debugGUI = null;

    public boolean infoEnable = false;
    public boolean showDebugGUINull = false;

    @Override
    public void cl(CharSequence funcName, CharSequence text, CharSequence url) {
        debug(TGS_Log.TYPE_LNK(), className, funcName, text, url);
        if (debugGUI != null) {
            debugGUI.cl(funcName, text, url);
        } else {
            if (showDebugGUINull) {
                debug(TGS_Log.TYPE_ERR(), "debugGUI == null");
            }
        }
    }

    @Override
    public void ci(CharSequence funcName, TGS_Compiler<Object> compiler) {
        if (!infoEnable) {
            return;
        }
        ci(funcName, compiler.compile());
    }

    @Override
    public void ci(CharSequence funcName, Object... oa) {
        if (!infoEnable) {
            return;
        }
        debug(TGS_Log.TYPE_INF(), className, funcName, oa);
        if (debugGUI != null) {
            debugGUI.ci(funcName, oa);
        } else {
            if (showDebugGUINull) {
                debug(TGS_Log.TYPE_ERR(), "debugGUI == null");
            }
        }
    }

    @Override
    public void cr(CharSequence funcName, Object... oa) {
        debug(TGS_Log.TYPE_RES(), className, funcName, oa);
        if (debugGUI != null) {
            debugGUI.cr(funcName, oa);
        } else {
            if (showDebugGUINull) {
                debug(TGS_Log.TYPE_ERR(), "debugGUI == null");
            }
        }
    }

    @Override
    public void ct(CharSequence funcName, Throwable t) {
        t = unwrap(t);
        debug(TGS_Log.TYPE_THR(), className, funcName, t);
        if (debugGUI != null) {
            debugGUI.ct(funcName, t);
        } else {
            if (showDebugGUINull) {
                debug(TGS_Log.TYPE_ERR(), "debugGUI == null");
            }
        }
    }

    private static Throwable unwrap(Throwable e) {
        if (e == null) {
            return null;
        }
        if (e instanceof UmbrellaException) {
            var ue = (UmbrellaException) e;
            if (ue.getCauses().size() == 1) {
                return unwrap(ue.getCauses().iterator().next());
            }
        }
        return e;
    }

    @Override
    public void ce(CharSequence funcName, Object... oa) {
        debug(TGS_Log.TYPE_ERR(), className, funcName, oa);
        if (debugGUI != null) {
            debugGUI.ce(funcName, oa);
        } else {
            if (showDebugGUINull) {
                debug(TGS_Log.TYPE_ERR(), "debugGUI == null");
            }
        }
    }

    private static void debug(int type, Object... oa) {
        if (oa == null || oa.length == 0) {
            return;
        }
        var sjMain = new StringJoiner("}, {", "{", "}");
        Arrays.stream(oa).forEachOrdered(o -> {
            String str;
            if (o == null) {
                str = String.valueOf(o);
            } else if (o instanceof Throwable) {
                str = TGS_StringUtils.toString((Throwable) o);
            } else if (o instanceof Stream) {
                var sjList = new StringJoiner("], [", "[", "]");
                ((Stream) o).forEachOrdered(oi -> sjList.add(String.valueOf(oi)));
                str = sjList.toString();
            } else if (o instanceof List) {
                var sjList = new StringJoiner("], [", "[", "]");
                ((List) o).stream().forEachOrdered(oi -> sjList.add(String.valueOf(oi)));
                str = sjList.toString();
            } else if (o instanceof Object[]) {
                var sjList = new StringJoiner("], [", "[", "]");
                Arrays.stream((Object[]) o).forEachOrdered(oi -> sjList.add(String.valueOf(oi)));
                str = sjList.toString();
            } else {
                str = String.valueOf(o);
            }
            sjMain.add(str);
        });
        if (Objects.equals(type, TGS_Log.TYPE_LNK())) {
            TGC_LogUtils.link(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_INF())) {
            TGC_LogUtils.info(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_RES())) {
            TGC_LogUtils.result(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_THR())) {
            TGC_LogUtils.error(sjMain.toString());
            return;
        }
        if (Objects.equals(type, TGS_Log.TYPE_ERR())) {
            TGC_LogUtils.error(sjMain.toString());
            return;
        }
        TGC_LogUtils.plain(sjMain.toString());
    }
}
