package ScannerFiles;

import java.nio.charset.Charset;
import java.io.*;
import java.util.*;

public class MyScanner implements AutoCloseable {
    private Reader in;
    private final Checker checker;
    private int cur;

    public MyScanner(String s, Checker checker) throws IOException {
        this.checker = checker;
        in = new BufferedReader(new StringReader(s));
        nextChar();
    }

    public MyScanner(InputStream inputStream, Checker checker, Charset charset) throws IOException {
        this.checker = checker;
        in = new BufferedReader(new InputStreamReader(inputStream, charset));
        nextChar();
    }

    public MyScanner(File file, Checker checker, Charset charset) throws FileNotFoundException, IOException {
        this.checker = checker;
        in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        nextChar();
    }

    public boolean hasNextChar() {
        return cur != -1;
    }

    public char nextChar() throws IOException {
        char ans = (char) cur;
        cur = in.read();
        return ans;
    }

    public void skipWhitespaces() throws IOException {
        while (hasNextChar() && checker.isWhitespace((char) cur)) {
            nextChar();
        }
    }

    public boolean hasNext() throws IOException {
        skipWhitespaces();
        return hasNextChar();
    }

    public String next() throws IOException {
        skipWhitespaces();
        StringBuilder builder = new StringBuilder();
        while (hasNextChar() && !checker.isWhitespace((char) cur)) {
            builder.append(nextChar());
        }
        return builder.toString();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public boolean isEOL() throws IOException {
        while (hasNextChar() && cur != '\n' && cur != '\r' && checker.isWhitespace((char) cur)) {
            nextChar();
        }
        return !hasNextChar() || cur == '\n' || cur == '\r';
    }

    public void skipEOL() throws IOException {
        if (cur == '\r') {
            nextChar();
        }
        if (cur == '\n') {
            nextChar();
        }
    }

    public void close() throws IOException {
        in.close();
    }
}
