package com.jeffpalm.cw2ss;

import java.io.IOException;
import java.io.InputStream;

final class PuzFileReader {

  public IPuzFile read(InputStream in) throws IOException, FormatException {
    int checkSum = readShort(in);
    String magic = readString(in);
    if (!"ACROSS&DOWN".equals(magic)) {
      throw new FormatException("Invalid magic:" + magic);
    }
    int cibCheckSum = readShort(in);
    int maskedLowChecksums = readInt(in);
    int maskedHighChecksums = readInt(in);
    String versionString = readString(in);
    int reserved1C = readShort(in);
    int scrambledChecksum = readShort(in);
    for (int i = 0; i < 0xC; i++) {
      readByte(in);
    }
    int width = readByte(in);
    int height = readByte(in);
    Log.info("Size: " + width + "x" + height);
    PuzFile res = new PuzFile(width, height);
    int numClues = readShort(in);
    int unknownBitmask = readShort(in);
    int scrambledTag = readShort(in);
    for (int i = 0, N = width * height; i < N; i++) {
      char c = (char) readByte(in);
      if (c == '.') {
        c = 0;
      }
      res.add(c);
    }
    for (int i = 0, N = width * height; i < N; i++) {
      readByte(in);
    }
    String title = readString(in);
    String author = readString(in);
    String copyright = readString(in);
    res.setTitle(title);
    res.setAuthor(author);
    res.setCopyright(copyright);
    for (int i = 0; i < numClues; i++) {
      res.addClue(readString(in));
    }
    return res.build();
  }

  final static class FormatException extends Exception {
    FormatException(String msg) {
      super(msg);
    }
  }

  private int readByte(InputStream in) throws IOException {
    return in.read();
  }

  private String readString(InputStream in) throws IOException {
    final char[] data = new char[256];
    int count = 0;
    while (count < data.length) {
      char c = (char) (in.read() & 0xff);
      if (c == 0) {
        break;
      }
      data[count++] = c;
    }
    return String.valueOf(data, 0, count);
  }

  private int readShort(InputStream in) throws IOException {
    int b0 = in.read();
    int b1 = in.read();
    return (b0 & 0xff) | (b1 & 0xff) << 8;
  }

  private int readInt(InputStream in) throws IOException {
    int b0 = in.read();
    int b1 = in.read();
    int b2 = in.read();
    int b3 = in.read();
    return (b0 & 0xff) | (b1 & 0xff) << 8 | (b2 & 0xff) << 16 | (b3 & 0xff) << 24;
  }
}
