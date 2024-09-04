package com.example.logitems;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LogEvent {
    private ArrayList<String> fields;

    public LogEvent(ArrayList<String> fields) {
        this.fields = fields;
    }

    public String getLineType() {
        try {
            var firstField = fields.get(0);
            if (firstField == "COMBAT_LOG_VERSION") {
                return "COMBAT_LOG_VERSION";
            }
            var lineType = firstField.split("  ", 0)[1];
            return lineType;
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    public String getString(int index) {
        var field = fields.get(index);
        return field.replaceAll("^\"|\"$", "");
    }

    public int getInt(int index) {
        return Integer.parseInt(getString(index));
    }

    public int getHex(int index) {
        return Integer.decode(getString(index));
    }

    public LocalDateTime getTimestamp() {
        var firstField = fields.get(0);
        if (firstField == "COMBAT_LOG_VERSION") {
            return null;
        }
        var timestampRegex = Pattern
                .compile("^([0-9]{1,2})\\/([0-9]{1,2})\\/([0-9]{4}) ([0-9]{2}):([0-9]{2}):([0-9]{2})\\.([0-9]{3})1$");
        var dateTimeString = firstField.split("  ", 0)[0];
        var matcher = timestampRegex.matcher(dateTimeString);
        if(matcher.matches() == false) {
            return null;
        }
        return LocalDateTime.of(Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(4)),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6)),
                Integer.parseInt(matcher.group(7)) * 1000000);
    }
}
