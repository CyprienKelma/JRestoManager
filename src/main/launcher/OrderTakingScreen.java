package main.launcher;

import java.util.*;

import main.place.*;
import main.staff.Employé;
import main.staff.Serveur;

//Classe qui gère l'écran de prise de commande du restaurant (1er écran)
public class OrderTakingScreen {

    OrderTakingScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui n'ont
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Fonction qui tente d'afficher l'écran de prise de commande
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du serveur
    // (showOrderTakingScreen)
    public static void tryShowingOrderTakingScreen(Scanner menuScanner) {
        if (!Restaurant.isOpen()) {
            clearConsole();
            print("==========================================================================\n");
            print("Accés refusé : Le restaurant n'est pas encore ouvert !\n");
            print("Pour prendre des commandes, votre manager doit d'abord créer une équipe, puis ouvrir le restaurant.\n");
            print("1 - Retour au menu principal\n");

            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                App.showMainMenu();
            } else {
                tryShowingOrderTakingScreen(menuScanner);
            }
        } else {
            showOrderTakingScreen(menuScanner);
        }
    }

    public static void showOrderTakingScreen(Scanner menuScanner) {
        print("==========================================================================\n");
        print("Quel serveur êtes-vous ?\n");
        print("1 - " + Restaurant.getEquipeActuelle().getServeur1().getNom() + " "
                + Restaurant.getEquipeActuelle().getServeur1().getPrenom());
        print("2 - " + Restaurant.getEquipeActuelle().getServeur2().getNom() + " "
                + Restaurant.getEquipeActuelle().getServeur2().getPrenom());
        print("\n3- Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Appelle la fonction de l'écran de prise de commande
                showOrderSelectionScreen(menuScanner, Restaurant.getEquipeActuelle().getServeur1());
                break;
            case "2":
                // Appelle la fonction de l'écran de prise de commande
                showOrderSelectionScreen(menuScanner, Restaurant.getEquipeActuelle().getServeur2());
                break;
            case "3":
                // Appelle la fonction de l'écran de prise de commande
                App.showMainMenu();
                break;
            default:
                showOrderTakingScreen(menuScanner);
                break;
        }
    }

    public static void showOrderSelectionScreen(Scanner menuScanner, Serveur whichWaiter) {

        clearConsole();
        print("==========================================================================");
        print("SELECTIONNER LA TRANSACTION :");
        print("--------------------------------------------------------------------------");
        print("0 - Ajouter une nouvelle transaction");
        print("--------------------------------------------------------------------------");

        // Affiche la liste des transactions en cours sous le format :
        // [Numéro de la table] : [Etat de la transaction]
        for (int i = 0; i < Restaurant.getTransactionsListSize(); i++) {
            print((i + 1) + " - " + Restaurant.getTransactionsList().get(i).getTable().getNuméro() + " : "
                    + Restaurant.getTransactionsList().get(i).getState().getDescription().toLowerCase());
        }

        print("\n\n");
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "0":
                // Appelle la fonction de l'écran de prise de commande
                addNewTransactionScreen(menuScanner, whichWaiter);
                break;
            default:
                // Appelle la fonction de l'écran de prise de commande
                showOrderSelectionScreen(menuScanner, whichWaiter);
                break;
        }

        // TODO : Ajouter fonction de traitement des transaction existantes

    }

    public static void addNewTransactionScreen(Scanner menuScanner, Serveur whichWaiter) {

        clearConsole();
        print("==========================================================================");
        print("AJOUTER UNE NOUVELLE TRANSACTION :");

        print("Combien de clients sont présents à la table (entre 1 et 8) ?");
        String nbrClientsStr = menuScanner.next();

        // La valeur convertie en int ci-dessous est utilisée pour l'affichage de la
        // fonction showTableConfirmationScreen (qui prend un int en paramètre)
        int nbrClientInt = Integer.parseInt(nbrClientsStr);

        // On vérifie que le nombre de client est bien compris entre 1 et 8 :
        if (Integer.parseInt(nbrClientsStr) < 1 || Integer.parseInt(nbrClientsStr) > 8) {
            clearConsole();
            print("==========================================================================");
            print("ERREUR : Le nombre de clients doit être compris entre 1 et 8 !\n");
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
        // ! choix du manager

        // On répertorie les tables disponibles :
        List<Table> availableTablesTmp = new ArrayList<>();

        // On vérifie pour toute les tables du restaurant...
        for (int i = 0; i < Restaurant.getTablesList().size(); i++) {

            // ... si la table est disponible (autrement dit non occupée) ...
            if (Restaurant.getTablesList().get(i).isDisponible()) {

                // ... et aussi s'il y a assez de couverts ou non :
                if (Restaurant.getTablesList().get(i).getNbrCouvert() >= Integer.parseInt(nbrClientsStr)) {
                    print((i + 1) + ") Table n°" + Restaurant.getTablesList().get(i).getNuméro() + " :"
                            + Restaurant.getTablesList().get(i).getNbrCouvert() + " couverts");

                    // Si oui, on l'ajoute à la liste temporaire des tables disponibles :
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

        // * TODO : Faire en sorte que l'on choisisse le numéro de table et pas le
        // numéro dans la liste

        print("--------------------------------------------------------------------------");
        print("Quelle table les clients ont choisis ?");
        print("Entrez le numéro de la liste (ex : 1 pour la première table affiché) :");

        String choixEcran = menuScanner.next();
        int indexTable = Integer.parseInt(choixEcran) - 1;

        // donne l'indice du numéro de table choisi dans la liste des tables disponibles

        // SelectedOne désigne la table d'indice choixEcran - 1
        // (puisque les indices des listes commencent à 0)
        // int nbrTableTmp = availableTablesTmp.get(Integer.parseInt(choixEcran) -
        // 1).getNbrCouvert();

        int newIndexTable = Table.numeroToIndex(availableTablesTmp, indexTable);
        Table selectedOne = availableTablesTmp.get(newIndexTable);

        // Redirige vers confirmation de table :
        showTableConfirmationScreen(menuScanner, whichWaiter, selectedOne, nbrClientInt);
    }

    public static void showTableConfirmationScreen(Scanner menuScanner, Serveur whichWaiter, Table selectedOne,
            int nbrClients) {

        // On instancie alors la nouvelle transaction :
        instanciateTransaction(menuScanner, whichWaiter, selectedOne, nbrClients);

        clearConsole();
        print("==========================================================================");
        print("CONFIRMATION DE LA TRANSACTION :");

        print("La table " + selectedOne.getNuméro() + " est maintenant occupée par "
                + nbrClients + " client(s).");

        print("--------------------------------------------------------------------------");

        print("1 - Retour à la selection des transactions");
        print("2 - Retour au menu principal");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                showOrderSelectionScreen(menuScanner, whichWaiter);
                break;
            case "2":
                App.showMainMenu();
                break;
            default:
                showTableConfirmationScreen(menuScanner, whichWaiter, selectedOne, nbrClients);
                break;
        }

    }

    // Fonction qui instancie une transaction (avec le nombre exact de client
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