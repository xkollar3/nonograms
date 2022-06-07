package nonograms.formatters;

import nonograms.Nonogram;

import java.util.List;

/**
 * Every symbol is doubled because it
 * ascii aspect ratio is weird
 */
public class CmdNonogram implements NonogramFormatter {
    @Override
    public String format(Nonogram nonogram) {
        StringBuilder res = new StringBuilder();
        String delimiter = "+" + "-".repeat(nonogram.getWidth()*2) + "+" + System.lineSeparator();
        res.append(delimiter);
        for (int i = 0; i < nonogram.getHeight(); i++) {
            List<Integer> row = nonogram.getRow(i);
            res.append("|");
            for (Integer symbol: row
                 ) {
                res.append(symbol == 1 ? "XX" : "  ");
            }
            res.append("|");
            res.append(System.lineSeparator());
        }
        res.append(delimiter);
        return res.toString();
    }
}
