package nonograms;

import nonograms.formatters.NonogramFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * My very simple representation of a nonogram
 *
 * the solve algorhithm works as follows
 * generate the possibilites for each row
 * and then dfs through them until a solution is valid by column hints
 *
 */
public class NonogramImpl implements Nonogram {
    private final int height;
    private final int width;

    private final List<List<Integer>> rowHints;
    private final List<List<Integer>> colHints;

    private final List<List<Boolean>> board;

    public NonogramImpl(int rowSize, int colSize, List<List<Integer>> rowHints, List<List<Integer>> colHints) {
        this.height = rowSize;
        this.width = colSize;
        this.colHints = colHints;
        this.rowHints = rowHints;
        board = initBoard();
    }

    private List<List<Boolean>> initBoard() {
        List<List<Boolean>> res = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            res.add(IntStream.iterate(0, n -> 0)
                    .limit(width).boxed()
                    .map(n -> false)
                    .collect(Collectors.toList()));

        }
        return res;
    }

    @Override
    public boolean solve() {
        return solveHelper(0, Stream.iterate(0, n -> n + 1).limit(height).map(rowNum -> genlines(rowHints.get(rowNum), width)).collect(Collectors.toList()));
    }

    private boolean solveHelper(int row, List<List<List<Integer>>> rowHints) {
        if (row == height) {
            return validateBoard();
        }

        for (List<Integer> possibleLine: rowHints.get(row)
             ) {
            board.set(row, possibleLine.stream().map(n -> n == 1).collect(Collectors.toList()));
            if (solveHelper(row + 1, rowHints)) {
                return true;
            }
        }

        board.set(row, IntStream.iterate(0, n -> 0).limit(width).boxed().map(n -> false).collect(Collectors.toList()));
        return false;
    }

    private List<List<Integer>> genlines(List<Integer> hint, int length) {
        return genlinesRec(hint, length, new ArrayList<>(), new ArrayList<>());
    }

    private List<List<Integer>> genlinesRec(List<Integer> hint, int length,
                                            List<Integer> current, List<List<Integer>> all) {
        if (hint.size() == 0) {
            if (current.size() - 1 == length && current.get(current.size() - 1) == 0) {
                current.remove(current.size() - 1);
            }
            if (current.size() <= length) {
                current.addAll(IntStream.iterate(0, n -> 0).limit(length - current.size())
                        .boxed().collect(Collectors.toList()));
                all.add(current);
            }
            return all;
        }

        if (current.size() > length) {
            return all;
        }

        for (int i = 0; i < length; i++) {
            List<Integer> newrow = new ArrayList<>(current);
            newrow.addAll(IntStream.iterate(0, n -> 0).limit(i).boxed().collect(Collectors.toList()));
            newrow.addAll(IntStream.iterate(1, n -> 1).limit(hint.get(0)).boxed().collect(Collectors.toList()));
            newrow.add(0);
            if (hint.subList(1, hint.size()).stream().mapToInt(Integer::intValue).sum() + hint.subList(1, hint.size()).size() - 1 + newrow.size() <= length) {
                genlinesRec(hint.subList(1, hint.size()), length, newrow, all);
            }
            else {
                break;
            }
        }
        return all;
    }


    @Override
    public String getFormatted(NonogramFormatter formatter) {
        return formatter.format(this);
    }

    @Override
    public List<Integer> getRow(int index) {
        if (index < 0 || index >= height) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return board.get(index).stream().map(x -> !x ? 0 : 1).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getCol(int index) {
        if (index < 0 || index >= width) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return board.stream().map(x -> !x.get(index) ? 0 : 1).collect(Collectors.toList());
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean validateBoard() {
        return validateByRows() && validateByCols();
    }

    private boolean validateByRows() {
        for (int i = 0; i < height; i++) {
            List<Integer> row = getRow(i);
            if (!rowHints.get(i).equals(getHints(row))) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> getHints(List<Integer> line) {
        int hintCounter = 0;
        List<Integer> res = new ArrayList<>();
        for (Integer i : line
        ) {
            if (i == 0 && hintCounter != 0) {
                res.add(hintCounter);
                hintCounter = 0;
            } else if (i == 1){
                hintCounter++;
            }
        }

        if (hintCounter != 0) {
            res.add(hintCounter);
        }

        return res.isEmpty() ? new ArrayList<>(List.of(0)) : res;
    }

    private boolean validateByCols() {
        for (int i = 0; i < width; i++) {
            List<Integer> col = getCol(i);
            if (!colHints.get(i).equals(getHints(col))) {
                return false;
            }
        }
        return true;
    }
}

