package com.jeffpalm.cw2ss;

final class PuzFilePrinter {

  public String toString(IPuzFile p) {
    final StringBuilder res = new StringBuilder();
    int w = p.getWidth();
    int h = p.getHeight();
    res.append(" (");
    res.append(w);
    res.append("x");
    res.append(h);
    res.append(")\n");
    res.append("*");
    for (int i = 0; i < w; i++) {
      res.append("-");
    }
    res.append("*");
    res.append("\n");
    for (int row = 0; row < h; row++) {
      res.append("|");
      for (int col = 0; col < w; col++) {
        IPuzFile.Cell c = p.getCell(col, row);
        res.append(c == null ? ' ' : c.getChar());

      }
      res.append("|");
      res.append("\n");
    }
    res.append("*");
    for (int i = 0; i < w; i++) {
      res.append("-");
    }
    res.append("*");
    {
      String s = p.getTitle();
      if (s != null) {
        res.append("\nTitle: ");
        res.append(s);
      }
    }
    {
      String s = p.getAuthor();
      if (s != null) {
        res.append("\nAuthor: ");
        res.append(s);
      }
    }
    {
      String s = p.getCopyright();
      if (s != null) {
        res.append("\nCopyright: ");
        res.append(s);
      }
    }
    return res.toString();
  }
}
