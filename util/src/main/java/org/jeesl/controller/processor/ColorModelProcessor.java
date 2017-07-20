package org.jeesl.controller.processor;

import java.awt.Color;

public class ColorModelProcessor

{
	private double[] hsv;
	private double[] rgb;
	private float[] hsb;
	private int inRED, inGREEN, inBLUE;
	
	/**
	 * edit the hue, saturation or value (HSV) of an RGB color (red,green,blue)
	 * and get back RGB 
	 * 
	 * @param rgbString
	 *            - RGB-color-code like "FF00AA"
	 * 
	 */
	public ColorModelProcessor(String rgbString)
	{
		this.inRED = Integer.parseInt(rgbString.substring(0, 2), 16);
		this.inGREEN = Integer.parseInt(rgbString.substring(2, 4), 16);
		this.inBLUE = Integer.parseInt(rgbString.substring(4, 6), 16);
		RGB2HSV(inRED, inGREEN, inBLUE);
		HSVtoRGB(hsv[0], hsv[1], hsv[2]);
	}
	
	/**
	 * @return double - actual Hue
	 */
	public double getHUE()
	{
		return hsv[0];
	}
	
	/**
	 * @return double - actual saturation
	 */
	public double getSAT()
	{
		return hsv[1];
	}
	
	/**
	 * @return double - actual value
	 */
	public double getVAL()
	{
		return hsv[2];
	}
	
	/**
	 * @param hue
	 *            - set hue (0-100)
	 */
	public void setHUE(double hue)
	{
		this.hsv[0] = hue;
	}
	
	/**
	 * @param sat
	 *            - set saturation (0-100)
	 */
	public void setSAT(double sat)
	{
		this.hsv[1] = sat;
	}
	
	/**
	 * @param val
	 *            - set value (0-100)
	 */
	public void setVAL(double val)
	{
		this.hsv[2] = val;
	}
	
	/**
	 * @param r
	 *            - set red (0-255)
	 * @param g
	 *            - set green (0-255)
	 * @param b
	 *            - set blue (0-255)
	 * @return double[] - hue, saturation, value
	 */
	public double[] RGB2HSV(double r, double g, double b)
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
	
	/**
	 * @param h
	 *            - set hue (0-100)
	 * @param s
	 *            - set saturation (0-100)
	 * @param v
	 *            - set value (0-100)
	 * @return double[] - red, green, blue
	 */
	public double[] HSVtoRGB(double h, double s, double v)
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
	
	/**
	 * @return String new RGB String like "FF8822"
	 */
	public String getNewRGBString()
	{
		HSVtoRGB(hsv[0], hsv[1], hsv[2]);
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toHexString((int) Math.round(rgb[0])));
		sb.append(Integer.toHexString((int) Math.round(rgb[1])));
		sb.append(Integer.toHexString((int) Math.round(rgb[2])));
		return sb.toString();
	}
}
