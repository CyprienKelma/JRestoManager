package main.launcher.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.launcher.App;
import main.place.Restaurant;
import main.staff.*;

public class TeamScreen {

    // Le but est selectionner des employés de chaque type pour former l'équipe
    // On va donc les récupérer dans la liste de tout les employés (=>
    // Restaurant.getEmployésList())
    // et les ajouter dans des variables temporaires.
    // Une fois que l'équipe est formée, on va créer une instance de la classe
    // Equipe avec ces variables

    // On a donc :
    private static Serveur serveur1Tmp = null;
    private static Serveur serveur2Tmp = null;
    private static Cuisinier cuisinier1Tmp = null;
    private static Cuisinier cuisinier2Tmp = null;
    private static Cuisinier cuisinier3Tmp = null;
    private static Cuisinier cuisinier4Tmp = null;
    private static Barman barmanTmp = null;
    private static Manager managerTmp = null;

    // TODO : utiliser une HashMap pour stocker les employés sélectionnés plutôt que
    // des variables temporaires

    private TeamScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires. Elles n'ont
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Ecran : Programmer les employés pour la soirée (i.e. former l'équipe)
    public static void showTeamFormationScreen(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("FORMER L'EQUIPE DE CE SOIR :\n");

        print("Avant de pouvoir ouvrir le restaurant, vous devez former l'équipe de ce soir.\n");
        print("Pour former cette équipe, vous devez choisir les employés qui travailleront");
        print("durant tout ce service.\n");

        print("1 - Choisir les serveurs");
        print("2 - Choisir les cuisiniers");
        print("3 - Choisir le barman");
        print("4 - Choisir le manager\n");
        print("5 - Confirmer la selection et créer l'équipe");
        print("\n6 - Page précédente");
        print("7 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                showSelectServer(menuScanner);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case 7:
                App.showMainMenu();
                break;
            default:

        }
    }

    // Ecran pour choisir les 2 serveurs
    public static void showSelectServer(Scanner menuScanner) {
        clearConsole();
        print("==========================================================================\n");
        print("CHOISIR LES SERVEURS :\n");

        print("Vous devez choisir 2 serveurs qui travailleront durant tout ce service.\n");

        print("1 - Serveur 1 : A SELECTIONNER");
        print("2 - Serveur 2 : A SELECTIONNER");
        print("\n3 - Page précédente");
        print("4 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                // Redirige vers la fonction qui affiche la liste des serveurs
                showEmployeeListByType(menuScanner, "Serveur", 1);
                break;
            case 2:
                // Même chose pour le deuxième serveur à selectionner
                showEmployeeListByType(menuScanner, "Serveur", 2);
                break;
            case 3:
                // Page précédente
                showTeamFormationScreen(menuScanner);
                break;
            case 4:
                // Retour au menu principal
                App.showMainMenu();
                break;
            default:
                // Si le choix n'est pas valide, on relance la fonction
                showSelectServer(menuScanner);

        }
    }

    // Cette fonction affiche la liste des employés du type voulu. Comme ça,
    // on peut choisir lequel on ajoute à l'équipe parmis ceux du type voulu
    public static void showEmployeeListByType(Scanner menuScanner, String type, int whichOne) {

        // On récupère la liste de tout les employés à chaque appele de cette fonction
        // pour être sûr d'avoir la liste à jour
        List<Employé> createEmployésList = Restaurant.getEmployésList();
        List<Employé> tmpList = new ArrayList<>();

        clearConsole();
        print("==========================================================================\n");
        if (whichOne == 1) {
            print("Selectionner un premier " + type + "à ajouter parmis la liste :");
        } else {
            print("Selectionner un " + whichOne + "ème " + type + "à ajouter parmis la liste :");
        }

        // On affiche la liste des employés du type qui nous concerne
        // Par exemple n'afficher que les serveurs quand on selectionne nos 2 serveurs
        for (int i = 0; i < createEmployésList.size(); i++) {

            // Puisque la liste va être filtrée (on ne veut que les serveurs par exemple)
            // On va utiliser une variable j pour afficher les numéros des employés
            int j = 0;

            Employé employé = createEmployésList.get(i);

            // On affiche que les employés du type qui nous concerne (i.e. du paramètre
            // 'type')
            if (employé.getClass().getSimpleName().equals(type)) {

                // On affiche l'employé seulement si il n'est pas déjà sélectionné :
                if (!(serveur1Tmp == employé || serveur2Tmp == employé)) {
                    // Vérifier que l'employé n'est pas déja dans l'équipe :
                    print((j + 1) + ") " + type + " : " + employé.getNom() + ", " + employé.getPrenom() + ", "
                            + employé.getSalaire() + " euros/h net.");

                    tmpList.add(employé);

                    j++; // On incrémente j seulement si on affiche l'employé
                    // i.e. s'il est du type qui nous concerne ET qu'il n'est pas déjà sélectionné
                }
            }
        }

        print("\n--------------------------------------------------------------------------");
        print("Employé numéro :\n");
        int input = menuScanner.nextInt();

        // SelectedOne désigne l'employé d'indice j (où j = input - 1)
        Employé selectedOne = tmpList.get(input - 1);

        // On ajoute l'employé sélectionné à l'équipe
        // Pour cela on l'ajoute dans sa variable temporaires
        switch (type) {
            case "Serveur":
                if (whichOne == 1) {
                    serveur1Tmp = (Serveur) selectedOne;
                } else {
                    serveur2Tmp = (Serveur) selectedOne;
                }
                break;
            case "Cuisinier":
                if (whichOne == 1) {
                    cuisinier1Tmp = (Cuisinier) selectedOne;
                } else if (whichOne == 2) {
                    cuisinier2Tmp = (Cuisinier) selectedOne;
                } else if (whichOne == 3) {
                    cuisinier3Tmp = (Cuisinier) selectedOne;
                } else {
                    cuisinier4Tmp = (Cuisinier) selectedOne;
                }
                break;
            case "Barman":
                barmanTmp = (Barman) selectedOne;
                break;
            case "Manager":
                managerTmp = (Manager) selectedOne;
                break;
            default:
                // Renvoie une erreur :
                throw new IllegalStateException("Erreur : type d'employé inconnu dans TeamScreen.java");
        }
    }

    // utiliser pour afficher un texte plus rapidement
    public static void print(String text) {
        System.out.println(text);
    }

    // Fonction qui permet de nettoyer la console pour une meilleure lisibilité
    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}