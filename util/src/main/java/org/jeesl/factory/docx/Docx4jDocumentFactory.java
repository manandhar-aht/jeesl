package org.jeesl.factory.docx;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.Body;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.CTShd;
import org.docx4j.wml.CTTblPrBase.TblStyle;
import org.docx4j.wml.CTVerticalJc;
import org.docx4j.wml.Color;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBorder;
import org.docx4j.wml.STVerticalJc;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcMar;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.TcPrInner.GridSpan;
import org.docx4j.wml.TcPrInner.TcBorders;
import org.docx4j.wml.TcPrInner.VMerge;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;

public class Docx4jDocumentFactory
{
	public WordprocessingMLPackage wordMLPackage;
	public ObjectFactory factory;
	boolean landscape = false;
	
	public void addBoldStyle(RPr runProperties)
	{
		BooleanDefaultTrue b = new BooleanDefaultTrue();
		b.setVal(true);
		runProperties.setB(b);
	}
	
	public void addCellStyle(Tc tableCell, String content, DocxStyleBean style)
	{
		if (style != null)
		{
			
			P paragraph = factory.createP();
			
			Text text = factory.createText();
			text.setValue(content);
			
			R run = factory.createR();
			run.getContent().add(text);
			
			paragraph.getContent().add(run);
			
			setHorizontalAlignment(paragraph, style.getHorizAlignment());
			
			RPr runProperties = factory.createRPr();
			
			if (style.isBold())
			{
				addBoldStyle(runProperties);
			}
			if (style.isItalic())
			{
				addItalicStyle(runProperties);
			}
			if (style.isUnderline())
			{
				addUnderlineStyle(runProperties);
			}
			
			setFontSize(runProperties, style.getFontSize());
			setFontColor(runProperties, style.getFontColor());
			setFontFamily(runProperties, style.getFontFamily());
			
			setCellMargins(tableCell, style.getTop(), style.getRight(),
					
					style.getBottom(), style.getLeft());
			setCellColor(tableCell, style.getBackground());
			setVerticalAlignment(tableCell, style.getVerticalAlignment());
			
			setCellBorders(tableCell, style.isBorderTop(), style.isBorderRight(),
					
					style.isBorderBottom(), style.isBorderLeft());
			
			run.setRPr(runProperties);
			
			tableCell.getContent().add(paragraph);
		}
	}
	
	public void addImageCellStyle(Tc tableCell, P image, DocxStyleBean style)
	{
		setCellMargins(tableCell, style.getTop(), style.getRight(),
				
				style.getBottom(), style.getLeft());
		setCellColor(tableCell, style.getBackground());
		setVerticalAlignment(tableCell, style.getVerticalAlignment());
		setHorizontalAlignment(image, style.getHorizAlignment());
		setCellBorders(tableCell, style.isBorderTop(), style.isBorderRight(),
				
				style.isBorderBottom(), style.isBorderLeft());
		tableCell.getContent().add(image);
	}
	
	public void addItalicStyle(RPr runProperties)
	{
		BooleanDefaultTrue b = new BooleanDefaultTrue();
		b.setVal(true);
		runProperties.setI(b);
	}
	
	public void addTableCell(Tr tableRow, P image, int width, DocxStyleBean style,
			
			int horizontalMergedCells, String verticalMergedVal)
	{
		Tc tableCell = factory.createTc();
		addImageCellStyle(tableCell, image, style);
		setCellWidth(tableCell, width);
		setCellVMerge(tableCell, verticalMergedVal);
		setCellHMerge(tableCell, horizontalMergedCells);
		tableRow.getContent().add(tableCell);
	}
	
	public void addTableCell(Tr tableRow, String content, int width,
			
			DocxStyleBean style, int horizontalMergedCells, String verticalMergedVal)
	{
		Tc tableCell = factory.createTc();
		addCellStyle(tableCell, content, style);
		setCellWidth(tableCell, width);
		setCellVMerge(tableCell, verticalMergedVal);
		setCellHMerge(tableCell, horizontalMergedCells);
		if (style.isNoWrap())
		{
			setCellNoWrap(tableCell);
		}
		tableRow.getContent().add(tableCell);
	}
	
