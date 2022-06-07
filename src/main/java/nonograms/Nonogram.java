package nonograms;

import nonograms.formatters.NonogramFormatter;

import java.util.List;

public interface Nonogram {
    boolean solve();

    String getFormatted(NonogramFormatter formatter);

    List<Integer> getRow(int index);

    List<Integer> getCol(int index);

    int getHeight();
    int getWidth();

    boolean validateBoard();
}
