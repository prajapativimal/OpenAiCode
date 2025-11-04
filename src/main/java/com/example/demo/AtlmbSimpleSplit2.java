package com.example.demo;

import org.json.JSONObject;
import java.util.*;

public class AtlmbSimpleSplit2 {

    private static final LinkedHashMap<String, String> ADDR_TO_KEY = new LinkedHashMap<>();
    static {
        ADDR_TO_KEY.put("0000", "Charging mode");
        ADDR_TO_KEY.put("0001", "PV voltage");
        ADDR_TO_KEY.put("0002", "Battery Voltage");
        ADDR_TO_KEY.put("0003", "Charging current");
        ADDR_TO_KEY.put("0004", "Output Voltage");
        ADDR_TO_KEY.put("0005", "Load voltage");
        ADDR_TO_KEY.put("0006", "Load current");
        ADDR_TO_KEY.put("0007", "Charging power");
        ADDR_TO_KEY.put("0008", "Load power");
        ADDR_TO_KEY.put("0009", "Battery temperature");
        ADDR_TO_KEY.put("000a", "Internal temperature");
        ADDR_TO_KEY.put("000b", "Battery level");
        ADDR_TO_KEY.put("000c", "CO2 emission reduction");
        ADDR_TO_KEY.put("000e", "Fault code");
        ADDR_TO_KEY.put("000f", "System prompt");
        ADDR_TO_KEY.put("0101", "Battery type");
        ADDR_TO_KEY.put("0102", "Battery system voltage");
        ADDR_TO_KEY.put("0103", "CV voltage");
        ADDR_TO_KEY.put("0104", "CF voltage");
        ADDR_TO_KEY.put("0105", "Maximum charging current");
        ADDR_TO_KEY.put("0106", "Maximum load current");
        ADDR_TO_KEY.put("0107", "Equalizing charge voltage");
        ADDR_TO_KEY.put("0108", "Average charging time");
        ADDR_TO_KEY.put("0109", "Load rate");
        ADDR_TO_KEY.put("010a", "Number of batteries in series");
        ADDR_TO_KEY.put("0201", "Running time");
        ADDR_TO_KEY.put("0203", "Device address");
        ADDR_TO_KEY.put("0204", "Running days");
        ADDR_TO_KEY.put("0205", "Number of cycles");
        ADDR_TO_KEY.put("0206", "Daily power generation");
        ADDR_TO_KEY.put("0208", "Daily power generation duration");
        ADDR_TO_KEY.put("020a", "Monthly power generation");
        ADDR_TO_KEY.put("020c", "Monthly power generation duration");
        ADDR_TO_KEY.put("020e", "Total power generation");
        ADDR_TO_KEY.put("0210", "Load daily power consumption");
        ADDR_TO_KEY.put("0212", "Monthly load consumption");
        ADDR_TO_KEY.put("0214", "Total load consumption");
        ADDR_TO_KEY.put("0216", "Backlight time");
        ADDR_TO_KEY.put("0217", "Key switch enable");
        ADDR_TO_KEY.put("0301", "PvVoltRatio");
        ADDR_TO_KEY.put("0302", "PvVoltOffset");
        ADDR_TO_KEY.put("0303", "BatVoltRatio");
        ADDR_TO_KEY.put("0304", "BatVoltOffset");
        ADDR_TO_KEY.put("0305", "ChgCurrRatio");
        ADDR_TO_KEY.put("0306", "ChgCurrOffset");
        ADDR_TO_KEY.put("0307", "LoadCurrRatio");
        ADDR_TO_KEY.put("0308", "LoadCurrOffset");
        ADDR_TO_KEY.put("0309", "LoadVoltRatio");
        ADDR_TO_KEY.put("030a", "LoadVoltOffset");
        ADDR_TO_KEY.put("030b", "OutVoltRatio");
        ADDR_TO_KEY.put("030c", "OutVoltOffset");
        ADDR_TO_KEY.put("0401", "Load mode 1 5100~5118");
        ADDR_TO_KEY.put("0402", "Load mode 2 5201~5215");
        ADDR_TO_KEY.put("0403", "Open load PV voltage");
        ADDR_TO_KEY.put("0404", "Off load PV voltage");
        ADDR_TO_KEY.put("0405", "Light control load on time delay");
        ADDR_TO_KEY.put("0406", "Light control load off time delay");
        ADDR_TO_KEY.put("0407", "Opening hours in the evening");
        ADDR_TO_KEY.put("0409", "Closing time in the evening");
        ADDR_TO_KEY.put("040b", "Morning opening time");
        ADDR_TO_KEY.put("040d", "Closing time in the morning");
        ADDR_TO_KEY.put("040f", "Load status");
        ADDR_TO_KEY.put("0410", "Period 2 enable");
        ADDR_TO_KEY.put("0601", "System command");
        ADDR_TO_KEY.put("0701", "Load overvoltage Protection point");
        ADDR_TO_KEY.put("0702", "Load low voltage protection point");
        ADDR_TO_KEY.put("0703", "Battery overvoltage protection point");
        ADDR_TO_KEY.put("0704", "Battery overvoltage recovery point");
        ADDR_TO_KEY.put("0705", "Low voltage protection point of battery");
        ADDR_TO_KEY.put("0706", "Low voltage recovery point of battery");
        ADDR_TO_KEY.put("0809", "Version number");
        ADDR_TO_KEY.put("080b", "Model");
        ADDR_TO_KEY.put("0904", "Temperature unit");
        ADDR_TO_KEY.put("0907", "Charging voltage of lithium battery");
        ADDR_TO_KEY.put("0908", "Nominal voltage of lithium battery");
        ADDR_TO_KEY.put("0909", "Over discharge voltage of lithium battery");
        ADDR_TO_KEY.put("090a", "Overcharge voltage of lithium battery");
        ADDR_TO_KEY.put("090b", "Low voltage recovery voltage of lithium battery");
        ADDR_TO_KEY.put("090c", "Overcharge recovery voltage of lithium battery");
        ADDR_TO_KEY.put("0a01", "Power generation data in the last 12 months");
        ADDR_TO_KEY.put("0a19", "Electricity consumption data in the last 12 months");
        ADDR_TO_KEY.put("0a31", "Power generation data in recent 31 days");
        ADDR_TO_KEY.put("0a6f", "Power consumption data in recent 31 days");
    }

