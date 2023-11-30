package main.launcher.employee;

import java.util.*;

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

    /*
     * Note importante :
     * On aurait pu utiliser ici une HashMap pour stocker les employés temporaires.
     * Par exemple :
     * private static Map<String, Employé> employeeTmpHashMap = new HashMap<>();
     * Mais puisque le nombre d'employés est fixe, il est plus simple d'utiliser des
     * variables séparées pour chaque employé temporaire, afin d'éviter les doublons
     */

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
        print("durant tout le service.\n");

        print("1 - Choisir les serveurs");
        print("2 - Choisir les cuisiniers");
        print("3 - Choisir le barman");
        print("4 - Choisir le manager\n");
        print("5 - Confirmer la selection et créer l'équipe");
        print("\n6 - Page précédente");
        print("7 - Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                showSelectEmployeesScreen(menuScanner, Serveur.class, 2);
                break;
            case "2":
                showSelectEmployeesScreen(menuScanner, Cuisinier.class, 4);
                break;
            case "3":
                showSelectEmployeesScreen(menuScanner, Barman.class, 1);
                break;
            case "4":
                showSelectEmployeesScreen(menuScanner, Manager.class, 1);
                break;
            case "5":
                confirmFinalNewTeam(menuScanner);
                break;
            case "6":
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case "7":
                App.showMainMenu();
                break;
            default:
                showTeamFormationScreen(menuScanner);
        }
    }

    // Ecran pour choisir les employés pour former l'équipe parmis ceux requis
    public static <T> void showSelectEmployeesScreen(Scanner menuScanner, Class<T> type, int numberOfEmployees) {
        clearConsole();
        print("==========================================================================\n");
        if (numberOfEmployees == 1) { // Pour le barman et manager
            print("CHOISIR LE " + type.getSimpleName().toUpperCase() + " :\n");
            print("Vous devez choisir le " + type.getSimpleName().toLowerCase()
                    + " qui travaillera durant tout ce service.\n");
        } else { // Pour les serveurs et cuisiniers
            print("CHOISIR LES " + type.getSimpleName().toUpperCase() + "S :\n");
            print("Vous devez choisir " + numberOfEmployees + " " + type.getSimpleName().toLowerCase()
                    + "s qui travailleront durant tout ce service.\n");

        }

        // En fonction du nombre d'employé de ce type qu'il faut
        // Ex : 4 cuisiniers, 1 barman, etc...
        for (int i = 1; i <= numberOfEmployees; i++) {
            print(i + " - " + type.getSimpleName() + " " + i + " : A SELECTIONNER");
        }

        // Pour changer le A SELECTIONNER en NOM PRENOM SALAIRE
        // for (int i = 1; i <= numberOfEmployees; i++) {
        //     if ((i == 1) && serveur1Tmp != null) {
        //         print(i + " - " + type.getSimpleName() + " " + i + " : " + serveur1Tmp.getPrenom() + " "
        //                 + serveur1Tmp.getNom());
        //         continue;
        //     } else {
        //         print(i + " - " + type.getSimpleName() + " " + i + " : A SELECTIONNER");
        //     }
        //     // A faire pour chaque employé temporaire
        // }

        print("\n" + (numberOfEmployees + 1) + " - Page précédente");
        print((numberOfEmployees + 2) + " - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        // Permet de choisir en fonction du nombre d'employé requis (ex : 4 cuisiniers)
        if (choixEcran >= 1 && choixEcran <= numberOfEmployees) {
            showEmployeeListByType(menuScanner, type, choixEcran);

        } else if (choixEcran == numberOfEmployees + 1) {

            showTeamFormationScreen(menuScanner); // Au dela de 1, on reviens à la page d'avant
        } else if (choixEcran == numberOfEmployees + 2) {

            App.showMainMenu(); // Au dela de 2, on reviens au menu principale
        } else {

            showSelectEmployeesScreen(menuScanner, type, numberOfEmployees);
        }
    }

    // Cette fonction affiche la liste des employés du type voulu. Comme ça,
    // on peut choisir lequel on ajoute parmis ceux du bon type
    public static <T> void showEmployeeListByType(Scanner menuScanner, Class<T> type, int whichOne) {

        // On va créer une liste temporaire pour stocker les employés du type voulu
        List<Employé> tmpList = new ArrayList<>();

        clearConsole();
        print("==========================================================================\n");
        if (whichOne == 1) {
            print("Selectionner un premier " + type.getSimpleName() + "à ajouter parmis la liste :");
        } else {
            print("Selectionner un " + whichOne + "ème " + type.getSimpleName() + "à ajouter parmis la liste :");
        }

        // Puisque la liste va être filtrée (on ne veut que les serveurs par exemple)
        // On va utiliser une variable j pour afficher les numéros des employés
        int j = 0;

        // On affiche la liste des employés du type qui nous concerne
        // Par exemple n'afficher que les serveurs quand on selectionne nos 2 serveurs
        for (int i = 0; i < Restaurant.getEmployésList().size(); i++) {

            Employé employé = Restaurant.getEmployésList().get(i);

            // On affiche que les employés du type qui nous concerne (i.e. du paramètre
            // 'type')
            if (employé.getClass().equals(type) && !(serveur1Tmp == employé || serveur2Tmp == employé)) {

                // Vérifier que l'employé n'est pas déja dans l'équipe :
                print((j + 1) + ") " + type.getSimpleName() + " : " + employé.getNom() + ", " + employé.getPrenom()
                        + ", "
                        + employé.getSalaire() + " euros/h net.");

                j++; // On incrémente j seulement si on affiche l'employé
                // i.e. s'il est du type qui nous concerne ET qu'il n'est pas déjà sélectionné

                tmpList.add(employé);

            }
        }

        print("\n--------------------------------------------------------------------------");
        print("Employé numéro :\n");
        int input = menuScanner.nextInt();

        // SelectedOne désigne l'employé d'indice j (où j = input - 1)
        Employé selectedOne = tmpList.get(input - 1);

        // On ajoute l'employé sélectionné dans l'équipe
        // Pour cela on l'ajoute dans sa variable temporaires
        if (Serveur.class.equals(type)) {
            if (whichOne == 1) {
                serveur1Tmp = (Serveur) selectedOne;
                notifyAddTeam(menuScanner, serveur1Tmp, 1, Serveur.class);
            } else {
                serveur2Tmp = (Serveur) selectedOne;
                notifyAddTeam(menuScanner, serveur2Tmp, 2, Serveur.class);
            }
        } else if (Cuisinier.class.equals(type)) {
            if (whichOne == 1) {
                cuisinier1Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier1Tmp, 1, Cuisinier.class);
            } else if (whichOne == 2) {
                cuisinier2Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier2Tmp, 2, Cuisinier.class);
            } else if (whichOne == 3) {
                cuisinier3Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier3Tmp, 3, Cuisinier.class);
            } else {
                cuisinier4Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier4Tmp, 4, Cuisinier.class);
            }
        } else if (Barman.class.equals(type)) {
            barmanTmp = (Barman) selectedOne;
            notifyAddTeam(menuScanner, barmanTmp, 1, Barman.class);
        } else if (Manager.class.equals(type)) {
            managerTmp = (Manager) selectedOne;
            notifyAddTeam(menuScanner, managerTmp, 1, Manager.class);
        } else {
            // Renvoie une erreur :
            throw new IllegalStateException("Erreur : type d'employé inconnu dans TeamScreen.java");
        }
    }

    public static <T extends Employé> void notifyAddTeam(Scanner menuScanner, Employé selectedOne, int whichOne,
            Class<T> type) {
        clearConsole();
        print("==========================================================================\n");
        print(selectedOne.getPrenom() + " " + selectedOne.getNom() + " a été ajouté à l'équipe !\n");
        if (Barman.class.equals(type) || Manager.class.equals(type))
            print("Une fois l'équipe confirmé, il sera le " + type.getSimpleName().toLowerCase()
                    + " du service de se soir.");
        else
            print("Une fois l'équipe confirmé, il sera le " + type.getSimpleName().toLowerCase() + " N°" + whichOne
                    + " du service de ce soir\n\n\n");

        print("\n--------------------------------------------------------------------------");
        print("1 - Page précédente");
        print("2 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        // Pour rappeller la fonction précédente (showSelectEmployeesScreen)
        // On doit redonner en paramètre le nombre d'employé (qui dépend du type)
        int numberOfEmployees = 0;
        if (Serveur.class.equals(type))
            numberOfEmployees = 2;
        else if (Cuisinier.class.equals(type))
            numberOfEmployees = 4;
        else
            numberOfEmployees = 1;

        if (choixEcran == 1) {
            showSelectEmployeesScreen(menuScanner, type, numberOfEmployees);
        } else if (choixEcran == 2) {
            App.showMainMenu();
        } else {
            notifyAddTeam(menuScanner, selectedOne, whichOne, type);
        }

    }

    public static void confirmFinalNewTeam(Scanner menuScanner) {

        // On vérifie si l'quipe est incomplète

        if (serveur1Tmp == null || serveur2Tmp == null) {
            missingTeamMembers(menuScanner, "Serveur");
        } else if (cuisinier1Tmp == null || cuisinier2Tmp == null || cuisinier3Tmp == null || cuisinier4Tmp == null) {
            missingTeamMembers(menuScanner, "Cuisinier");
        } else if (barmanTmp == null) {
            missingTeamMembers(menuScanner, "Barman");
        } else if (managerTmp == null) {
            missingTeamMembers(menuScanner, "Manager");
        }

        clearConsole();
        print("==========================================================================\n");
        print("CONFIRMER VOTRE ÉQUIPE :\n");

        print("Etes-vous sûr de vouloir confirmer la création d'équipe ?");
        print("Il ne sera plus possible de la modifier jusqu'au prochain service.\n");

        print("1 - Confirmer la selection et finaliser l'équipe\n");
        print("2 - Page précédente");
        print("3 - Retour au menu principal");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                instanciateTeam();
                break;
            case "2":
                showTeamFormationScreen(menuScanner);
                break;
            case "3":
                App.showMainMenu();
                break;
            default:
                confirmFinalNewTeam(menuScanner);
        }
    }

    public static void missingTeamMembers(Scanner menuScanner, String missingMember) {
        clearConsole();
        print("==========================================================================\n");
        print("ERREUR : L'équipe n'est pas complète !");
        print("Vous devez d'abord choisir tous les employés présent durant le service.\n");
        print("Il manque encore au moins un " + missingMember.toLowerCase() + " dans l'équipe.\n\n");

        print("\n--------------------------------------------------------------------------");
        print("1 - Page précédente");
        print("2 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        if (choixEcran == 1) {
            showTeamFormationScreen(menuScanner);
        } else if (choixEcran == 2) {
            App.showMainMenu();
        } else {
            missingTeamMembers(menuScanner, missingMember);
        }
    }

    public static void instanciateTeam() {
        // On instancie l'équipe avec les employés temporaires
        Equipe newTeam = new Equipe(serveur1Tmp, serveur2Tmp, cuisinier1Tmp, cuisinier2Tmp, cuisinier3Tmp,
                cuisinier4Tmp,
                barmanTmp, managerTmp);

        // On définit alors l'équipe actuelle du restaurant
        Restaurant.setEquipeActuelle(newTeam);

        // On ouvre le restaurant :
        Restaurant.setIsOpen(true);

        // On affiche un message de confirmation
        clearConsole();
        print("==========================================================================\n");
        print("L'équipe a été créée avec succès !");
        print("Vous pouvez maintenant ouvrir le restaurant.");
        print("Pour cela, rendez-vous dans l'écran Monitoring du manager.\n\n");

        // TEST - Pas présent au rendu final :

        print("Les membres de l'équipe sont :");
        print("--------------------------------------------------------------------------");
        print("Serveur 1 : " + Restaurant.getEquipeActuelle().getServeur1().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getServeur1().getNom());
        print("Serveur 2 : " + Restaurant.getEquipeActuelle().getServeur2().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getServeur2().getNom());
        print("\nCuisinier 1 : " + Restaurant.getEquipeActuelle().getCuisinier1().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier1().getNom());
        print("Cuisinier 2 : " + Restaurant.getEquipeActuelle().getCuisinier2().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier2().getNom());
        print("Cuisinier 3 : " + Restaurant.getEquipeActuelle().getCuisinier3().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier3().getNom());
        print("Cuisinier 4 : " + Restaurant.getEquipeActuelle().getCuisinier4().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier4().getNom());
        print("\nBarman : " + Restaurant.getEquipeActuelle().getBarman().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getBarman().getNom());
        print("\nManager : " + Restaurant.getEquipeActuelle().getManager().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getManager().getNom());
        print("--------------------------------------------------------------------------\n");

        print("Appuyez sur Entrée pour revenir au menu principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        App.showMainMenu();
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