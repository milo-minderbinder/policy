package co.insecurity.policy.directive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public interface Dictionary {

    BufferedReader getReader() throws IOException;

    default int getNumWords() throws IOException {
        int numWords = 0;
        try (BufferedReader reader = getReader()) {
            while (reader.readLine() != null)
                numWords++;
        } catch (IOException e) {
            throw e;
        }
        return numWords;
    }

    static Dictionary fromResource(String resourceName) {
        return () -> {
            return new BufferedReader(new InputStreamReader(
                    Dictionary.class.getClassLoader().getResourceAsStream(resourceName)));
        };
    }

    static Dictionary fromFile(Path filePath, Charset charSet) {
        return () -> Files.newBufferedReader(filePath, charSet);
    }
}