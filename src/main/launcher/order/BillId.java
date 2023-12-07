package main.launcher.order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BillId {

    private static final String FILE_PATH = "src\\main\\data\\factures.txt";

    // Méthode pour obtenir le prochain ID de facture
    public static int getNextBillId() {
        int lastBillId = getLastBillId();
        return lastBillId + 1;
    }

    // Méthode pour obtenir le dernier ID de facture enregistré
    private static int getLastBillId() {
        int lastBillId = 0;

        try {
            File file = new File(FILE_PATH);
            Scanner scanner = new Scanner(file);

            // Lire chaque ligne du fichier
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Ignorer les lignes vides
                if (line.isEmpty()) {
                    continue;
                }

                // Séparer les éléments de la ligne
                String[] elements = line.split(",");

                // Si la ligne contient des éléments et le premier élément est un nombre, mettez à jour lastBillId
                if (elements.length > 0 && elements[0].matches("\\d+")) {
                    lastBillId = Integer.parseInt(elements[0]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lastBillId;
    }
}
