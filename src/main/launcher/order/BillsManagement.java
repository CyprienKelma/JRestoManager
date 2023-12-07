package main.launcher.order;

import java.io.*;
import java.util.Map;

import main.carte.Carte;
import main.place.Transaction;

public class BillsManagement {

    private static final String FILE_PATH = "src\\main\\data\\factures.txt";

    // Méthode pour sauvegarder un ticket de caisse dans le fichier
    public static void sauvegardeFacture(String serverName, int tableNumber, 
        Map.Entry<String, Integer> platEntry, Map.Entry<String, Integer> boissonEntry, 
        double total, int nbrfacture, Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int billId = BillId.getNextBillId();

            // Construit le texte du ticket de caisse formaté
            String formattedBillText = formatFacture(billId, serverName, tableNumber, platEntry, boissonEntry, total, nbrfacture, transaction);

            writer.write(formattedBillText);
            writer.newLine();
        } catch (IOException e) {
            gereErreur("Erreur lors de la sauvegarde du ticket de caisse dans le fichier 'factures.txt'", e);
        }
    }



    // Méthode pour gérer les erreurs d'entrée/sortie
    private static void gereErreur(String message, IOException e) {
        System.err.println(message);
        e.printStackTrace();
    }

    private static String formatFacture(int billId, String serverName, int tableNumber, 
                                        Map.Entry<String, Integer> platEntry, 
                                        Map.Entry<String, Integer> boissonEntry, 
                                        double total, int nbrfacture, Transaction transaction) {
        // Construit le texte du ticket de caisse formaté
        StringBuilder formattedBillText = new StringBuilder();
        formattedBillText.append(billId).append(",").append(serverName).append(",").append(tableNumber).append(",").append(nbrfacture).append("\n");

        // Ajoutez la liste des plats avec quantité et prix
        for (Map.Entry<String, Integer> plat : transaction.getCommandeReçu().getPlats().entrySet()) {
            formattedBillText.append(plat.getValue()).append("x ").append(plat.getKey()).append(" - ").append(Carte.getPrixPlat(plat.getKey()) * plat.getValue()).append("\n");
        }

        // Ajoutez la liste des boissons avec quantité et prix
        for (Map.Entry<String, Integer> boisson : transaction.getCommandeReçu().getBoissons().entrySet()) {
            formattedBillText.append(boisson.getValue()).append("x ").append(boisson.getKey()).append(" - ").append(Carte.getPrixBoisson(boisson.getKey()) * boisson.getValue()).append("\n");
        }

        formattedBillText.append(total).append("\n\n");
        return formattedBillText.toString();
    }


}
