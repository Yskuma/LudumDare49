package com.livelyspark.ludumdare49.desktop;

import com.livelyspark.ludumdare49.managers.FloatFormatter;

public class DesktopFloatFormatter implements FloatFormatter {

    @Override
    public String getFormattedString(float value) {
        return String.format("%.1f", value);
    }
}