	public void addUnderlineStyle(RPr runProperties)
	{
		U val = new U();
		val.setVal(UnderlineEnumeration.SINGLE);
		runProperties.setU(val);
	}
	
	public Tbl createTableWithContent() throws IOException
	{
		Tbl table = factory.createTbl();
		
		// for TEST: this adds borders to all cells
		TblPr tblPr = new TblPr();
		TblStyle tblStyle = new TblStyle();
		tblStyle.setVal("TableGrid");
		tblPr.setTblStyle(tblStyle);
		table.setTblPr(tblPr);
		
		Tr tableRow = factory.createTr();
		
		// a default table cell style
		DocxStyleBean defStyle = new DocxStyleBean();
		defStyle.setBold(false);
		defStyle.setItalic(false);
		defStyle.setUnderline(false);
		defStyle.setHorizAlignment(JcEnumeration.CENTER);
		
		// a specific table cell style
		DocxStyleBean style = new DocxStyleBean();
		style.setBold(true);
		style.setItalic(true);
		style.setUnderline(true);
		style.setFontSize("40");
		style.setFontColor("FF00FF");
		style.setFontFamily("Book Antiqua");
		style.setTop(300);
		style.setBackground("CCFFCC");
		style.setVerticalAlignment(STVerticalJc.CENTER);
		style.setHorizAlignment(JcEnumeration.CENTER);
		style.setBorderTop(true);
		style.setBorderBottom(true);
		style.setNoWrap(true);
		
		addTableCell(tableRow, "Field 1", 3500, style, 1, null);
		// start vertical merge for Filed 2 and Field 3 on 3 rows
		addTableCell(tableRow, "Field 2", 3500, defStyle, 1, "restart");
		addTableCell(tableRow, "Field 3", 1500, defStyle, 1, "restart");
		table.getContent().add(tableRow);
		
		tableRow = factory.createTr();
		addTableCell(tableRow, "Text", 3500, defStyle, 1, null);
		addTableCell(tableRow, "", 3500, defStyle, 1, "");
		addTableCell(tableRow, "", 1500, defStyle, 1, "");
		table.getContent().add(tableRow);
		
		tableRow = factory.createTr();
		addTableCell(tableRow, "Interval", 3500, defStyle, 1, null);
		addTableCell(tableRow, "", 3500, defStyle, 1, "close");
		addTableCell(tableRow, "", 1500, defStyle, 1, "close");
		table.getContent().add(tableRow);
		
		// add an image horizontally merged on 3 cells
		String filenameHint = null;
		String altText = null;
		int id1 = 0;
		int id2 = 1;
		P pImage;
		File fi = new File("src\\test\\resources\\data\\docx\\images.jpg");
		BufferedImage originalImage = ImageIO.read(fi);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpg", baos);
		byte[] bytes = baos.toByteArray();
		
		try
		{
			pImage = newImage(wordMLPackage, bytes, filenameHint, altText, id1, id2, 8500);
			tableRow = factory.createTr();
			addTableCell(tableRow, pImage, 8500, defStyle, 3, null);
			table.getContent().add(tableRow);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return table;
	}
	
	protected int getDPI()
	{
		return GraphicsEnvironment.isHeadless() ? 96 : Toolkit.getDefaultToolkit().getScreenResolution();
	}
	
	/**
	 * @return the factory
	 */
	public ObjectFactory getFactory()
	{
		return factory;
	}
	
	/**
	 * @return the wordMLPackage
	 */
	public WordprocessingMLPackage getWordMLPackage()
	{
		return wordMLPackage;
	}
	
	/**
	 * @return the landscape
	 */
	public boolean isLandscape()
	{
		return landscape;
	}
	
	public void newDoc() throws Docx4JException, IOException
	{
		wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, landscape);
		factory = Context.getWmlObjectFactory();
		setPageMargins();
		Tbl table = createTableWithContent();
		wordMLPackage.getMainDocumentPart().addObject(table);
		wordMLPackage.save(new File("src\\test\\resources\\data\\docx\\testTables.docx"));
		
	}
	
