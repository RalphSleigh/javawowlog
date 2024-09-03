package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;
import com.example.parsers.AbstractParser;
import com.example.parsers.EncounterStartParser;
import com.example.parsers.SpellDamageParser;
import com.example.parsers.UnknownParser;
import com.example.parsers.EncounterEndParser;

public class LogParser {
    LogFile logFile;
    HashMap<String, Integer> linesParsed = new HashMap<String, Integer>();

    static Map<String, AbstractParser> parsers = Map.of(
            "ENCOUNTER_START", new EncounterStartParser(),
            "ENCOUNTER_END", new EncounterEndParser(),
            "SPELL_DAMAGE", new SpellDamageParser());

    static UnknownParser unknownParser = new UnknownParser();

    public LogParser(LogFile logFile, String fileName) {
        try {
            this.logFile = logFile;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            var i = 0;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                parseLine(line);
                i++;
                if (i % 100000 == 0) {
                    System.out.println(STR."Parsed \{i} lines");
                    System.out.println(linesParsed);
                }
            }
            reader.close();
            System.out.println(STR."Parsed \{i} lines");
            System.out.println(linesParsed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void parseLine(String line) {
        var fields = parseFields(line);
        var lineType = fields.getLineType();
        linesParsed.merge(lineType, 1, Integer::sum);
        parsers.getOrDefault(lineType, unknownParser).parseEvent(logFile, fields);
    }

    LogEvent parseFields(String line) {
        var fields = new ArrayList<String>();
        var inQuotes = false;
        for (var i = 0; i < line.length(); i++) {
            var c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(line.substring(0, i));
                line = line.substring(i + 1);
                i = -1;
            }
        }
        return new LogEvent(fields);
    }
}
