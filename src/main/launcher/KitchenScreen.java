package main.launcher;

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
    public static void tryShowingKitchenScreen(Scanner menuScanner) {
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

    public static void showKitchenScreen(Scanner menuScanner) {
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

    public static void showCookToDo(Scanner menuScanner, Cuisinier whichCuisinier){

        clearConsole();
        print("==========================================================================\n");
        print("Repas à préparer :\n");

        print("Selectionner votre prochaine préparation parmis les repas suivant :\n");
        print("--------------------------------------------------------------------------");

        int indexCommand = 1;

        // Pour chacune des transactions en cours ...
        for(Transaction transaction : Restaurant.getTransactionsList()) {

            // Si la commande est défini comme étant en cours de préparation
            if(transaction != null && transaction.getState() == TransactionState.PREPARING){

                // On affiche les plats qu'il faut préparer
                for(Map.Entry<String, Integer> plat : transaction.getCommandeDemandé().getPlats().entrySet()) {
                    print("- Commande " + indexCommand + " : " + plat.getKey() + " pour la table N°" + transaction.getTable().getNumero());
                }
                indexCommand++;
            }
        }
        
        print("--------------------------------------------------------------------------\n");
        print("0 - Retour au menu principal\n");
        print("Entrez le numéro de la commande que vous souhaitez préparer :\n\n");

        String choixEcran = menuScanner.next();
        int choixEcranInt = Integer.parseInt(choixEcran);

        if(choixEcranInt == 0){
            App.showMainMenu();
        }
        else if(choixEcranInt > 0 && choixEcranInt < indexCommand){
            // Si l'on récupère bien un plat à préparer (au lieu de revenir au menu principal) :

            // Alors on récupère la transaction correspondante (grâce à la fonction getSelectedTransaction)
            Transaction selectedTransaction = getSelectedTransaction(choixEcranInt);

            // Et on récupère le premier plat de la commande (vous pouvez ajuster cela selon vos besoins)
            String firstPlat = selectedTransaction.getCommandeDemandé().getPlats().keySet().iterator().next();

            // On appele la fonction cookingProcessScreen
            cookingProcessScreen(menuScanner, whichCuisinier, selectedTransaction, firstPlat);
        } else{
            showCookToDo(menuScanner, whichCuisinier);
        }
    }

    // Fonction qui renvoie simplement la transaction correspondant à l'index
    private static Transaction getSelectedTransaction(int index) {
        int currentIndex = 1;
        for (Transaction transaction : Restaurant.getTransactionsList()) {
            if (transaction.getState() == TransactionState.PREPARING) {
                if (currentIndex == index) {
                    return transaction;
                }
                currentIndex++;
            }
        }
        return null; // Dans le cas (d'erreur) ou aucune transaction n'est trouvée
    }

    public static void cookingProcessScreen(Scanner menuScanner, Cuisinier whichCuisinier, Transaction whichTransaction, String whichPlat) {
        clearConsole();
        print("==========================================================================\n");
        print("Repas actuel à préparer :\n");

        print("A vous de jouez ! Lorsque vous aurez fini votre préparation, vous pourrez");
        print("indiquer que le plat est prêt en appuyant sur 1.\n");
        print("--------------------------------------------------------------------------\n");
        print("Plat à préparer : " + whichPlat + "pour la table N°" + whichTransaction.getTable().getNumero() + "\n");
        print("--------------------------------------------------------------------------\n");
        print("1 - Indiquer que le plat est préparé\n\n");

        String choixEcran = menuScanner.next();

        if(choixEcran.equals("1")){

            // Ajoute le plat fini dans la commande reçu
            whichTransaction.getCommandeReçu().addPlats(whichPlat, 1);
            // Retire le plat fini de la commande demandé
            // Ainsi lorsque la commande demandé sera vide (plats ET boissons)
            // alors cela signifiera que la commande est fini
            whichTransaction.getCommandeDemandé().removePlats(whichPlat, 1);

            // On revient dans la liste des plats à préparer pour continuer de travailler
            showCookToDo(menuScanner, whichCuisinier);
        }
        else{
            cookingProcessScreen(menuScanner, whichCuisinier, whichTransaction, whichPlat);
        }

    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}