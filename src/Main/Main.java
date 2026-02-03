package Main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //A seguinte variável será utilizada para identificar a extensão de um determinado arquivo.
        String[] extensions = {".csv", ".txt", ".xlsx"};

        //Caminho para os arquivos, antes contidos no zip.
        Path filePath = Paths.get("./");

        //Try-with-catch para acessar a API e baixar os arquivos zip.
        try {
            for (int i = 1; i <= 3; i++) {
                @SuppressWarnings("deprecation") URL url = new URL("https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2025/" + i + "T2025.zip");
                String savePath = "./" + i + "T2025/";

                try (InputStream inputStream = url.openStream()) {
                    Path pathDestination = Paths.get(savePath);
                    Files.copy(inputStream, pathDestination, StandardCopyOption.REPLACE_EXISTING);
                }
                System.out.println("File download successfully to: " + savePath);
                //Método para extrair arquivos do zip e armazenar na pasta Files.
                Methods.unzipFile(i + "T2025", "./Files");

                //Teste de qual extensão é o arquivo que foi extraído.
                for (int j = 0; j < 3; j++) {
                    filePath = Paths.get("./Files/" + i + "T2025" + extensions[j]);
                    if (Files.exists(filePath)) {
                        j = 3;
                    }
                }
                //Método para ler os arquivos e verificar se eles possuem dados de despesa.
                Methods.readFile(filePath);

            }
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File download failed: " + e.getMessage());
        }

        List<String> lines;
        Path newFilePath = Paths.get("./Files/consolidado_despesas.cv");
        List<String> dadosConsolidados = new java.util.ArrayList<>(List.of());
        System.out.println("Reading files...");
        //Try-with-catch para juntar todos os arquivos em um único arquivo CSV na pasta Files.
        try {
            for (int i = 1; i <= 3; i++) {
                for (int j = 0; j < 3; j++) {
                    filePath = Paths.get("./Files/" + i + "T2025" + extensions[j]);
                    if (Files.exists(filePath)) {
                        j = 3;
                    }
                }
                lines = Files.readAllLines(filePath);
                dadosConsolidados.addAll(lines);
                Files.write(newFilePath, dadosConsolidados, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            System.err.println("It wasn't possible to create the file: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Run time error: " + e.getMessage());
        }
    }
}