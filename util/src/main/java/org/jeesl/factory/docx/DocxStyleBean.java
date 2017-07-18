package org.jeesl.factory.docx;

import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.STVerticalJc;

public class DocxStyleBean
{
	private boolean bold;
	private boolean italic;
	private boolean underline;
	private String fontSize;
	private String fontColor;
	private String fontFamily;
	
	// cell margins
	private int left;
	private int bottom;
	private int top;
	private int right;
	
	private String background;
	private STVerticalJc verticalAlignment;
	private JcEnumeration horizAlignment;
	
	private boolean borderLeft;
	private boolean borderRight;
	private boolean borderTop;
	private boolean borderBottom;
	private boolean noWrap;
	
	/**
	 * @return the bold
	 */
	public boolean isBold()
	{
		return bold;
	}
	
	/**
	 * @param bold
	 *            the bold to set
	 */
	public void setBold(boolean bold)
	{
		this.bold = bold;
	}
	
	/**
	 * @return the italic
	 */
	public boolean isItalic()
	{
		return italic;
	}
	
	/**
	 * @param italic
	 *            the italic to set
	 */
	public void setItalic(boolean italic)
	{
		this.italic = italic;
	}
	
	/**
	 * @return the underline
	 */
	public boolean isUnderline()
	{
		return underline;
	}
	
	/**
	 * @param underline
	 *            the underline to set
	 */
	public void setUnderline(boolean underline)
	{
		this.underline = underline;
	}
	
	/**
	 * @return the fontSize
	 */
	public String getFontSize()
	{
		return fontSize;
	}
	
	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(String fontSize)
	{
		this.fontSize = fontSize;
	}
	
	/**
	 * @return the fontColor
	 */
	public String getFontColor()
	{
		return fontColor;
	}
	
	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(String fontColor)
	{
		this.fontColor = fontColor;
	}
	
	/**
	 * @return the fontFamily
	 */
	public String getFontFamily()
	{
		return fontFamily;
	}
	
	/**
	 * @param fontFamily
	 *            the fontFamily to set
	 */
	public void setFontFamily(String fontFamily)
	{
		this.fontFamily = fontFamily;
	}
	
	/**
	 * @return the left
	 */
	public int getLeft()
	{
		return left;
	}
	
	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(int left)
	{
		this.left = left;
	}
	
	/**
	 * @return the bottom
	 */
	public int getBottom()
	{
		return bottom;
	}
	
	/**
	 * @param bottom
	 *            the bottom to set
	 */
	public void setBottom(int bottom)
	{
		this.bottom = bottom;
	}
	
	/**
	 * @return the top
	 */
	public int getTop()
	{
		return top;
	}
	
	/**
	 * @param top
	 *            the top to set
	 */
	public void setTop(int top)
	{
		this.top = top;
	}
	
	/**
	 * @return the right
	 */
	public int getRight()
	{
		return right;
	}
	
	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(int right)
	{
		this.right = right;
	}
	
	/**
	 * @return the background
	 */
	public String getBackground()
	{
		return background;
	}
	
	/**
	 * @param background
	 *            the background to set
	 */
	public void setBackground(String background)
	{
		this.background = background;
	}
	
	/**
	 * @return the verticalAlignment
	 */
	public STVerticalJc getVerticalAlignment()
	{
		return verticalAlignment;
	}
	
	/**
	 * @param verticalAlignment
	 *            the verticalAlignment to set
	 */
	public void setVerticalAlignment(STVerticalJc verticalAlignment)
	{
		this.verticalAlignment = verticalAlignment;
	}
	
	/**
	 * @return the horizAlignment
	 */
	public JcEnumeration getHorizAlignment()
	{
		return horizAlignment;
	}
	
	/**
	 * @param horizAlignment
	 *            the horizAlignment to set
	 */
	public void setHorizAlignment(JcEnumeration horizAlignment)
	{
		this.horizAlignment = horizAlignment;
	}
	
	/**
	 * @return the borderLeft
	 */
	public boolean isBorderLeft()
	{
		return borderLeft;
	}
	
	/**
	 * @param borderLeft
	 *            the borderLeft to set
	 */
	public void setBorderLeft(boolean borderLeft)
	{
		this.borderLeft = borderLeft;
	}
	
	/**
	 * @return the borderRight
	 */
	public boolean isBorderRight()
	{
		return borderRight;
	}
	
	/**
	 * @param borderRight
	 *            the borderRight to set
	 */
	public void setBorderRight(boolean borderRight)
	{
		this.borderRight = borderRight;
	}
	
	/**
	 * @return the borderTop
	 */
	public boolean isBorderTop()
	{
		return borderTop;
	}
	
	/**
	 * @param borderTop
	 *            the borderTop to set
	 */
	public void setBorderTop(boolean borderTop)
	{
		this.borderTop = borderTop;
	}
	
	/**
	 * @return the borderBottom
	 */
	public boolean isBorderBottom()
	{
		return borderBottom;
	}
	
	/**
	 * @param borderBottom
	 *            the borderBottom to set
	 */
	public void setBorderBottom(boolean borderBottom)
	{
		this.borderBottom = borderBottom;
	}
	
	/**
	 * @return the noWrap
	 */
	public boolean isNoWrap()
	{
		return noWrap;
	}
	
	/**
	 * @param noWrap
	 *            the noWrap to set
	 */
	public void setNoWrap(boolean noWrap)
	{
		this.noWrap = noWrap;
	}
	
}