    private static String nextAddr(String addr) {
        int v = Integer.parseInt(addr, 16);
        v++;
        return String.format("%04x", v);
    }

    public static LinkedHashMap<String, String> parseParameters(String raw) {
        LinkedHashMap<String, String> out = new LinkedHashMap<>();
        for (Map.Entry<String, String> e : ADDR_TO_KEY.entrySet()) out.put(e.getValue(), "NO_RESP");
        if (raw == null || raw.isEmpty()) return out;

        String imei = raw.substring(5, 20);
        System.out.println("IMEI: " + imei);

        String[] frames = raw.split("@");
        for (String frame : frames) {
            frame = frame.trim();
            if (frame.isEmpty()) continue;
            String[] parts = frame.split(",", 5);
            if (parts.length < 5) continue;
            if (!"01".equals(parts[0].trim()) || !"03".equals(parts[1].trim())) continue;

            String baseAddr = parts[2].trim().toLowerCase(Locale.ROOT);
            String valRaw = parts[4].trim();
            if (valRaw.equalsIgnoreCase("NO_RESP") || valRaw.toUpperCase(Locale.ROOT).startsWith("CHECKSUM_ISSUE"))
                continue;

            String value = valRaw.replaceAll("\\s+|=.+$", "");
            String[] words = value.split("(?<=\\G.{4})");

            String addr = baseAddr;
            for (String w : words) {
                if (w.isEmpty()) continue;
                String key = ADDR_TO_KEY.get(addr);
                if (key != null) out.put(key, w.toLowerCase(Locale.ROOT));
                addr = nextAddr(addr);
            }
        }
        return out;
    }

