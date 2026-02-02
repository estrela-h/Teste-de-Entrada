import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        try {
            for (int i = 1; i <= 3; i++) {
                @SuppressWarnings("deprecation") URL url = new URL("https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2025/" + i + "T2025.zip");
                String savePath = "./" + i + "T2025/";

                try (InputStream inputStream = url.openStream()) {
                    Path pathDestination = Paths.get(savePath);
                    Files.copy(inputStream, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                }
                System.out.println("File download successfully to: " + savePath);
            }
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File download failed: " + e.getMessage());
        }
    }
}