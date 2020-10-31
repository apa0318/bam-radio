//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.json;

public class JSONException extends Exception {
    private Throwable cause;

    public JSONException(String var1) {
        super(var1);
    }

    public JSONException(Throwable var1) {
        super(var1.getMessage());
        this.cause = var1;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