	public P newImage(WordprocessingMLPackage wordMLPackage, byte[] bytes,
			
			String filenameHint, String altText, int id1, int id2, long cx) throws Exception
	{
		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
		Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, cx, false);
		// Now add the inline in w:p/w:r/w:drawing
		ObjectFactory factory = Context.getWmlObjectFactory();
		P p = factory.createP();
		R run = factory.createR();
		p.getContent().add(run);
		Drawing drawing = factory.createDrawing();
		run.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);
		return p;
	}
	
	public int pixelsToDxa(int pixels)
	{
		return (1440 * pixels / getDPI());
	}
	
	public void setCellBorders(Tc tableCell, boolean borderTop, boolean borderRight,
			
			boolean borderBottom, boolean borderLeft)
	{
		
		TcPr tableCellProperties = tableCell.getTcPr();
		if (tableCellProperties == null)
		{
			tableCellProperties = new TcPr();
			tableCell.setTcPr(tableCellProperties);
		}
		
		CTBorder border = new CTBorder();
		// border.setColor("auto");
		border.setColor("0000FF");
		border.setSz(new BigInteger("20"));
		border.setSpace(new BigInteger("0"));
		border.setVal(STBorder.SINGLE);
		
		TcBorders borders = new TcBorders();
		if (borderBottom)
		{
			borders.setBottom(border);
		}
		if (borderTop)
		{
			borders.setTop(border);
		}
		if (borderLeft)
		{
			borders.setLeft(border);
		}
		if (borderRight)
		{
			borders.setRight(border);
		}
		tableCellProperties.setTcBorders(borders);
	}
	
	public void setCellColor(Tc tableCell, String color)
	{
		if (color != null)
		{
			TcPr tableCellProperties = tableCell.getTcPr();
			if (tableCellProperties == null)
			{
				tableCellProperties = new TcPr();
				tableCell.setTcPr(tableCellProperties);
			}
			CTShd shd = new CTShd();
			shd.setFill(color);
			tableCellProperties.setShd(shd);
		}
	}
	
	public void setCellHMerge(Tc tableCell, int horizontalMergedCells)
	{
		if (horizontalMergedCells > 1)
		{
			TcPr tableCellProperties = tableCell.getTcPr();
			if (tableCellProperties == null)
			{
				tableCellProperties = new TcPr();
				tableCell.setTcPr(tableCellProperties);
			}
			
			GridSpan gridSpan = new GridSpan();
			gridSpan.setVal(new BigInteger(String.valueOf(horizontalMergedCells)));
			
			tableCellProperties.setGridSpan(gridSpan);
			tableCell.setTcPr(tableCellProperties);
		}
	}
	
	public void setCellMargins(Tc tableCell, int top, int right, int bottom, int left)
	{
		TcPr tableCellProperties = tableCell.getTcPr();
		if (tableCellProperties == null)
		{
			tableCellProperties = new TcPr();
			tableCell.setTcPr(tableCellProperties);
		}
		TcMar margins = new TcMar();
		
		if (bottom > 0)
		{
			TblWidth bW = new TblWidth();
			bW.setType("dxa");
			bW.setW(BigInteger.valueOf(bottom));
			margins.setBottom(bW);
		}
		
		if (top > 0)
		{
			TblWidth tW = new TblWidth();
			tW.setType("dxa");
			tW.setW(BigInteger.valueOf(top));
			margins.setTop(tW);
		}
		
		if (left > 0)
		{
			TblWidth lW = new TblWidth();
			lW.setType("dxa");
			lW.setW(BigInteger.valueOf(left));
			margins.setLeft(lW);
		}
		
		if (right > 0)
		{
			TblWidth rW = new TblWidth();
			rW.setType("dxa");
			rW.setW(BigInteger.valueOf(right));
			margins.setRight(rW);
		}
		
		tableCellProperties.setTcMar(margins);
	}
	
	public void setCellNoWrap(Tc tableCell)
	{
		TcPr tableCellProperties = tableCell.getTcPr();
		if (tableCellProperties == null)
		{
			tableCellProperties = new TcPr();
			tableCell.setTcPr(tableCellProperties);
		}
		BooleanDefaultTrue b = new BooleanDefaultTrue();
		b.setVal(true);
		tableCellProperties.setNoWrap(b);
	}
	
	public void setCellVMerge(Tc tableCell, String mergeVal)
	{
		if (mergeVal != null)
		{
			TcPr tableCellProperties = tableCell.getTcPr();
			if (tableCellProperties == null)
			{
				tableCellProperties = new TcPr();
				tableCell.setTcPr(tableCellProperties);
			}
			VMerge merge = new VMerge();
			if (!"close".equals(mergeVal))
			{
				merge.setVal(mergeVal);
			}
			tableCellProperties.setVMerge(merge);
		}
	}
	
	public void setCellWidth(Tc tableCell, int width)
	{
		if (width > 0)
		{
			TcPr tableCellProperties = tableCell.getTcPr();
			if (tableCellProperties == null)
			{
				tableCellProperties = new TcPr();
				tableCell.setTcPr(tableCellProperties);
			}
			TblWidth tableWidth = new TblWidth();
			tableWidth.setType("dxa");
			tableWidth.setW(BigInteger.valueOf(width));
			tableCellProperties.setTcW(tableWidth);
		}
	}
	
	/**
	 * @param factory
	 *            the factory to set
	 */
	public void setFactory(ObjectFactory factory)
	{
		this.factory = factory;
	}
	
	public void setFontColor(RPr runProperties, String color)
	{
		if (color != null)
		{
			Color c = new Color();
			c.setVal(color);
			runProperties.setColor(c);
		}
	}
	
	public void setFontFamily(RPr runProperties, String fontFamily)
	{
		if (fontFamily != null)
		{
			RFonts rf = runProperties.getRFonts();
			if (rf == null)
			{
				rf = new RFonts();
				runProperties.setRFonts(rf);
			}
			rf.setAscii(fontFamily);
		}
		
	}
	
	public void setFontSize(RPr runProperties, String fontSize)
	{
		if (fontSize != null && !fontSize.isEmpty())
		{
			HpsMeasure size = new HpsMeasure();
			size.setVal(new BigInteger(fontSize));
			runProperties.setSz(size);
			runProperties.setSzCs(size);
		}
		
	}
	
	public void setHorizontalAlignment(P paragraph, JcEnumeration hAlign)
	{
		if (hAlign != null)
		{
			PPr pprop = new PPr();
			Jc align = new Jc();
			align.setVal(hAlign);
			pprop.setJc(align);
			paragraph.setPPr(pprop);
		}
	}
	
	/**
	 * @param landscape
	 *            the landscape to set
	 */
	public void setLandscape(boolean landscape)
	{
		this.landscape = landscape;
	}
	
	public void setPageMargins() throws Docx4JException
	{
		Body body = wordMLPackage.getMainDocumentPart().getContents().getBody();
		PageDimensions page = new PageDimensions();
		PgMar pgMar = page.getPgMar();
		pgMar.setBottom(BigInteger.valueOf(pixelsToDxa(50)));
		pgMar.setTop(BigInteger.valueOf(pixelsToDxa(50)));
		pgMar.setLeft(BigInteger.valueOf(pixelsToDxa(50)));
		pgMar.setRight(BigInteger.valueOf(pixelsToDxa(50)));
		SectPr sectPr = factory.createSectPr();
		body.setSectPr(sectPr);
		sectPr.setPgMar(pgMar);
	}
	
	public void setVerticalAlignment(Tc tableCell, STVerticalJc align)
	{
		if (align != null)
		{
			TcPr tableCellProperties = tableCell.getTcPr();
			if (tableCellProperties == null)
			{
				tableCellProperties = new TcPr();
				tableCell.setTcPr(tableCellProperties);
			}
			
			CTVerticalJc valign = new CTVerticalJc();
			valign.setVal(align);
			
			tableCellProperties.setVAlign(valign);
		}
	}
	
	/**
	 * @param wordMLPackage
	 *            the wordMLPackage to set
	 */
	public void setWordMLPackage(WordprocessingMLPackage wordMLPackage)
	{
		this.wordMLPackage = wordMLPackage;
	}
}
