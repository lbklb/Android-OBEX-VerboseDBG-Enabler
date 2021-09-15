package io.github.bestowl.android_obex_vdbg_enabler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class VdbgEnabler implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.bluetooth")) {
            return;
        }

        XposedBridge.log("Hooking com.android.bluetooth");

        XposedBridge.log("Finding class: javax.obex.ObexHelper");
        Class<?> cls;
        try {
            cls = XposedHelpers.findClass("javax.obex.ObexHelper", lpparam.classLoader);
        } catch (XposedHelpers.ClassNotFoundError e) {
            XposedBridge.log("Could not find class: javax.obex.ObexHelper, abort!");
            XposedBridge.log(e);
            return;
        }

        XposedBridge.log("Found class: " + cls);
        XposedBridge.log("Modifying VDBG");
        XposedHelpers.setStaticBooleanField(cls, "VDBG", true);
    }
}
