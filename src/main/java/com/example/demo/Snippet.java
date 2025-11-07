package com.example.demo;

import java.util.*;
import java.util.stream.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Snippet {
	public static void main(String[] args) {
		String input = "ATLMB862360075265996,000000,010170,MPPT,@01,03,0000,08,0000000001dd0000@01,03,0004,00,NO_RESP@01,03,0008,08,0032000000000000@01,03,000c,00,0000055000400000@01,03,0101,08,000400000090008a@01,03,0105,00,NO_RESP@01,03,0109,00,CHECKSUM_ISSUE@01,03,0201,08,000200c800010000@01,03,0205,00,NO_RESP@01,03,0206,08,0000000000000000@,ATLMB862360075265996,000000,010170,MPPT,@01,03,080f,00,NO_RESP@01,03,0904,02,0000@01,03,0907,00,NO_RESP@01,03,090b,04,01a40230@01,03,0a01,00,NO_RESP@01,03,0a05,08,0000000000000000@01,03,0a09,08,0000000000000000@01,03,0a0d,00,NO_RESP@01,03,0a11,08,0000000000000000@01,03,0a15,00,NO_RESP@,ATLMB862360075265996,000000,010170,MPPT,@01,03,0a69,08,0000000000000000@01,03,0a6d,00,NO_RESP@01,03,0a6f,08,0000000000000000@01,03,0a73,00,NO_RESP@01,03,0a77,08,0000000000000000@01,03,0a7b,00,NO_RESP@01,03,0a7f,08,0000000000000000@01,03,0a83,00,NO_RESP@01,03,0a87,08,0000000000000000@01,03,0a8b,00,NO_RESP@,ATLMB862360075265996,000000,010170,MPPT,@01,03,020a,08,0000000000000000@01,03,020e,00,NO_RESP@01,03,0212,08,NO_RESP@01,03,0216,00,NO_RESP@01,03,0301,08,0000000000000000@01,03,0305,00,NO_RESP@01,03,0309,08,0000000000000000@01,03,0401,00,NO_RESP@01,03,0405,08,0000000000000000@01,03,0409,00,NO_RESP@,ATLMB862360075265996,000000,010170,MPPT,@01,03,0a19,00,NO_RESP@01,03,0a1d,08,0000000000000000@01,03,0a21,00,NO_RESP@01,03,0a25,08,0000000000000000@01,03,0a29,00,NO_RESP@01,03,0a2d,08,0000000000000000@01,03,0a31,00,NO_RESP@01,03,0a35,08,0000000000000000@01,03,0a39,00,NO_RESP@01,03,0a3d,08,0000000000000000@,ATLMB862360075265996,000000,010170,MPPT,@01,03,0a8f,00,NO_RESP@01,03,0a93,08,0000000000000000@01,03,0a97,00,NO_RESP@01,03,0a9b,08,0000000000000000@01,03,0a9f,00,NO_RESP@01,03,0aa3,08,0000000000000000@01,03,0aa7,00,NO_RESP@01,03,0aab,02,0000@01,03,0aac,00,NO_RESP@,ATLMB862360075265996,000000,010170,MPPT,@01,03,040d,00,NO_RESP@01,03,0601,00,NO_RESP@01,03,0602,00,NO_RESP@01,03,0606,00,NO_RESP@01,03,0701,08,0000000000960094@01,03,0705,00,NO_RESP@01,03,0801,08,3432353830303330@01,03,0805,00,NO_RESP@01,03,0809,04,2e313333@01,03,080b,00,NO_RESP@,";

		List<String> parts = Arrays.stream(input.split("@")).map(String::trim).map(s -> s.replaceFirst("^,+", "")) // remove
																													// leading
																													// commas
				.filter(s -> !s.isEmpty()).collect(Collectors.toList());
		JSONArray digitaljsonarr = new JSONArray();
		for (String p : parts) {
			System.out.println(p);
			String[] split = p.split(",");
			if (split.length > 3) {
				// This avoids ArrayIndexOutOfBounds
				if (split[0].equalsIgnoreCase("01") && split[1].equalsIgnoreCase("03")) {
					String regAddress = split[2];
					String regVal = split[split.length - 1];
					System.out.println("Register " + regAddress + " : " + regVal);

					/*
					 * if(regAddress.equalsIgnoreCase("0000")) { String[] regparts =
					 * splitByChunkSize(regVal,4); // for (String p1 : regparts) { for(int
					 * i=0;i<regparts.length;i++) { String p1=regparts[i]; System.out.println(p1);
					 * 
					 * if(i==0) { //Charging mode JSONObject analogObj1 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP")) analogObj1.put("1654480827",p1); else
					 * analogObj1.put("1654480827","0"); digitaljsonarr.put(analogObj1); } if(i==1)
					 * { //PV voltage JSONObject analogObj2 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj2.put("1654480882",Integer.parseInt(p1, 16)); else
					 * analogObj2.put("1654480882","0"); digitaljsonarr.put(analogObj2); } if(i==2)
					 * { //Battery Voltage JSONObject analogObj3 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj3.put("1654481198",Integer.parseInt(p1, 16)); else
					 * analogObj3.put("1654481198","0"); digitaljsonarr.put(analogObj3); } if(i==3)
					 * { //Charging Current JSONObject analogObj4 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj4.put("1654487055",Integer.parseInt(p1, 16)); else
					 * analogObj4.put("1654487055","0"); digitaljsonarr.put(analogObj4); }
					 * 
					 * } }
					 * 
					 * if(regAddress.equalsIgnoreCase("0004")) { String[] regparts =
					 * splitByChunkSize(regVal,4); for(int i=0;i<regparts.length;i++) { String
					 * p1=regparts[i]; System.out.println(p1);
					 * 
					 * if(i==0) { //Output Voltage JSONObject analogObj5 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj5.put("1654487838",Integer.parseInt(p1, 16)); else
					 * analogObj5.put("1654487055","0"); digitaljsonarr.put(analogObj5); } if(i==1)
					 * { //Load Voltage JSONObject analogObj6 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj6.put("1654488716",Integer.parseInt(p1, 16)); else
					 * analogObj6.put("1654488716","0"); digitaljsonarr.put(analogObj6); } if(i==2)
					 * { //Load Current JSONObject analogObj7 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj7.put("1654489121",Integer.parseInt(p1, 16)); else
					 * analogObj7.put("1654489121","0"); digitaljsonarr.put(analogObj7); } if(i==3)
					 * { //Charging Power JSONObject analogObj8 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj8.put("1654490616",Integer.parseInt(p1, 16)); else
					 * analogObj8.put("1654490616","0"); digitaljsonarr.put(analogObj8); } } }
					 * 
					 * if(regAddress.equalsIgnoreCase("0008")) { String[] regparts =
					 * splitByChunkSize(regVal,4); for(int i=0;i<regparts.length;i++) { String
					 * p1=regparts[i]; System.out.println(p1);
					 * 
					 * if(i==0) { //Load Power JSONObject analogObj5 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj5.put("1654490837",Integer.parseInt(p1, 16)); else
					 * analogObj5.put("1654490837","0"); digitaljsonarr.put(analogObj5); } if(i==1)
					 * { //Battery temp JSONObject analogObj6 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj6.put("1654491025",Integer.parseInt(p1, 16)); else
					 * analogObj6.put("1654491025","0"); digitaljsonarr.put(analogObj6); } if(i==2)
					 * { //Internal temp JSONObject analogObj7 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj7.put("1654491400",Integer.parseInt(p1, 16)); else
					 * analogObj7.put("1654491400","0"); digitaljsonarr.put(analogObj7); } if(i==3)
					 * { //Batttery Level JSONObject analogObj8 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj8.put("1654494927",Integer.parseInt(p1, 16)); else
					 * analogObj8.put("1654494927","0"); digitaljsonarr.put(analogObj8); } } }
					 * 
					 * if(regAddress.equalsIgnoreCase("000c")) { String[] regparts =
					 * splitByLengths(regVal, 8, 4, 4); for(int i=0;i<regparts.length;i++) { String
					 * p1=regparts[i]; System.out.println(p1);
					 * 
					 * if(i==0) { // CO2 esmission reduction JSONObject analogObj5 = new
					 * JSONObject(); if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj5.put("1654495145",Integer.parseInt(p1, 16)); else
					 * analogObj5.put("1654495145","0"); digitaljsonarr.put(analogObj5); } if(i==1)
					 * { //Fault Code JSONObject analogObj6 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj6.put("1654495854",Integer.parseInt(p1, 16)); else
					 * analogObj6.put("1654495854","0"); digitaljsonarr.put(analogObj6); } if(i==2)
					 * { //System prompt JSONObject analogObj7 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj7.put("1654496260",Integer.parseInt(p1, 16)); else
					 * analogObj7.put("1654496260","0"); digitaljsonarr.put(analogObj7); }
					 * 
					 * } }
					 */

					if (regAddress.equalsIgnoreCase("020a")) {
						String[] regparts = splitByLengths(regVal, 8, 8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Battery type
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654505431", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654505431", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Batttery system Voltage
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654505617", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654505617", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("020e")) {
						String[] regparts = splitByLengths(regVal, 8, 8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Total power generation
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654505806", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654505806", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Load daily power consumption
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654505991", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654505991", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("0212")) {
						String[] regparts = splitByLengths(regVal, 8, 8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Monthly load consumption
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654506278", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654506278", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Total load consumption
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654506659", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654506659", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("0216")) {
						String[] regparts = splitByLengths(regVal, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Backlight time
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654506856", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654506856", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Key switch enable
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654507090", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654507090", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("0301")) {
						String[] regparts = splitByLengths(regVal, 4, 4, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// PvVoltRatio
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654508843", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654508843", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// PvVoltOffset
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654509140", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654509140", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// BatVoltRatio
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654509387", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654509387", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 3) {
								// BatVoltOffset
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654509561", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654509561", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}

					if (regAddress.equalsIgnoreCase("0305")) {
						String[] regparts = splitByLengths(regVal, 4, 4, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// ChgCurrRatio
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654509762", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654509762", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// ChgCurrOffset
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654509925", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654509925", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// LoadCurrRatio
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654510161", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654510161", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 3) {
								// LoadCurrOffset
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654510359", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654510359", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}

					if (regAddress.equalsIgnoreCase("0309")) {
						String[] regparts = splitByLengths(regVal, 4, 4, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// LoadVoltRatio
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654510567", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654510567", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// LoadVoltOffset
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654510879", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654510879", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// OutVoltRatio
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654511043", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654511043", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 3) {
								// OutVoltOffset
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654511228", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654511228", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("0401")) {
						String[] regparts = splitByLengths(regVal, 4, 4, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Load mode 1: 5100 ~ 5118
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654514105", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654514105", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Load mode 2: 5201 ~ 5215
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654514372", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654514372", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// Open load PV voltage
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654514576", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654514576", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 3) {
								// Offload PV Voltage
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654514763", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654514763", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}

					if (regAddress.equalsIgnoreCase("0405")) {
						String[] regparts = splitByLengths(regVal, 4, 4, 8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Light control load on time delay
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654514931", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654514931", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Light control load off time delay
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654517125", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654517125", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// Opening hours in the morning
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654517347", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654517347", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("0409")) {
						String[] regparts = splitByLengths(regVal, 8, 8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// closing time in the evening
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654517586", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654517586", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Morning opening time
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654517936", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654517936", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("040d")) {
						String[] regparts = splitByLengths(regVal, 8, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Closing time in the morning
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654518195", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654518195", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Load status
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654518398", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654518398", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// Period 2 enable
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654518596", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654518596", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					if (regAddress.equalsIgnoreCase("0601")) {
						String[] regparts = splitByLengths(regVal,  4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// System command
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654536700", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654536700", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("0602")) {
						String[] regparts = splitByLengths(regVal,  8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Time Paramter
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654537391", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654537391", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("0606")) {
						String[] regparts = splitByLengths(regVal,  8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Time Paramter
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654537391", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654537391", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("0701")) {
						String[] regparts = splitByLengths(regVal, 4, 4, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// load overvoltage protection point
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654538360", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654538360", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Load low voltage protection point
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654538613", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654538613", "0");
								digitaljsonarr.put(analogObj6);
							}
							if (i == 2) {
								// Battery overvoltage protection point
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654538873", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654538873", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 3) {
								// Battery overvoltage recovery point
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654539070", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654539070", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("0705")) {
						String[] regparts = splitByLengths(regVal, 4, 4);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Low voltage protection point of battery
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654539248", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654539248", "0");
								digitaljsonarr.put(analogObj5);
							}
							if (i == 1) {
								// Low voltage recovery point of battery
								JSONObject analogObj6 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj6.put("1654539434", Integer.parseInt(p1, 16));
								else
									analogObj6.put("1654539434", "0");
								digitaljsonarr.put(analogObj6);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("0801")) {
					    String[] regparts = splitByLengths(regVal, 16);
					    for (int i = 0; i < regparts.length; i++) {
					        String p1 = regparts[i];
					        System.out.println(p1);

					        if (i == 0) {
					            // serial number (as string, not converted)
					            JSONObject analogObj5 = new JSONObject();
					            if (!regVal.equalsIgnoreCase("NO_RESP"))
					                analogObj5.put("1654541184", p1); // store string directly
					            else
					                analogObj5.put("1654541184", "0");
					            digitaljsonarr.put(analogObj5);
					        }
					    }
					}

					
					if (regAddress.equalsIgnoreCase("0805")) {
						String[] regparts = splitByLengths(regVal, 16);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// serial number
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654541184", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654541184", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("0809")) {
						String[] regparts = splitByLengths(regVal, 8);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Version number
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654543691", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654543691", "0");
								digitaljsonarr.put(analogObj5);
							}
						}
					}
					
					if (regAddress.equalsIgnoreCase("080b")) {
						String[] regparts = splitByLengths(regVal, 16);
						for (int i = 0; i < regparts.length; i++) {
							String p1 = regparts[i];
							System.out.println(p1);

							if (i == 0) {
								// Model
								JSONObject analogObj5 = new JSONObject();
								if (!regVal.equalsIgnoreCase("NO_RESP"))
									analogObj5.put("1654544167", Integer.parseInt(p1, 16));
								else
									analogObj5.put("1654544167", "0");
								digitaljsonarr.put(analogObj5);
							}
					
						
					

					/*
					 * if(i==2) { //System prompt JSONObject analogObj7 = new JSONObject();
					 * if(!regVal.equalsIgnoreCase("NO_RESP"))
					 * analogObj7.put("1654496260",Integer.parseInt(p1, 16)); else
					 * analogObj7.put("1654496260","0"); digitaljsonarr.put(analogObj7); }
					 */

				}
			}

		}
	}

	}System.out.println(digitaljsonarr);}
	// }

	private static String zeros(int n) {
		char[] buf = new char[n];
		Arrays.fill(buf, '0');
		return new String(buf);
	}

	// Split a string by exact segment lengths (in order)
	public static String[] splitByLengths(String s, int... lengths) {
		if ("NO_RESP".equals(s)) {
			String[] out = new String[lengths.length];
			for (int i = 0; i < lengths.length; i++) {
				out[i] = zeros(lengths[i]); // e.g., "0000" for 4
			}
			return out;
		}

		int total = 0;
		for (int len : lengths)
			total += len;
		if (s.length() != total) {
			throw new IllegalArgumentException(
					"String length " + s.length() + " doesn't match sum of lengths " + total);
		}

		String[] out = new String[lengths.length];
		int idx = 0;
		for (int i = 0; i < lengths.length; i++) {
			out[i] = s.substring(idx, idx + lengths[i]);
			idx += lengths[i];
		}
		return out;
	}

	static String[] splitByChunkSize(String s, int chunkSize) {
		if (chunkSize <= 0)
			throw new IllegalArgumentException("chunkSize must be > 0");

		int n = (int) Math.ceil(s.length() / (double) chunkSize);
		String[] out = new String[n];
		for (int i = 0; i < n; i++) {
			int start = i * chunkSize;
			int end = Math.min(start + chunkSize, s.length());
			out[i] = s.substring(start, end);
		}
		return out;
	}

}
