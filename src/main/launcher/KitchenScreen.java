package main.launcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.place.Restaurant;
import main.place.Transaction;
import main.place.TransactionState;
import main.staff.Cuisinier;

// Classe qui gère l'écran des cuisiniers (2)
public class KitchenScreen {

    KitchenScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires qui ne
        // contiennent
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Fonction qui tente d'afficher l'écran des cuisiniers
    // - Si l'équipe n'est pas encore créée, indique qu'il faut d'abord en créer une
    // - Si l'équipe est créée, affiche l'écran de selection du cuisinier
    // (showKitchenScreen)
    public static void tryShowingKitchenScreen(Scanner menuScanner) throws IOException {
        if (!Restaurant.isOpen()) {
            App.clearConsole();
            print("==========================================================================\n");
            print("Accés refusé : Le restaurant n'est pas encore ouvert !\n");
            print("Pour préparer des plats, votre manager doit d'abord créer une équipe, puis ouvrir le restaurant.\n");
            print("1 - Retour au menu principal\n");

            String choixEcran = menuScanner.next();

            if (choixEcran.equals("1")) {
                App.showMainMenu();
            } else {
                tryShowingKitchenScreen(menuScanner);
            }
        }
        showKitchenScreen(menuScanner);
    }

    public static void showKitchenScreen(Scanner menuScanner) throws IOException {
        App.clearConsole(); // Pour simuler un écran
        print("==========================================================================\n");
        print("Quel cuisinier êtes-vous ?\n");

        print("1 - " + Restaurant.getEquipeActuelle().getCuisinier1().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier1().getPrenom());

        print("2 - " + Restaurant.getEquipeActuelle().getCuisinier2().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier2().getPrenom());

        print("3 - " + Restaurant.getEquipeActuelle().getCuisinier3().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier3().getPrenom());

        print("4 - " + Restaurant.getEquipeActuelle().getCuisinier4().getNom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier4().getPrenom());

        print("\n5- Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                // Appelle la fonction de l'écran de prise de commande
                showCookToDo(menuScanner, Restaurant.getEquipeActuelle().getCuisinier1());
                break;
            case "2":
                // Appelle la fonction de l'écran de prise de commande
                showCookToDo(menuScanner, Restaurant.getEquipeActuelle().getCuisinier2());
                break;
            case "3":
                // Appelle la fonction de l'écran de prise de commande
                showCookToDo(menuScanner, Restaurant.getEquipeActuelle().getCuisinier3());
                break;
            case "4":
                // Appelle la fonction de l'écran de prise de commande
                showCookToDo(menuScanner, Restaurant.getEquipeActuelle().getCuisinier4());
                break;
            case "5":
                App.showMainMenu();
                break;
            default:
                showKitchenScreen(menuScanner);
                break;
        }
    }

    public static void showCookToDo(Scanner menuScanner, Cuisinier whichCuisinier) throws IOException {

        clearConsole();
        print("==========================================================================\n");
        print("Repas à préparer :\n");
    
        print("Selectionner votre prochaine préparation parmis les repas suivant :\n");
        print("--------------------------------------------------------------------------");
    
        int indexCommand = 1;
        int indexPlat = 1;
    
        List<Transaction> transactionsList = Restaurant.getTransactionsList();
    
        // Pour chacune des transactions en cours ...
        for (Transaction transaction : transactionsList) {
    
            // Si la commande est défini comme étant en cours de préparation
            if (transaction != null && transaction.getState() == TransactionState.PREPARING) {
    
                // On affiche les plats qu'il faut préparer
                for (Map.Entry<String, Integer> plat : transaction.getCommandeDemandé().getPlats().entrySet()) {
                    if (plat.getValue() > 0) {
                        print(indexPlat + " - Commande " + indexCommand + " : " + plat.getValue() + " " + plat.getKey() + " pour la table N°"
                                + transaction.getTable().getNumero());
                        indexPlat++;
                    }
                }
                indexCommand++;
            }
        }
    
        print("--------------------------------------------------------------------------\n");
        print(indexPlat + " - Page précédente");
        print("0 - Retour au menu principal\n");
        print("Quand vous aurez fini de préparer un plat, entrez son numéro pour le supprimer de la liste.\n\n");

        String choixEcran = menuScanner.next();
        int choixEcranInt = Integer.parseInt(choixEcran);

        if (choixEcranInt == 0) {
            App.showMainMenu();
        } else if (choixEcranInt == indexPlat) {
            // Return to the previous page
            showKitchenScreen(menuScanner);
        } else {
            int indexPlat2 = 1;

            // Pour chacune des transactions en cours ...
            for (Transaction transaction : transactionsList) {
                // Si la commande est défini comme étant en cours de préparation
                if (transaction != null && transaction.getState() == TransactionState.PREPARING) {

                    // On affiche les plats qu'il faut préparer
                    for (Map.Entry<String, Integer> plat : transaction.getCommandeDemandé().getPlats().entrySet()) {

                        if (plat.getValue() > 0) {
                            // Si le plat sélectionné correspond au plat affiché
                            if (indexPlat2 == choixEcranInt) {

                                changeCommandeQuantity(menuScanner, transaction, plat);

                                showCookToDo(menuScanner, whichCuisinier);
                            }
                            indexPlat2++;
                        }
                    }
                }
            }
        }
    }
    

