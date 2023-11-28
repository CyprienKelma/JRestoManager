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

    // Le tableau suivant est utilisé plus bas pour créer l'instance de la
    // classe Equipe, lors de la confirmation
    private static Employé[] actualTmpTeamTab = { (Serveur) serveur1Tmp, (Serveur) serveur2Tmp,
            (Cuisinier) cuisinier1Tmp, (Cuisinier) cuisinier2Tmp, (Cuisinier) cuisinier3Tmp,
            (Cuisinier) cuisinier4Tmp, (Barman) barmanTmp, (Manager) managerTmp };

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

        int choixEcran = menuScanner.nextInt();

        switch (choixEcran) {
            case 1:
                showSelectEmployeesScreen(menuScanner, "Serveur", 2);
                break;
            case 2:
                showSelectEmployeesScreen(menuScanner, "Cuisinier", 4);
                break;
            case 3:
                showSelectEmployeesScreen(menuScanner, "Barman", 1);
                break;
            case 4:
                showSelectEmployeesScreen(menuScanner, "Manager", 1);
                break;
            case 5:
                confirmFinalNewTeam(menuScanner);
                break;
            case 6:
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case 7:
                App.showMainMenu();
                break;
            default:
                showTeamFormationScreen(menuScanner);
        }
    }

    // Ecran pour choisir les employés pour former l'équipe parmis ceux requis
    public static void showSelectEmployeesScreen(Scanner menuScanner, String type, int numberOfEmployees) {
        clearConsole();
        print("==========================================================================\n");
        if (numberOfEmployees == 1) { // Pour le barman et manager
            print("CHOISIR LE " + type.toUpperCase() + " :\n");
            print("Vous devez choisir le " + type.toLowerCase()
                    + " qui travaillera durant tout ce service.\n");
        } else { // Pour les serveurs et cuisiniers
            print("CHOISIR LES " + type.toUpperCase() + "S :\n");
            print("Vous devez choisir " + numberOfEmployees + " " + type.toLowerCase()
                    + "s qui travailleront durant tout ce service.\n");

        }

        // En fonction du nombre d'employé de ce type qu'il faut
        // Ex : 4 cuisiniers, 1 barman, etc...
        for (int i = 1; i <= numberOfEmployees; i++) {
            print(i + " - " + type + " " + i + " : A SELECTIONNER");
        }

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
    public static void showEmployeeListByType(Scanner menuScanner, String type, int whichOne) {

        // On va créer une liste temporaire pour stocker les employés du type voulu
        List<Employé> tmpList = new ArrayList<>();

        clearConsole();
        print("==========================================================================\n");
        if (whichOne == 1) {
            print("Selectionner un premier " + type + "à ajouter parmis la liste :");
        } else {
            print("Selectionner un " + whichOne + "ème " + type + "à ajouter parmis la liste :");
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
            if (employé.getClass().getSimpleName().equals(type)) {

                // On affiche l'employé seulement si il n'est pas déjà sélectionné :
                if (!(serveur1Tmp == employé || serveur2Tmp == employé)) {

                    // Vérifier que l'employé n'est pas déja dans l'équipe :
                    print((j + 1) + ") " + type + " : " + employé.getNom() + ", " + employé.getPrenom() + ", "
                            + employé.getSalaire() + " euros/h net.");

                    j++; // On incrémente j seulement si on affiche l'employé
                    // i.e. s'il est du type qui nous concerne ET qu'il n'est pas déjà sélectionné

                    tmpList.add(employé);

                }
            }
        }

        print("\n--------------------------------------------------------------------------");
        print("Employé numéro :\n");
        int input = menuScanner.nextInt();

        // SelectedOne désigne l'employé d'indice j (où j = input - 1)
        Employé selectedOne = tmpList.get(input - 1);

        // On ajoute l'employé sélectionné dans l'équipe
        // Pour cela on l'ajoute dans sa variable temporaires
        switch (type) {
            case "Serveur":
                if (whichOne == 1) {
                    serveur1Tmp = (Serveur) selectedOne;
                    actualTmpTeamTab[0] = serveur1Tmp;
                    notifyAddTeam(menuScanner, serveur1Tmp, 1, "Serveur");
                } else {
                    serveur2Tmp = (Serveur) selectedOne;
                    actualTmpTeamTab[1] = serveur2Tmp;
                    notifyAddTeam(menuScanner, serveur2Tmp, 2, "Serveur");
                }
                break;
            case "Cuisinier":
                if (whichOne == 1) {
                    cuisinier1Tmp = (Cuisinier) selectedOne;
                    actualTmpTeamTab[2] = cuisinier1Tmp;
                    notifyAddTeam(menuScanner, serveur1Tmp, 1, "Cuisinier");
                } else if (whichOne == 2) {
                    cuisinier2Tmp = (Cuisinier) selectedOne;
                    actualTmpTeamTab[3] = cuisinier2Tmp;
                    notifyAddTeam(menuScanner, serveur1Tmp, 2, "Cuisinier");
                } else if (whichOne == 3) {
                    cuisinier3Tmp = (Cuisinier) selectedOne;
                    actualTmpTeamTab[4] = cuisinier3Tmp;
                    notifyAddTeam(menuScanner, serveur1Tmp, 3, "Cuisinier");
                } else {
                    cuisinier4Tmp = (Cuisinier) selectedOne;
                    actualTmpTeamTab[5] = cuisinier4Tmp;
                    notifyAddTeam(menuScanner, serveur1Tmp, 4, "Cuisinier");
                }
                break;
            case "Barman":
                barmanTmp = (Barman) selectedOne;
                actualTmpTeamTab[6] = barmanTmp;
                notifyAddTeam(menuScanner, serveur1Tmp, 1, "Barman");
                break;
            case "Manager":
                managerTmp = (Manager) selectedOne;
                actualTmpTeamTab[7] = managerTmp;
                notifyAddTeam(menuScanner, serveur1Tmp, 1, "Manager");
                break;
            default:
                // Renvoie une erreur :
                throw new IllegalStateException("Erreur : type d'employé inconnu dans TeamScreen.java");
        }
    }

    public static void notifyAddTeam(Scanner menuScanner, Employé selectedOne, int whichOne, String type) {
        clearConsole();
        print("==========================================================================\n");
        print(selectedOne.getPrenom() + " " + selectedOne.getNom() + "a été ajouté à l'équipe !");
        if (whichOne == 1)
            print("Une fois l'équipe confirmé, il sera le " + type.toLowerCase() + " du service de se soir.");
        else
            print("Une fois l'équipe confirmé, il sera le " + type.toLowerCase() + " N°" + whichOne
                    + " du service de ce soir\n\n\n");

        print("\n--------------------------------------------------------------------------");
        print("1 - Page précédente");
        print("2 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        // Pour rappeller la fonction précédente (showSelectEmployeesScreen)
        // On doit redonner en paramètre le nombre d'employé (qui dépend du type)
        int numberOfEmployees = 0;
        if (type.equals("Serveur"))
            numberOfEmployees = 2;
        else if (type.equals("Cuisinier"))
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

        // On vérifie que l'équipe est complète
        for (int i = 0; i < actualTmpTeamTab.length; i++) {
            // Dans le cas on une valeur n'est pas donné (=null)
            if (actualTmpTeamTab[i] == null) {
                if (i == 0 || i == 1)
                    // On redirige vers un message d'erreur
                    missingTeamMembers(menuScanner, "Serveur");
                else if (i >= 2 && i <= 5)
                    missingTeamMembers(menuScanner, "Cuisinier");
                else if (i == 6)
                    missingTeamMembers(menuScanner, "Barman");
                else
                    missingTeamMembers(menuScanner, "Manager");
                break;
            }
        }

    }

    public static void missingTeamMembers(Scanner menuScanner, String missingMember) {
        clearConsole();
        print("==========================================================================\n");
        print("ERREUR : L'équipe n'est pas complète !");
        print("Vous devez d'abord choisir tous les employés qui travailleront durant le service.\n");
        print("Il manque encore au moins un " + missingMember + " dans l'équipe.\n\n");

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
        Equipe newTeam = new Equipe((Serveur) actualTmpTeamTab[0], (Serveur) actualTmpTeamTab[1],
                (Cuisinier) actualTmpTeamTab[2], (Cuisinier) actualTmpTeamTab[3], (Cuisinier) actualTmpTeamTab[4],
                (Cuisinier) actualTmpTeamTab[5], (Barman) actualTmpTeamTab[6], (Manager) actualTmpTeamTab[7]);

        // On définit alors l'équipe actuelle du restaurant
        Restaurant.setEquipeActuelle(newTeam);

        // On affiche un message de confirmation
        clearConsole();
        print("==========================================================================\n");
        print("L'équipe a été créée avec succès !");
        print("Vous pouvez maintenant ouvrir le restaurant.\n\n");

        // TEST - Pas présent au rendu final :

        print("Les membres de l'équipe sont :\n\n");
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
        print("--------------------------------------------------------------------------");
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