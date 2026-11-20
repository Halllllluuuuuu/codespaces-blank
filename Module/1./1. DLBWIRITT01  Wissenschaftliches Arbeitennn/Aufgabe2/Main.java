import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void writeToFile(String text, String fileName)
            throws IOException {

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(text);
        }
    }

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Text eingeben: ");
            String text = scanner.nextLine();

            System.out.print("Dateiname eingeben: ");
            String fileName = scanner.nextLine();

            try {
                writeToFile(text, fileName);
                System.out.println("Text erfolgreich gespeichert.");
            } catch (IOException exception) {
                System.out.println(
                    "Fehler beim Speichern: " + exception.getMessage()
                );
            }
        }
    }
}