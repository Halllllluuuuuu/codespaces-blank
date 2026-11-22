import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DateiAnalyse {

    private static File[] readEntries(File directory) throws IOException {
        if (!directory.isDirectory()) {
            throw new IOException(
                "Kein gültiges Verzeichnis: " + directory.getPath()
            );
        }

        File[] entries = directory.listFiles();

        if (entries == null) {
            throw new IOException(
                "Verzeichnis nicht lesbar: " + directory.getPath()
            );
        }

        return entries;
    }

    public static void listFiles(String directoryPath) throws IOException {
        for (File entry : readEntries(new File(directoryPath))) {
            if (!Files.isSymbolicLink(entry.toPath()) && entry.isFile()) {
                System.out.println(entry.getName());
            }
        }
    }

    public static long calculateDirectSize(String directoryPath)
            throws IOException {

        long totalSize = 0;

        for (File entry : readEntries(new File(directoryPath))) {
            if (!Files.isSymbolicLink(entry.toPath()) && entry.isFile()) {
                long size = entry.length();
                System.out.println(entry.getName() + ": " + size + " Bytes");
                totalSize += size;
            }
        }

        return totalSize;
    }

    public static long calculateTotalSize(File directory)
            throws IOException {

        long totalSize = 0;

        for (File entry : readEntries(directory)) {
            if (Files.isSymbolicLink(entry.toPath())) {
                continue;
            }

            if (entry.isDirectory()) {
                totalSize += calculateTotalSize(entry);
            } else if (entry.isFile()) {
                totalSize += entry.length();
            }
        }

        return totalSize;
    }

    public static void main(String[] args) {
        String directoryPath = args.length > 0 ? args[0] : "testdaten";

        try {
            System.out.println("Dateien direkt im Verzeichnis:");
            listFiles(directoryPath);

            System.out.println("\nEinzelgrößen:");
            long directSize = calculateDirectSize(directoryPath);
            System.out.println("Summe direkt: " + directSize + " Bytes");

            long totalSize = calculateTotalSize(new File(directoryPath));
            System.out.println(
                "Summe mit Unterverzeichnissen: " + totalSize + " Bytes"
            );
        } catch (IOException exception) {
            System.out.println("Fehler: " + exception.getMessage());
        }
    }
}