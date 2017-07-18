package org.jeesl.factory.docx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XwpfDocumentFactory
{
	final static Logger logger = LoggerFactory.getLogger(XwpfDocumentFactory.class);
	private XWPFDocument document;
	private XWPFParagraph paragraph;
	private XWPFTable table;
	private XWPFTableRow tblRow;
	private XWPFTableCell tblCell;
	XWPFRun run;
	private String fontStyle1,fontStyle2;
	private int fontSize1,fontSize2;
	private ParagraphAlignment alignment1, alignment2;
	private boolean bold1, bold2, italic1, italic2;
	
	public XwpfDocumentFactory(){}
	
	public void createDocument()
	{
		document = new XWPFDocument();
		//...for adding some presets or something else....
	}
	
	public void setFileStream2Document(FileInputStream fis) throws IOException
	{
		document = new XWPFDocument(fis);
	}
	
	public XWPFParagraph createTableParagraph(String setText,ParagraphAlignment alignment, String fontStyle, int fontSize, boolean bold, boolean italic)
	{
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(alignment);
		run=paragraph.createRun();
		run.setFontFamily(fontStyle);
		run.setFontSize(fontSize);
		run.setBold(bold);
		run.setItalic(italic);
		run.setText(setText);
		return paragraph;
	}
	
	public void run(String setText,ParagraphAlignment alignment, String fontStyle, int fontSize, boolean bold, boolean italic)	
	{
			paragraph.setAlignment(alignment);
			run=paragraph.createRun();
			run.setFontFamily(fontStyle);
			run.setFontSize(fontSize);
			run.setBold(bold);
			run.setItalic(italic);
			run.setText(setText);
			run.addBreak();
	}

	
	public void createTableParagraph()
	{
		paragraph = document.createParagraph();
	}
	
	public void createParagraph(String setText,ParagraphAlignment alignment, String fontStyle, int fontSize, boolean bold, boolean italic)
	{
		paragraph = document.createParagraph();
		paragraph.setAlignment(alignment);
		run=paragraph.createRun();
		run.setFontFamily(fontStyle);
		run.setFontSize(fontSize);
		run.setBold(bold);
		run.setItalic(italic);
		run.setText(setText);
	
	}
		
	public void selectTable(int number)
	{
		table = document.getTables().get(number);
	}
	
	public void selectTableField(int row, int cell)
	{
		tblRow = table.getRow(row);
		tblCell = tblRow.getCell(cell);
	}
	
	private void selectStylePreset(int id)
	{
		switch (id)
		{
			case 0 : 
				fontStyle1="Arial";
				fontStyle2="Arial";
				fontSize1=8;
				fontSize2=12;
				alignment1=ParagraphAlignment.LEFT;
				alignment2=ParagraphAlignment.LEFT;
				bold1=true;
				bold2=false;
				italic1=false;
				italic2=false;						
				break;
				
			case 1 :
				
				break;
			case 2 :

				break;
			case 3 : 
				
				break;
			default: 
				
				break;
		}
	}
	
	
	public void insertDefAndTextIntoTableField(String def, String text, int StylePreset)
	{
		selectStylePreset(StylePreset);
		createTableParagraph();
		run(def, alignment1, fontStyle1, fontSize1, bold1, italic1);
		tblCell.setParagraph(document.getLastParagraph());
		run(text, alignment2, fontStyle2, fontSize2, bold2, italic2);
		tblCell.setParagraph(document.getLastParagraph());
	

	}
	
	public XWPFTable createTable(XWPFDocument document, int rows, int cells)
	{
		XWPFTable table = document.createTable();
		XWPFTableRow row = table.getRow(0);
		row.getCell(0).setText("");
		for (int r=1;r<rows;r++)
		{
			XWPFTableRow nR = table.createRow();
			nR.getCell(0).setText("");
			for (int c=1;c<cells;c++)
			{
				nR.addNewTableCell().setText("");
			}
		}
		return table;
	}
	
	public void applyingBorder(Borders bordersStyle)
	{
		  paragraph.setBorderBottom(bordersStyle);
	      paragraph.setBorderLeft(bordersStyle);
	      paragraph.setBorderRight(bordersStyle);
	      paragraph.setBorderTop(bordersStyle);
	}
	
	public void writeDocument(String filePath) throws IOException
	{
		FileOutputStream out = new FileOutputStream(new File(filePath));
		document.write(out);
	    out.close();
	    logger.debug(filePath + " was written successfully");
	}
	
	
}
