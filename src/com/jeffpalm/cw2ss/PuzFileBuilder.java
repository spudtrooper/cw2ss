package com.jeffpalm.cw2ss;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

final class PuzFileBuilder {

	/**
	 * Outputs <code>puz</code> to <code>outFile</code> in XLS format.
	 * 
	 * @param puz
	 *          puzzle
	 * @param outFile
	 *          output file
	 * @throws IOException
	 * @throws WriteException
	 */
	public void build(IPuzFile puz, File outFile) throws IOException, WriteException {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(outFile, ws);
		WritableSheet sheet = workbook.createSheet(outFile.getName(), 0);
		int w = puz.getWidth();
		int h = puz.getHeight();
		Log.info("Writing " + w + "x" + h + " to " + outFile);
		final WritableFont font = new WritableFont(WritableFont.ARIAL, 6);

		final int startCol = 1;
		final int startRow = 1;

		// Main board
		for (int row = 0; row < puz.getHeight(); row++) {
			for (int col = 0; col < puz.getWidth(); col++) {
				IPuzFile.Cell c = puz.getCell(row, col);
				String str;
				Colour colour;
				if (c == null) {
					colour = Colour.BLACK;
					str = "";
				} else {
					colour = Colour.WHITE;
					int n = c.getNum();
					str = n == -1 ? "" : String.valueOf(n);
				}
				WritableCellFormat format = new WritableCellFormat(font);
				format.setAlignment(Alignment.LEFT);
				format.setVerticalAlignment(VerticalAlignment.TOP);
				format.setShrinkToFit(true);
				format.setBackground(colour);
				format.setBorder(Border.ALL, BorderLineStyle.THIN);
				Label lab = new Label(startRow + row, startCol + col, str, format);
				sheet.addCell(lab);
			}
		}

		// Across clues
		{
			int col = 0;
			{
				WritableCellFormat format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12));
				Label lab = new Label(startRow + puz.getWidth() + 1, startCol + col++, "Across", format);
				sheet.addCell(lab);
			}
			for (IPuzFile.Clue clue : puz.getAcrossClues()) {
				WritableCellFormat format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8));
				String str = clue.getNum() + ". " + clue.getString();
				Log.info("Clue: " + str);
				Label lab = new Label(startRow + puz.getWidth() + 1, startCol + col++, str, format);
				sheet.addCell(lab);
			}
		}

		// Down clues
		{
			int col = 0;
			{
				WritableCellFormat format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12));
				Label lab = new Label(startRow + puz.getWidth() + 2, startCol + col++, "Down", format);
				sheet.addCell(lab);
			}
			for (IPuzFile.Clue clue : puz.getDownClues()) {
				WritableCellFormat format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8));
				String str = clue.getNum() + ". " + clue.getString();
				Log.info("Clue: " + str);
				Label lab = new Label(startRow + puz.getWidth() + 2, startCol + col++, str, format);
				sheet.addCell(lab);
			}
		}
		// Close
		workbook.write();
		workbook.close();
		Log.info("Done writing to " + outFile);
	}

}
