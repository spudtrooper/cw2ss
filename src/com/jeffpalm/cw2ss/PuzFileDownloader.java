package com.jeffpalm.cw2ss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

final class PuzFileDownloader {

  private final String PUZZLE_URL = "http://www.nytimes.com/specials/puzzles/classic.puz";

  public File download() throws IOException {
    URL u = new URL(PUZZLE_URL);
    BufferedInputStream in = new java.io.BufferedInputStream(u.openStream());
    final File f = new File(u.getFile());
    FileOutputStream fos = new FileOutputStream(f);
    BufferedOutputStream out = new BufferedOutputStream(fos, 1024);
    final byte data[] = new byte[1024];
    int count;
    while ((count = in.read(data, 0, data.length)) > 0) {
      out.write(data, 0, count);
    }
    out.close();
    in.close();
    return f;
  }

}
