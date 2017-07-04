package org.jeesl.controller.processor;

import java.awt.Color;

public class ColorModelProcessor

{
	private static double[] hsv;
	private static double[] rgb;
	private static float[] hsb;
	@SuppressWarnings("unused")
	private static String inRGBString;
	private static int inRED, inGREEN, inBLUE;
	
	public ColorModelProcessor()
	{
	}
	
	public static double getHUE()
	{
		return hsv[0];
	}
	
	public static double getSAT()
	{
		return hsv[1];
	}
	
	public static double getVAL()
	{
		return hsv[2];
	}
	
	public static void setHUE(double hue)
	{
		ColorModelProcessor.hsv[0] = hue;
	}
	
	public static void setSAT(double sat)
	{
		ColorModelProcessor.hsv[1] = sat;
	}
	
	public static void setVAL(double val)
	{
		ColorModelProcessor.hsv[2] = val;
	}
	
	public static void init(String rgbString)
	{
		ColorModelProcessor.inRGBString = rgbString;
		ColorModelProcessor.inRED = Integer.parseInt(rgbString.substring(0, 2), 16);
		ColorModelProcessor.inGREEN = Integer.parseInt(rgbString.substring(2, 4), 16);
		ColorModelProcessor.inBLUE = Integer.parseInt(rgbString.substring(4, 6), 16);
		RGB2HSV(inRED, inGREEN, inBLUE);
		HSVtoRGB(hsv[0], hsv[1], hsv[2]);
	}
	
	public static double[] RGB2HSV(double r, double g, double b)
	{
		hsv = new double[3];
		hsb = Color.RGBtoHSB((int) r, (int) g, (int) b, null);
		double h = (double) hsb[0];
		double s = (double) hsb[1];
		double v = (double) hsb[2];
		hsv[0] = (Math.round(((h * 360) * 100))) / 100;
		hsv[1] = (Math.round(((s * 100) * 100))) / 100;
		hsv[2] = (Math.round(((v * 100) * 100))) / 100;
		return hsv;
	}
	
	public static double[] HSVtoRGB(double h, double s, double v)
	{
		
		rgb = new double[3];
		float h2 = (float) h;
		float s2 = (float) s;
		float v2 = (float) v;
		int rgbt = Color.HSBtoRGB((h2 / 360.0f), (s2 / 100.0f), (v2 / 100.0F));
		rgb[0] = Math.round((rgbt >> 16) & 0xFF);
		rgb[1] = Math.round((rgbt >> 8) & 0xFF);
		rgb[2] = Math.round(rgbt & 0xFF);
		return rgb;
	}
	
	public static String getNewRGBString()
	{	
		HSVtoRGB(hsv[0],hsv[1],hsv[2]);
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toHexString((int)Math.round(rgb[0])));
		sb.append(Integer.toHexString((int)Math.round(rgb[1])));
		sb.append(Integer.toHexString((int)Math.round(rgb[2])));
		return sb.toString();
	}
}
