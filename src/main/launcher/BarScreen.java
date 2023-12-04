package main.launcher;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.place.Restaurant;
import main.place.Transaction;
import main.place.TransactionState;

public class BarScreen {

    private BarScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent 
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }


    // Fonction qui tente d'afficher l'écran du barman
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du cuisinier
    // (showBarScreen)
    public static void tryShowingBarScreen(Scanner menuScanner) {
        if (!Restaurant.isOpen()) {
            App.clearConsole();
            print("==========================================================================\n");
            print("Accés refusé : Le restaurant n'est pas encore ouvert !\n");
            print("Pour préparer des boissons, votre manager doit d'abord créer une équipe, puis ouvrir le restaurant.\n");
            print("1 - Retour au menu principal\n");

            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                App.showMainMenu();
            } else {
                tryShowingBarScreen(menuScanner);
            }
        }
        showBarScreen(menuScanner);
    }

    public static void showBarScreen(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("Boissons à préparer :\n");

        print("Voici les boissons que vous devez préparer :\n");
        print("--------------------------------------------------------------------------");


        int indexCommand = 1;
        int indexBoisson = 1;

        List<Transaction> transactionsList = Restaurant.getTransactionsList();

        // Pour chacune des transactions en cours ...
        for(Transaction transaction : transactionsList) {

            // Si la commande est défini comme étant en cours de préparation
            if(transaction != null && transaction.getState() == TransactionState.PREPARING){

                // On affiche les plats qu'il faut préparer
                for(Map.Entry<String, Integer> boisson : transaction.getCommandeDemandé().getBoissons().entrySet()) {
                    if(boisson.getValue() > 0){
                        print(indexBoisson + ") Commande " + indexCommand + " : " + boisson.getValue() + " " + boisson.getKey() + " pour la table N°" + transaction.getTable().getNumero());
                        indexBoisson++;
                    }
                }
                indexCommand++;
            }
        }

        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu principal\n");
        print("Quand vous aurez fini de préparer une boisson, entrez son numéro pour la supprimer de la liste.\n\n");
        String choixEcran = menuScanner.next();
        int choixEcranInt = Integer.parseInt(choixEcran);

        if(choixEcranInt == 0){
            App.showMainMenu();
        } else {
            int indexCommand2 = 1;
            int indexBoisson2 = 1;

            // Pour chacune des transactions en cours ...
            for(Transaction transaction : transactionsList) {
                // Si la commande est défini comme étant en cours de préparation
                if(transaction != null && transaction.getState() == TransactionState.PREPARING){

                    // On affiche les boissons qu'il faut préparer
                    for(Map.Entry<String, Integer> boisson : transaction.getCommandeDemandé().getBoissons().entrySet()) {

                        if(boisson.getValue() > 0){
                            // Si la boisson sélectionnée correspond à la boisson affichée
                            if(indexBoisson2 == choixEcranInt){

                                changeCommandeQuantity(menuScanner, transaction, boisson);

                                showBarScreen(menuScanner);
                            }
                            indexBoisson2++;
                        }
                    }
                    indexCommand2++;
                }
            }
        }
    }

    // Fonction qui change la quantité de la boisson sélectionnée dans la commande sélectionnée et celle reçue
    public static void changeCommandeQuantity(Scanner menuScanner, Transaction transaction, Map.Entry<String, Integer> boissonActuel){

        // On enlève la boisson de la commande demandée et on l'ajoute à la commande reçue
        transaction.getCommandeDemandé().removeBoissons(boissonActuel.getKey(), 1);
        if(transaction.getCommandeDemandé().getBoissons().get(boissonActuel.getKey()) == 0){
            transaction.getCommandeDemandé().supprimerBoisson(boissonActuel.getKey());
        }
        
        transaction.getCommandeReçu().addBoissons(boissonActuel.getKey(), 1);

        // TODO : check debug transactionState
        print("debug : la transaction " + transaction + " a bien retiré la boisson " + boissonActuel.getKey() + " de la commande demandée");
        print("debug : la transaction " + transaction + " a bien ajouté la boisson " + boissonActuel.getKey() + " à la commande reçue");

        print("\ndebug : la commande demandé de boissons est maintenant : " + transaction.getCommandeDemandé().getBoissons());
        print("debug : la commande reçue est de boissons  maintenant : " + transaction.getCommandeReçu().getBoissons());

        print("\ndebug : la commande demandé de plats est maintenant : " + transaction.getCommandeDemandé().getPlats());
        print("debug : la commande reçue est de plats est maintenant : " + transaction.getCommandeReçu().getPlats());
        
        // On vérifie si la commande est vide
        TransactionState.checkIfCommandReady(transaction);

        // Puis à rappelle la fonction d'écran du barman pour continuer de préparer les autres boissons
        showBarScreen(menuScanner);
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }   
}