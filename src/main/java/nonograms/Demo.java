package nonograms;

import nonograms.formatters.CmdNonogram;
import nonograms.formatters.NonogramFormatter;
import nonograms.readers.MyNonogramFormatReader;
import nonograms.readers.NonogramReader;

import java.io.File;
import java.io.IOException;

/**
 * Demo with a duck nonogram see duck.txt file
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        NonogramFormatter formatter = new CmdNonogram();
        NonogramReader reader = new MyNonogramFormatReader();
        Nonogram duck = reader.read(new File("duck.txt"));
        System.out.println(duck.getFormatted(formatter));
        System.out.println(duck.solve() ? "Solved the nonogram" : "Couldn't solve the nonogram");
        System.out.println(duck.getFormatted(formatter));
    }
}
