package org.rmj.g3appdriver.lib.ProductInquiry;

import android.app.Application;

public class MCPriceInquiry {
    private static final String TAG = "MCPriceInquiry";

    private final Application instance;

    private String message;

    public MCPriceInquiry(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }


}
