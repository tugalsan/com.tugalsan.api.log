package com.tugalsan.api.log.server;

import com.sun.jna.Platform;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.*;

public class TS_LogUtils {

    @Deprecated //NOT TESTED
    public static boolean ENABLE_Cp850_FOR_WINDOWS = false;

    public static PrintWriter printWriter() {
        return TGS_UnSafe.call(() -> {
            if (pritWriter == null) {
                pritWriter = (ENABLE_Cp850_FOR_WINDOWS && Platform.isWindows())
                        ? new PrintWriter(new OutputStreamWriter(System.out, "Cp850"))
                        : new PrintWriter(System.out);
            }
            return pritWriter;
        }, e -> {
            e.printStackTrace();
            return null;
        });
    }
    private static volatile PrintWriter pritWriter = null;

    public static void setColoredConsole(boolean enableColoredCMD) {
        TS_LogUtils.enableColoredCMD = enableColoredCMD;
        if (enableColoredCMD) {
            AnsiConsole.systemInstall();
            reset();
            result(TS_LogUtils.class.getSimpleName() + ".initialize->enableColoredCMD:true");
        } else {
            result(TS_LogUtils.class.getSimpleName() + "initialize->enableColoredCMD:false");
        }
    }
    private static boolean enableColoredCMD = false;

    public static void destroy() {
        if (enableColoredCMD) {
            reset();
            AnsiConsole.systemUninstall();
        }
    }

    private static void reset() {
        if (enableColoredCMD) {
//            printWriter().print(Ansi.ansi().fg(Color.WHITE).boldOff());
            printWriter().print(Ansi.ansi().reset());
        }
    }

    public static void result(CharSequence text) {
        if (enableColoredCMD) {
            printWriter().println(Ansi.ansi().fg(Color.GREEN).bold().a(text));
            reset();
        } else {
            printWriter().println(text);
        }
    }

    public static void error(CharSequence text) {
        if (enableColoredCMD) {
            printWriter().println(Ansi.ansi().fg(Color.RED).bold().a(text));
            reset();
        } else {
            printWriter().println(text);
        }
    }

    public static void info(CharSequence text) {
        if (enableColoredCMD) {
            printWriter().println(Ansi.ansi().fg(Color.YELLOW).bold().a(text));
            reset();
        } else {
            printWriter().println(text);
        }
    }

    public static void link(CharSequence text) {
        if (enableColoredCMD) {
            printWriter().println(Ansi.ansi().fg(Color.BLUE).bold().a(text));
            reset();
        } else {
            printWriter().println(text);
        }
    }

    public static void plain(CharSequence text) {
        if (enableColoredCMD) {
            reset();
            printWriter().println(text);
        } else {
            printWriter().println(text);
        }
    }

    public static void hidden(CharSequence text) {
        if (enableColoredCMD) {
            printWriter().println(Ansi.ansi().fg(Color.BLACK).bold().a(text));
            reset();
        } else {
            printWriter().println(text);
        }
    }

    public static void clear() {
        if (enableColoredCMD) {
            printWriter().println(Ansi.ansi().eraseScreen());
        }
    }
}