    // Fonction qui change la quantité de la boisson sélectionnée dans la commande sélectionnée et celle reçue
    public static void changeCommandeQuantity(Scanner menuScanner, Transaction transaction, Map.Entry<String, Integer> platActuel) throws IOException{

        // On enlève la boisson de la commande demandée et on l'ajoute à la commande reçue
        transaction.getCommandeDemandé().removePlats(platActuel.getKey(), 1);
        if(transaction.getCommandeDemandé().getPlats().get(platActuel.getKey()) <= 0){
            // Si la quantité de la boisson est à 0 (ou moins...), on la supprime de la commande
            // Comma ça on passe de {boisson, 0} à {} et la, la commande est vraiment vide
            transaction.getCommandeDemandé().supprimerPlat(platActuel.getKey());
        }

        transaction.getCommandeReçu().addPlats(platActuel.getKey(), 1);

        // TODO : check debug transactionState
        print("debug : la transaction " + transaction + " a bien retiré la boisson " + platActuel.getKey() + " de la commande demandée");
        print("debug : la transaction " + transaction + " a bien ajouté la boisson " + platActuel.getKey() + " à la commande reçue");

        print("\ndebug : la commande demandé de boissons est maintenant : " + transaction.getCommandeDemandé().getPlats());
        print("debug : la commande reçue est de boissons  maintenant : " + transaction.getCommandeReçu().getPlats());

        print("\ndebug : la commande demandé de plats est maintenant : " + transaction.getCommandeDemandé().getPlats());
        print("debug : la commande reçue est de plats est maintenant : " + transaction.getCommandeReçu().getPlats());
        
        // On vérifie si la commande est vide
        TransactionState.checkIfCommandReady(transaction);

    }

    public static void cookingProcessScreen(Scanner menuScanner, Cuisinier whichCuisinier, Transaction whichTransaction,
            String whichPlat) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("Repas actuel à préparer :\n");

        print("A vous de jouez ! Lorsque vous aurez fini votre préparation, vous pourrez");
        print("indiquer que le plat est prêt en appuyant sur 1.\n");
        print("--------------------------------------------------------------------------\n");
        print("Plat à préparer : " + whichPlat + " pour la table N°" + whichTransaction.getTable().getNumero() + "\n");
        print("--------------------------------------------------------------------------\n");
        print("1 - Indiquer que le plat est préparé\n\n");

        String choixEcran = menuScanner.next();

        if (choixEcran.equals("1")) {

            // Retire le plat fini de la commande demandé
            // Et ajoute le plat fini dans la commande reçu
            // Ainsi lorsque la commande demandé sera vide (plats ET boissons)
            // alors cela signifiera que la commande est fini
            removePlatFromTransactionsList(whichTransaction, whichPlat);

            // On revient dans la liste des plats à préparer pour continuer de travailler
            showCookToDo(menuScanner, whichCuisinier);
        } else {
            cookingProcessScreen(menuScanner, whichCuisinier, whichTransaction, whichPlat);
        }

    }

    // Fonction pour retirer le plat préparé de la liste des plats à préparer dans
    // la liste générale des transactions
    private static void removePlatFromTransactionsList(Transaction transaction, String plat) {

        // Ajoute le plat fini dans la commande reçu
        transaction.getCommandeReçu().addPlats(plat, 1);

        // Test : System.out.println("Plats à préparer avant retrait : " +
        // transaction.getCommandeDemandé().getPlats());

        // Retire le plat de la commande demandée de la transaction actuelle
        transaction.getCommandeDemandé().supprimerPlat(plat);

        // TODO : check debug kitchenScreen.removePlatFromTransactionsList
        print("debug : le plat " + plat + " a bien été retiré de la commande demandée de la transaction "
                + transaction);
        print("\ndebug : la commande demandée de la transaction " + transaction + " est maintenant : "
                + transaction.getCommandeDemandé().getPlats());

        print("debug : le plat " + plat + " a bien été ajouté à la commande reçue de la transaction " + transaction);
        print("\ndebug : la commande reçue de la transaction " + transaction + " est maintenant : "
                + transaction.getCommandeReçu().getPlats() + "\n");

        // On vérifie si la commande est vide
        TransactionState.checkIfCommandReady(transaction);
        print("debug : la transaction " + transaction + " est maintenant à l'état " + transaction.getState() + "\n");
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}