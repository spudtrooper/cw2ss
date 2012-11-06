package com.jeffpalm.cw2ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class PuzFile implements IPuzFile {

  private final int width;
  private final int height;
  private final Cell[] cells;
  private int cellIndex;
  private List<String> clues = new ArrayList<String>();
  private final Map<Integer, String> acrossNumbers2clues = new HashMap<Integer, String>();
  private final Map<Integer, String> downNumbers2clues = new HashMap<Integer, String>();

  private String title, author, copyright;

  PuzFile(int width, int height) {
    this.width = width;
    this.height = height;
    this.cells = new Cell[width * height];
    this.cellIndex = 0;
  }

  public IPuzFile build() {
    int cellNumber = 1;
    int curCellNumber = 0;
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        if (this.isBlackCell(x, y)) {
          continue;
        }
        boolean assignedNumber = false;
        if (cellNeedsAcrossNumber(x, y)) {
          CellImpl c = (CellImpl) getCell(x, y);
          c.num = cellNumber;
          acrossNumbers2clues.put(cellNumber, clues.get(curCellNumber++));
          assignedNumber = true;
        }
        if (cellNeedsDownNumber(x, y)) {
          CellImpl c = (CellImpl) getCell(x, y);
          c.num = cellNumber;
          downNumbers2clues.put(cellNumber, clues.get(curCellNumber++));
          assignedNumber = true;
        }
        if (assignedNumber) {
          cellNumber += 1;
        }
      }
    }
    this.clues = null;
    return this;
  }

  private boolean cellNeedsAcrossNumber(int x, int y) {
    if (x == 0 || isBlackCell(x - 1, y)) {
      return true;
    }
    return false;
  }

  private boolean cellNeedsDownNumber(int x, int y) {
    if (y == 0 || isBlackCell(x, y - 1)) {
      return true;
    }
    return false;
  }

  private boolean isBlackCell(int x, int y) {
    return this.getCell(x, y) == null;
  }

  @Override
  public Cell getCell(int col, int row) {
    return this.cells[row * this.width + col];
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  private final static class ClueImpl implements Clue {
    private final int num;
    private final String str;

    ClueImpl(int num, String str) {
      this.num = num;
      this.str = str;
    }

    @Override
    public String getString() {
      return this.str;
    }

    @Override
    public int getNum() {
      return this.num;
    }
  }

  private final static class CellImpl implements Cell {
    private final char c;
    int num = -1;

    CellImpl(char c) {
      this.c = c;
    }

    @Override
    public char getChar() {
      return this.c;
    }

    @Override
    public int getNum() {
      return this.num;
    }
  }

  void add(char c) {
    this.cells[this.cellIndex++] = c == 0 ? null : new CellImpl(c);
  }

  void setTitle(String title) {
    this.title = title;
  }

  void setAuthor(String author) {
    this.author = author;
  }

  void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public String getAuthor() {
    return this.author;
  }

  @Override
  public String getCopyright() {
    return this.copyright;
  }

  public void addClue(String clue) {
    this.clues.add(clue);
  }

  @Override
  public List<Clue> getAcrossClues() {
    List<Clue> res = new ArrayList<Clue>();
    List<Integer> nums = new ArrayList<Integer>(this.acrossNumbers2clues.keySet());
    Collections.sort(nums);
    for (int n : nums) {
      res.add(new ClueImpl(n, this.acrossNumbers2clues.get(n)));
    }
    return res;
  }

  @Override
  public List<Clue> getDownClues() {
    List<Clue> res = new ArrayList<Clue>();
    List<Integer> nums = new ArrayList<Integer>(this.downNumbers2clues.keySet());
    Collections.sort(nums);
    for (int n : nums) {
      res.add(new ClueImpl(n, this.downNumbers2clues.get(n)));
    }
    return res;
  }

}
