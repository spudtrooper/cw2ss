package com.jeffpalm.cw2ss;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.jeffpalm.cw2ss.PuzFileReader.FormatException;

public class PuzFileReaderTest {

	@Test
	public void test() throws IOException, FormatException {
		PuzFileReader r = new PuzFileReader();
		File inFile = new File("tst/classic.puz");
		InputStream in = new FileInputStream(inFile);
		IPuzFile puz = r.read(in);
		in.close();
		assertEquals(15, puz.getWidth());
		assertEquals(15, puz.getHeight());
		assertEquals("NY Times, Wed, Nov 25, 1998", puz.getTitle());
		assertEquals("Rich Norris / Will Shortz", puz.getAuthor().trim());
		assertEquals("© 1998, The New York Times", puz.getCopyright());
	}

}
