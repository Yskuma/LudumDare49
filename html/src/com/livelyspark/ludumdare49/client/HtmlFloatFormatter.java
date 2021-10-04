package com.livelyspark.ludumdare49.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.livelyspark.ludumdare49.managers.FloatFormatter;

public class HtmlFloatFormatter implements FloatFormatter {
    @Override
    public String getFormattedString(float value) {
        com.google.gwt.i18n.client.
        NumberFormat format = NumberFormat.getFormat("#.#");
        return format.format(value);
    }
}
