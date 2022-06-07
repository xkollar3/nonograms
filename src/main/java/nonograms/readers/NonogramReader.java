package nonograms.readers;

import nonograms.Nonogram;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface NonogramReader {
    Nonogram read(File file) throws IOException;
    Nonogram read(InputStream is) throws IOException;
}
