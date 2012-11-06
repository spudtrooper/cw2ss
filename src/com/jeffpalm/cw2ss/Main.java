package com.jeffpalm.cw2ss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.write.WriteException;

import com.jeffpalm.cw2ss.PuzFileReader.FormatException;

public class Main {

	public static void main(String[] args) {
		System.exit(new Main().realMain(args));
	}

	private int realMain(String[] args) {
		File puzFile = null;
		if (args.length > 0) {
			puzFile = new File(args[0]);
		} else {
			try {
				puzFile = this.downloadPuzFile();
			} catch (IOException e) {
				Log.handle(e);
				return 1;
			}
		}
		try {
			this.createPuzzle(puzFile);
			return 0;
		} catch (IOException e) {
			Log.handle(e);
		} catch (FormatException e) {
			Log.handle(e);
		}
		return 1;
	}

	private int createPuzzle(File puzFile) throws IOException, FormatException {
		InputStream in = new FileInputStream(puzFile);
		IPuzFile puz = new PuzFileReader().read(in);
		Log.info("\n" + new PuzFilePrinter().toString(puz));
		int res = buildPuzzle(puz, puzFile);
		in.close();
		return res;
	}

	private int buildPuzzle(IPuzFile puz, File inputFile) {
		String name = inputFile.getName();
		int ilastDot = name.lastIndexOf('.');
		if (ilastDot != -1) {
			name = name.substring(0, ilastDot);
		}
		File outFile = new File(name + ".xls");
		try {
			new PuzFileBuilder().build(puz, outFile);
		} catch (IOException e) {
			Log.handle(e);
			return 1;
		} catch (WriteException e) {
			Log.handle(e);
			return 1;
		}
		return 0;
	}

	private File downloadPuzFile() throws IOException {
		return new PuzFileDownloader().download();
	}
}
