package main.launcher.order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.launcher.monitoring.MonitoringScreen;

public class BillsManagement {

    private static final String FILE_PATH = "src\\main\\data\\factures.txt";
    
    public static void sauvegardeFacture(String billDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int billId = getNextBillId();
            String formattedBillDetails = String.format("ID: %d\n%s", billId, billDetails);

            // Append the formatted bill details to the file
            writer.write(formattedBillDetails);
            writer.newLine();
        } catch (IOException e) {
            gereErreur("Erreur lors de la sauvegarde du ticket de caisse dans le fichier 'factures.txt'", e);
        }
    }

    private static int nextBillId = 3;
    public static int getLastBillId() {
        return nextBillId - 1;
    }
    private static int getNextBillId() {
        return nextBillId++;
    }


    // Méthode pour gérer les erreurs d'entrée/sortie
    private static void gereErreur(String message, IOException e) {
        System.err.println(message);
        e.printStackTrace();
    }

    public static void afficherTousLesTickets(Scanner menuScanner) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            print("============================================================================");
            print("1 - Continuer et retourner Ecran Monitoring\n");
        
            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                MonitoringScreen.showMonitoringScreen(menuScanner);
            } else {
                afficherTousLesTickets(menuScanner);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture des tickets de caisse dans le fichier 'factures.txt'");
            e.printStackTrace();
        }
    }

    public static void retirerBill(int billIdToRemove, Scanner menuScanner) {
        try {
            // Utiliser la fonction countLinesBetweenIDs pour déterminer le nombre de lignes à retirer
            int linesToRemove = countLinesBetweenIDAndParCB(FILE_PATH, billIdToRemove);            
    
            // Liste pour stocker toutes les lignes du fichier
            List<String> billLines = new ArrayList<>();
    
            // Lire toutes les lignes du fichier
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    billLines.add(line);
                }
            }
    
            // Créer une nouvelle liste excluant les lignes à retirer
            List<String> updatedBillLines = new ArrayList<>();
            boolean removeLines = false;  // Indique si les lignes actuelles doivent être exclues
            for (String line : billLines) {
                if (line.startsWith("ID: " + billIdToRemove)) {
                    removeLines = true;  // Dès que l'on trouve la facture à retirer, les lignes suivantes doivent être exclues
                }
                if (!removeLines) {
                    updatedBillLines.add(line);
                }
                if (removeLines && line.startsWith("Par CB : ")) {
                    
                    removeLines = false;  // Arrête d'exclure les lignes lorsqu'on atteint la ligne "Par CB :"
                }

            }
    
            // Écrire les lignes mises à jour dans le fichier
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String line : updatedBillLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
    
            System.out.println("\nTicket avec l'ID " + billIdToRemove + " supprimé avec succès.");
    
            // Décrémenter les IDs de factures avec un ID inférieur à celui de la facture retirée
            decrementerIdsInférieurs(billIdToRemove);
    
            // Décrémenter les IDs des factures suivantes
            decrementerIdsSuivants(billIdToRemove, linesToRemove);
            getLastBillId();
    
            MonitoringScreen.showMonitoringScreen(menuScanner);
    
        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression du ticket de caisse dans le fichier 'factures.txt'");
            e.printStackTrace();
        }
    }
    
    
    // Fonction pour décrémenter les IDs inférieurs
    private static void decrementerIdsInférieurs(int removedId) {
        try {
            // Liste pour stocker toutes les lignes du fichier après décrémenter les IDs
            List<String> updatedBillLines = new ArrayList<>();
    
            // Lire toutes les lignes du fichier après la suppression
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("ID: ")) {
                        int currentId = Integer.parseInt(line.substring(4).trim());
                        if (currentId > removedId) {
                            // Décrémenter l'ID
                            line = "ID: " + (currentId - 1);
                        }
                    }
                    updatedBillLines.add(line);
                }
            }
    
            // Écrire les lignes mises à jour dans le fichier
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String line : updatedBillLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
    
            System.out.println("IDs des factures décrémentés avec succès.");
    
        } catch (IOException e) {
            System.err.println("Erreur lors de la mise à jour des IDs dans le fichier 'factures.txt'");
            e.printStackTrace();
        }
    }
    
    
    // Fonction pour compter les lignes entre l'ID spécifié et la ligne "Par CB : " inclusivement
    private static int countLinesBetweenIDAndParCB(String filePath, int startId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int linesCount = 0;
            boolean counting = false;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: " + startId)) {
                    counting = true;
                }

                if (counting) {
                    linesCount++;

                    if (line.startsWith("Par CB : ")) {
                        // Arrête de compter lorsque la ligne "Par CB : " est atteinte
                        break;
                    }
                }
            }

            return linesCount;
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + filePath);
            e.printStackTrace();
            return 0;
        }
    }


    private static void decrementerIdsSuivants(int startId, int linesToDecrement) {
        try {
            // Liste pour stocker toutes les lignes du fichier après décrémenter les IDs
            List<String> updatedBillLines = new ArrayList<>();
    
            // Lire toutes les lignes du fichier après la suppression
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("ID: ")) {
                        int currentId = Integer.parseInt(line.substring(4).trim());
                        if (currentId > startId) {
                            // Décrémenter l'ID
                            line = "ID: " + (currentId - linesToDecrement);
                        }
                    }
                    updatedBillLines.add(line);
                }
            }
    
            // Écrire les lignes mises à jour dans le fichier
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String line : updatedBillLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
    
            System.out.println("IDs des factures suivantes décrémentés avec succès.\n");
    
        } catch (IOException e) {
            System.err.println("Erreur lors de la mise à jour des IDs dans le fichier 'factures.txt'");
            e.printStackTrace();
        }
    }
    



    public static void print(String text) {
        System.out.println(text);
    }
    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            print("\n");
        }
    }

}