    public static LinkedHashMap<String, Object> applyHexToDecimalConversion(LinkedHashMap<String, String> outMap) {
        Set<String> hexToDecimalParams = new HashSet<>(Arrays.asList(
            "PV voltage", "Battery Voltage", "Charging current", "Output Voltage", "Load voltage", "Load current",
            "Charging power", "Load power", "Battery temperature", "Internal temperature", "Battery level",
            "CO2 emission reduction", "Fault code", "System prompt", "CV voltage", "CF voltage",
            "Maximum charging current", "Maximum load current", "Equalizing charge voltage", "Average charging time",
            "Load rate", "Number of batteries in series", "Running time", "Device address", "Running days",
            "Number of cycles", "Daily power generation", "Daily power generation duration", "Monthly power generation",
            "Monthly power generation duration", "Total power generation", "Load daily power consumption",
            "Monthly load consumption", "Total load consumption", "Backlight time", "Key switch enable",
            "PvVoltRatio", "PvVoltOffset", "BatVoltRatio", "BatVoltOffset", "ChgCurrRatio", "ChgCurrOffset",
            "LoadCurrRatio", "LoadCurrOffset", "LoadVoltRatio", "LoadVoltOffset", "OutVoltRatio", "OutVoltOffset",
            "Load mode 1: 5100~5118", "Load mode 2: 5201~5215", "Open load PV voltage", "Off load PV voltage",
            "Light control load on time delay", "Light control load off time delay", "Opening hours in the evening",
            "Closing time in the evening", "Morning opening time", "Closing time in the morning", "Time parameter",
            "Load overvoltage Protection point", "Load low voltage protection point",
            "Battery overvoltage protection point", "Battery overvoltage recovery point",
            "Low voltage protection point of battery", "Low voltage recovery point of battery",
            "Charging voltage of lithium battery", "Nominal voltage of lithium battery",
            "Over discharge voltage of lithium battery", "Overcharge voltage of lithium battery",
            "Low voltage recovery voltage of lithium battery", "Overcharge recovery voltage of lithium battery",
            "Power generation data in the last 12 months", "Electricity consumption data in the last 12 months",
            "Power generation data in recent 31 days", "Power consumption data in recent 31 days"
        ));
        System.out.println("size of DecimalParams set: " + hexToDecimalParams.size());

        LinkedHashMap<String, Object> converted = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : outMap.entrySet()) {
            String param = entry.getKey();
            String value = entry.getValue();
            if (!hexToDecimalParams.contains(param)) continue;

            if (value == null || value.equalsIgnoreCase("NO_RESP")) {
                converted.put(param, "NO_RESP");
                continue;
            }
            try {
                int decimal = Integer.parseUnsignedInt(value, 16);
                converted.put(param, decimal);
            } catch (Exception e) {
                converted.put(param, value);
            }
        }

