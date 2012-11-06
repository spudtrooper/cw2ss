package com.jeffpalm.cw2ss;

import java.util.List;

interface IPuzFile {

  /** Value of a black cell returned by {@link #getCell(int, int)}. */
  Cell BLACK = null;

  interface Cell {
    char getChar();

    int getNum();
  }

  interface Clue {
    String getString();

    int getNum();
  }

  List<Clue> getAcrossClues();

  List<Clue> getDownClues();

  /**
   * Returns a valid cell or <code>null</code> for black cell.
   * 
   * @param row
   *          row of the cell
   * @param col
   *          column of the cell
   * @return a valid cell or <code>null</code> for black cell.
   */
  Cell getCell(int row, int col);

  int getWidth();

  int getHeight();

  String getTitle();

  String getAuthor();

  String getCopyright();

}