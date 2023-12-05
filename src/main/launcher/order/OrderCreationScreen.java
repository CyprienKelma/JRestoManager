package main.launcher.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.launcher.App;
import main.place.Restaurant;
import main.place.Table;
import main.place.Transaction;
import main.place.TransactionState;
import main.staff.Serveur;
import main.launcher.order.OrderTakingScreen;

// Classe qui gère tout l'écran de création d'une nouvelle transaction
public class OrderCreationScreen {

    private OrderCreationScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne contiennent
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Fonction qui affiche l'écran de création d'une nouvelle transaction
    public static void addNewTransactionScreen(Scanner menuScanner, Serveur whichWaiter) throws IOException {

        clearConsole();
        print("==========================================================================");
        print("AJOUTER UNE NOUVELLE TRANSACTION :");

        print("Combien de clients sont présents à la table (entre 1 et 8) ?");
        String nbrClientsStr = menuScanner.next();

        // La valeur convertie en int ci-dessous est utilisée pour l'affichage de la
        // fonction showTableConfirmationScreen (qui prend un int en paramètre)
        int nbrClientInt = Integer.parseInt(nbrClientsStr);

        // On vérifie que le nombre de client est bien compris entre 1 et 8
        // Si ce n'est pas le cas on propose de réessayer ou de retourner au menu
        if (Integer.parseInt(nbrClientsStr) < 1 || Integer.parseInt(nbrClientsStr) > 15) {
            clearConsole();
            print("==========================================================================");
            print("ERREUR : Le nombre de clients doit être compris entre 1 et 15 !\n");
            print("1 - Réessayer");
            print("2 - Retour au menu principal");

            String choixEcran = menuScanner.next();

            switch (choixEcran) {
                case "1":
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
                case "2":
                    App.showMainMenu();
                    break;
                default:
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
            }
        }

        clearConsole();
        print("==========================================================================");
        print("AJOUTER UNE NOUVELLE TRANSACTION :");

        print("Voici les tables disponibles pour " + nbrClientsStr + " client(s) :");
        print("Rappel : Pensez à ne pas proposer une table de 10 pour 3 clients !");
        print("--------------------------------------------------------------------------\n");

        // ! TODO : Il faudra changer la liste des tables disponibles en fonction du
        // ! choix du manager (voir écran monitoring)

        // On répertorie les tables disponibles :
        List<Table> availableTablesTmp = new ArrayList<>();

        // On vérifie pour toute les tables du restaurant...
        for (int i = 0; i < Restaurant.getTablesList().size(); i++) {

            // ... si la table est disponible (autrement dit non occupée) ...
            if (Restaurant.getTablesList().get(i).isDisponible()) {

                // ... et aussi s'il y a assez de couverts ou non :
                if (Restaurant.getTablesList().get(i).getNbrCouvert() >= Integer.parseInt(nbrClientsStr)) {

                    // Si oui, on l'affiche :
                    print((i + 1) + ") Table n°" + Restaurant.getTablesList().get(i).getNumero() + " :"
                            + Restaurant.getTablesList().get(i).getNbrCouvert() + " couverts");

                    // Et (si oui) on l'ajoute à la liste temporaire des tables disponibles :
                    availableTablesTmp.add(Restaurant.getTablesList().get(i));
                }
            }
        }

        // Si aucune table n'est disponible, on affiche un message d'erreur :
        if (availableTablesTmp.size() == 0) {
            clearConsole();
            print("==========================================================================");
            print("ERREUR : Aucune table n'est disponible pour " + nbrClientsStr + " client(s) !\n");
            print("Avant qu'il partent, n'oubliez pas de proposer au client :");
            print("     a) D'attendre que d'autres tables se libèrent.");
            print("     b) De proposer qu'ils mangent sur des tables séparés ...\n\n");

            print("1 - Réessayer");
            print("2 - Retour au menu principal");

            String choixEcran = menuScanner.next();

            switch (choixEcran) {
                case "1":
                    // Réaffiche l'écran d'ajout de nouvelle transaction
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
                case "2":
                    // Retourne au menu principal
                    App.showMainMenu();
                    break;
                default:
                    addNewTransactionScreen(menuScanner, whichWaiter);
                    break;
            }
        }

        // Si au moins une table est disponible, on demande au serveur de choisir la
        // table que les clients veulent
        print("--------------------------------------------------------------------------");
        print("Quelle table les clients ont choisis ?");
        print("Entrez le numéro de la table :\n");

        String choixEcran = menuScanner.next();
        int indexTable = Integer.parseInt(choixEcran);

        // Permet de récupérer le numéro de la table choisie par les clients
        // (qui est différent de l'index de la table dans la liste des tables
        // disponibles)
        int newIndexTable = Table.numeroToIndex(availableTablesTmp, indexTable);

        // selectedOne désigne la table choisie par les clients
        Table selectedOne = availableTablesTmp.get(newIndexTable);

        // Redirige vers la confirmation de table :
        showTableConfirmationScreen(menuScanner, whichWaiter, selectedOne, nbrClientInt);
    }


    // Fonction qui affiche l'écran de confirmation de la table choisie par les clients
    // et qui instancie la transaction
    public static void showTableConfirmationScreen(Scanner menuScanner, Serveur whichWaiter, Table selectedOne,
            int nbrClients) throws IOException {

        // On instancie alors la nouvelle transaction :
        instanciateTransaction(menuScanner, whichWaiter, selectedOne, nbrClients);

        clearConsole();
        print("==========================================================================");
        print("CONFIRMATION DE LA TRANSACTION :");

        print("La table " + selectedOne.getNumero() + " est maintenant occupée par "
                + nbrClients + " client(s).");

        print("--------------------------------------------------------------------------");

        print("1 - Retour à la selection des transactions");
        print("2 - Retour au menu principal");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                OrderTakingScreen.showOrderSelectionScreen(menuScanner, whichWaiter);
                break;
            case "2":
                App.showMainMenu();
                break;
            default:
                showTableConfirmationScreen(menuScanner, whichWaiter, selectedOne, nbrClients);
                break;
        }

    }

    // Fonction qui créer/instancie une transaction (avec le nombre exact de client
    // 'nbrClients')
    public static void instanciateTransaction(Scanner menuScanner, Serveur whichWaiter, Table selectedOne,
            int nbrClients) {

        // On instancie la transaction :
        // Serveur, nbrClients, table
        Transaction newTransaction = new Transaction(whichWaiter, nbrClients, selectedOne);

        // On ajoute la transaction à la liste des transactions du restaurant :
        Restaurant.getTransactionsList().add(newTransaction);

        // On change l'état de la table :
        selectedOne.setDisponible(false);

        // On change l'état de la transaction :
        newTransaction.setState(TransactionState.NOT_STARTED);
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}