        String v = outMap.get("Charging mode");
        System.out.println("v values "+v);
        if (v == null || "NO_RESP".equalsIgnoreCase(v)) {
            converted.put("Charging mode", "NO_RESP");
        } else {
            String first4 = v.length() >= 4 ? v.substring(0, 4) : v;
            try {
                converted.put("Charging mode", Integer.parseUnsignedInt(first4, 16));
            } catch (Exception e) {
                converted.put("Charging mode", first4); 
            }
        }
        
        
        return converted;
    }

    public static void main(String[] args) {
        String raw =
            "ATLMB862360075265996,182424,311299,MPPT,@01,03,0000,08,0001000001e40000@01,03,0004,08,0000000000000000@01,03,0008,00,NO_RESP";

//    	 String raw =
//    	    "ATLMB862360075265996,031208,311025,MPPT,@01,03,0000,08,0001000000000000@01,03,0004,08,0000000000000000@01,03,0008,08,0000000000000000@01,03,000c,00,xxxxxxxx@01,03,0101,00,NO_RESP@01,03,0105,08,0000000000000000@01,03,0109,00,xxxxxxxx@01,03,0201,00,NO_RESP@01,03,0205,02,0000@01,03,0206,02,0000@0ATLMB- ATLMB862360075265996,031208,311025,MPPT,@01,03,020a,08,0000000000000000@01,03,020e,08,0000000000000000@01,03,0212,08,0000000000000000@01,03,0216,04,00000000@01,03,0301,04,001e0000@01,03,0305,08,0000000000000000@01,03,0309,08,0000000000000000@01,03,0401,08,0000000100000006@01,03,0405,00,xxxxxxxx@01,03,0409,00,NO_RESP@1ATLMBa ATLMB862360075265996,031208,311025,MPPT,@01,03,040d,08,610003e80000f4c1@01,03,0601,00,NO_RESP@01,03,0602,08,90b870bf83b8a126@01,03,0606,08,aabb9bca40b89142@01,03,0701,08,156595bd17e4c7fa@01,03,0705,04,28ee4be6@01,03,0801,00,NO_RESP@01,03,0805,08,42ea8ea1a380a554@01,03,0809,00,NO_RESP@01,03,080b,00,NO_RESP@2ATLMB{ ATLMB862360075265996,031208,311025,MPPT,@01,03,080f,08,765b7a12f7837aae@01,03,0904,00,NO_RESP@01,03,0907,08,0208020d01a4021c@01,03,090b,04,12c15727@01,03,0a01,00,NO_RESP@01,03,0a05,08,0000000000000000@01,03,0a09,00,NO_RESP@01,03,0a0d,08,0000000000000000@01,03,0a11,00,NO_RESP@01,03,0a15,08,0000000000000000@3ATLMB/ ATLMB862360075265996,031208,311025,MPPT,@01,03,0a19,00,NO_RESP@01,03,0a1d,08,0000000000000000@01,03,0a21,00,NO_RESP@01,03,0a25,08,0000000000000000@01,03,0a29,00,NO_RESP@01,03,0a2d,08,0000000000000000@01,03,0a31,00,NO_RESP@01,03,0a35,08,0000000000000000@01,03,0a39,00,NO_RESP@01,03,0a3d,08,0000000000000000@4ATLMBe ATLMB862360075265996,031208,311025,MPPT,@01,03,0a41,00,NO_RESP@01,03,0a45,08,0000000000000000@01,03,0a49,00,NO_RESP@01,03,0a4d,08,0000000000000000@01,03,0a51,00,NO_RESP@01,03,0a55,08,0000000000000000@01,03,0a59,00,NO_RESP@01,03,0a5d,08,0000000000000000@01,03,0a61,00,NO_RESP@01,03,0a65,08,0000000000000000@5ATLMB= ATLMB862360075265996,031208,311025,MPPT,@01,03,0a69,00,NO_RESP@01,03,0a6d,04,00000000@01,03,0a6f,00,NO_RESP@01,03,0a73,08,0000000000000000@01,03,0a77,00,NO_RESP@01,03,0a7b,08,0000000000000000@01,03,0a7f,00,NO_RESP@01,03,0a83,08,0000000000000000@01,03,0a87,00,NO_RESP@01,03,0a8b,08,0000000000000000@6ATLMBe ATLMB862360075265996,031208,311025,MPPT,@01,03,0a8f,00,NO_RESP@01,03,0a93,08,0000000000000000@01,03,0a97,00,NO_RESP@01,03,0a9b,08,0000000000000000@01,03,0a9f,08,0000000000000000@01,03,0aa3,00,NO_RESP@01,03,0aa7,08,0000000000000000@01,03,0aab,00,NO_RESP@01,03,0aac,02,0000@7ATLMB3 ATLMB862360075265996,031308,311025,MPPT,@01,03,0000,08,0000000000000000@01,03,0004,00,xxxxxxxx@01,03,0008,00,NO_RESP@01,03,000c,08,0000000000000000@01,03,0101,08,0004000000000000@01,03,0105,08,0000000000000000@01,03,0109,00,xxxxxxxx@01,03,0201,00,NO_RESP@01,03,0205,02,0000@01,03,0206,02,0000@0ATLMB( ATLMB862360075265996,031308,311025,MPPT,@01,03,020a,08,0000000000000000@01,03,020e,08,0000000000000000@01,03,0212,0a,E:8,C:0A:1f7f7bf512c1572700@01,03,0216,04,00000000@01,03,0301,00,xxxxxxxx@01,03,0305,08,0000000000000000@01,03,0309,00,NO_RESP@01,03,0401,08,0000000100000006@01,03,0405,08,000000000000f1a9@01,03,0409,00,NO_RESP@1ATLMB# ATLMB862360075265996,031308,311025,MPPT,@01,03,040d,00,NO_RESP@01,03,0601,02,84ac@01,03,0602,08,90b870bf83b8a126@01,03,0606,08,aabb9bca40b89142@01,03,0701,08,156595bd17e4c7fa@01,03,0705,00,xxxxxxxx@01,03,0801,04,28ee4be6@01,03,0805,08,42ea8ea1a380a554@01,03,0809,00,NO_RESP@01,03,080b,00,NO_RESP@2ATLMBm ATLMB862360075265996,031308,311025,MPPT,@01,03,080f,00,NO_RESP@01,03,0904,02,1c52@01,03,0907,08,0208020d01a4021c@01,03,090b,04,12c15727@01,03,0a01,00,CHECKSUM_ISSUE@01,03,0a05,00,NO_RESP@01,03,0a09,08,0000000000000000@01,03,0a0d,00,NO_RESP@01,03,0a11,08,0000000000000000@01,03,0a15,00,NO_RESP@3ATLMBy ATLMB862360075265996,031308,311025,MPPT,@01,03,0a19,08,0000000000000000@01,03,0a1d,00,NO_RESP@01,03,0a21,08,0000000000000000@01,03,0a25,00,NO_RESP@01,03,0a29,08,0000000000000000@01,03,0a2d,00,NO_RESP@01,03,0a31,08,0000000000000000@01,03,0a35,08,0000000000000000@01,03,0a39,00,NO_RESP@01,03,0a3d,08,0000000000000000@4ATLMB& ATLMB862360075265996,031308,311025,MPPT,@01,03,0a41,00,NO_RESP@01,03,0a45,08,0000000000000000@01,03,0a49,00,NO_RESP@01,03,0a4d,08,0000000000000000@01,03,0a51,00,NO_RESP@01,03,0a55,08,0000000000000000@01,03,0a59,00,NO_RESP@01,03,0a5d,08,0000000000000000@01,03,0a61,00,NO_RESP@01,03,0a65,08,0000000000000000@5ATLMB< ATLMB862360075265996,031308,311025,MPPT,@01,03,0a69,00,NO_RESP@01,03,0a6d,04,00000000@01,03,0a6f,00,NO_RESP@01,03,0a73,08,0000000000000000@01,03,0a77,00,NO_RESP@01,03,0a7b,08,0000000000000000@01,03,0a7f,00,NO_RESP@01,03,0a83,08,0000000000000000@01,03,0a87,00,NO_RESP@01,03,0a8b,08,0000000000000000@6ATLMBd ATLMB862360075265996,031308,311025,MPPT,@01,03,0a8f,00,NO_RESP@01,03,0a93,08,0000000000000000@01,03,0a97,00,NO_RESP@01,03,0a9b,08,0000000000000000@01,03,0a9f,00,NO_RESP@01,03,0aa3,08,0000000000000000@01,03,0aa7,00,NO_RESP@01,03,0aab,02,0000@01,03,0aac,00,NO_RESP@7ATLMBp ATLMB862360075265996,031408,311025,MPPT,@01,03,0000,08,0000000000000000@01,03,0004,00,xxxxxxxx@01,03,0008,00,NO_RESP@01,03,000c,08,0000000000000000@01,03,0101,0a,E:8,C:0A:1f7f7bf512c1572700@01,03,0105,08,0000000000000000@01,03,0109,00,NO_RESP@01,03,0201,08,0000000000000000@01,03,0205,02,0000@01,03,0206,00,NO_RESP@0ATLMB= ATLMB862360075265996,031408,311025,MPPT,@01,03,020a,08,0000000000000000@01,03,020e,00,xxxxxxxx@01,03,0212,00,NO_RESP@01,03,0216,04,00000000@01,03,0301,04,001e0000@01,03,0305,08,0000000000000000@01,03,0309,08,0000000000000000@01,03,0401,00,xxxxxxxx@01,03,0405,00,NO_RESP@01,03,0409,08,610000060000f1a9@1ATLMB$ ATLMB862360075265996,031408,311025,MPPT,@01,03,040d,00,xxxxxxxx@01,03,0601,00,NO_RESP@01,03,0602,08,90b870bf83b8a126@01,03,0606,08,aabb9bca40b89142@01,03,0701,08,156595bd17e4c7fa@01,03,0705,00,xxxxxxxx@01,03,0801,00,NO_RESP@01,03,0805,08,42ea8ea1a380a554@01,03,0809,00,NO_RESP@01,03,080b,00,NO_RESP@2ATLMB' ATLMB862360075265996,031408,311025,MPPT,@01,03,080f,08,765b7a12f7837aae@01,03,0904,00,NO_RESP@01,03,0907,08,0208020d01a4021c@01,03,090b,04,12c15727@01,03,0a01,04,01b3020d@01,03,0a05,08,0000000000000000@01,03,0a09,00,NO_RESP@01,03,0a0d,08,0000000000000000@01,03,0a11,00,NO_RESP@01,03,0a15,08,0000000000000000@3ATLMBa ATLMB862360075265996,031408,311025,MPPT,@01,03,0a19,00,NO_RESP@01,03,0a1d,08,0000000000000000@01,03,0a21,00,NO_RESP@01,03,0a25,08,0000000000000000@01,03,0a29,00,NO_RESP@01,03,0a2d,08,0000000000000000@01,03,0a31,00,NO_RESP@01,03,0a35,08,0000000000000000@01,03,0a39,00,NO_RESP@01,03,0a3d,08,0000000000000000@4ATLMBc ATLMB862360075265996,031408,311025,MPPT,@01,03,0a41,00,NO_RESP@01,03,0a45,08,0000000000000000@01,03,0a49,00,NO_RESP@01,03,0a4d,08,0000000000000000@01,03,0a51,00,NO_RESP@01,03,0a55,08,0000000000000000@01,03,0a59,00,NO_RESP@01,03,0a5d,08,0000000000000000@01,03,0a61,00,NO_RESP@01,03,0a65,08,0000000000000000@5ATLMB; ATLMB862360075265996,031408,311025,MPPT,@01,03,0a69,00,NO_RESP@01,03,0a6d,04,00000000@01,03,0a6f,00,NO_RESP@01,03,0a73,08,0000000000000000@01,03,0a77,00,NO_RESP@01,03,0a7b,08,0000000000000000@01,03,0a7f,00,NO_RESP@01,03,0a83,08,0000000000000000@01,03,0a87,00,NO_RESP@01,03,0a8b,08,0000000000000000@6ATLMBc ATLMB862360075265996,031408,311025,MPPT,@01,03,0a8f,00,NO_RESP@01,03,0a93,08,0000000000000000@01,03,0a97,00,NO_RESP@01,03,0a9b,08,0000000000000000@01,03,0a9f,00,NO_RESP@01,03,0aa3,08,0000000000000000@01,03,0aa7,00,NO_RESP@01,03,0aab,02,0000@01,03,0aac,00,NO_RESP@7ATLMBw";

        LinkedHashMap<String, String> parsed = parseParameters(raw);
        System.out.println("parsed Map Size: " + parsed.size());
        LinkedHashMap<String, Object> converted = applyHexToDecimalConversion(parsed);

        JSONObject json = new JSONObject(converted);
        System.out.println("===============================================");
        System.out.println("Convert json size: " + json.length());
        System.out.println("JSON Output: \n" + json.toString(4));
    }
}
