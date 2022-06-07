package nonograms.readers;

import nonograms.Nonogram;
import nonograms.NonogramImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyNonogramFormatReader implements NonogramReader {
    @Override
    public Nonogram read(File file) throws IOException {
        return read(new FileInputStream(file));
    }

    @Override
    public Nonogram read(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<List<Integer>> rowHints = new ArrayList<>();
        List<List<Integer>> colHints = new ArrayList<>();
        String[] hintsRows = br.readLine().split(" ");
        String[] hintsCols = br.readLine().split(" ");
        fill(hintsRows, rowHints);
        fill(hintsCols, colHints);
        return new NonogramImpl(hintsRows.length, hintsCols.length, rowHints, colHints);
    }

    private void fill(String[] tokens, List<List<Integer>> container) {
        for (String token: tokens
             ) {
            List<Integer> hint = Arrays.stream(token.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            container.add(hint);
        }
    }
}
