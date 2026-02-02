package Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Methods {
    public static void unzipFile (String zipFile, String destFolder) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destFolder + File.separator + entry.getName());
                if (!entry.isDirectory()) {
                    new File(newFile.getParent());
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }

    public static void readFile(String fileName) {
        Path filePath = Paths.get("./");
        String[] extensions = {".csv", ".txt", ".xlsx"};
        for (int i = 0; i < 3; i++) {
            filePath = Paths.get("./Files/" + fileName + extensions[i]);
            if (Files.exists(filePath)) {
                i = 3;
            }
        }
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 0; i < lines.size(); i++) {
                String linha = lines.get(i);
                int test1 = linha.indexOf("Despesas");
                int test2 = linha.indexOf("DESPESAS");
                int test3 = linha.indexOf("despesas");
                if (test1 != -1) {
                    i = lines.size();
                } else if (test2 != -1) {
                    i = lines.size();
                } else if (test3 != -1) {
                    i = lines.size();
                } else if (i == lines.size()-1) {
                    Files.deleteIfExists(filePath);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed on read file: " + e.getMessage());
        }
    }
}

