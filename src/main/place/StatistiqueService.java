package main.place;

import java.util.HashMap;
import java.util.Map;


// Classe qui gère les statistiques du restaurant :
// Les statistiques sont réinitialisées à chaque réouverture du restaurant
// Comme ça le manager peut avoir les stats du service actuel si le restaurant est ouvert
// Ou les stats du dernier service si le restaurant est fermé
public class StatistiqueService {

    StatistiqueService() {
        // Constructeur privé pour empêcher l'instanciation de la classe "StatistiqueService"
        // C'est une classe utilitaire puisqu'on travaille avec un seul restaurant
        throw new IllegalStateException("Classe Restaurant : pas d'instances requises");
    }

    // Statistiques usuelles du restaurant lors d'un service :
    private static int nbrTransaction = 0;
    private static int nbrClient = 0;
    private static int nbrPlatsVendues = 0;
    private static int nbrBoissonsVendues = 0;
    private static double chiffreAffaire = 0; // Chiffre d'affaire du restaurant sur la soirée


    // Map qui contient le nom des plat et les nombre de fois qu'ils ont étés vendu
    private static Map<String, Integer> platsVendus = new HashMap<>();

    // Map qui contient le nom des boissons et les nombre de fois qu'elles ont étés vendu
    private static Map<String, Integer> boissonsVendues = new HashMap<>();

    // Réinitialise toutes les statistiques
    // => Utilisé lors de la réouverture du restaurant
    // Ainsi le manager voit les stats du dernier service uniquement
    public static void resetAllStatistique(){
        nbrTransaction = 0;
        nbrClient = 0;
        nbrPlatsVendues = 0;
        nbrBoissonsVendues = 0;
        platsVendus.clear();
        boissonsVendues.clear();
        chiffreAffaire = 0;
    }

    // Affiche les statistiques actuelles du restaurant
    public static void showActualStatistique(){
        print("Statistiques générales :\n");

        print("Chiffre d'affaire : " + getChiffreAffaire() + " euros\n");
        print("Nombre de transaction effectués : " + getNbrTransactionStat());
        print("Nombre de clients venu : " + getNbrClientStat() + "\n");

        print("Nombre de plats vendus : " + getNbrPlatsVenduesStat());
        print("Nombre de boissons vendues : " + getNbrBoissonsVenduesStat() + "\n");

        print("Produit les plus et moins appréciés par les clients :\n");

        print("Plats le plus selectionné : " + getMostSelectedElem(platsVendus));
        print("Plats le moins selectionné : " + getLessSelectedElem(platsVendus) + "\n");
        print("Boissons la plus selectionnée : " + getMostSelectedElem(boissonsVendues));
        print("Boissons la moins selectionnée : " + getLessSelectedElem(boissonsVendues) + "\n");

        print("Statistiques par employés :\n ");
        print("Nombre de transaction effectué par le serveur N°1 (" + Restaurant.getEquipeActuelle().getServeur1().getPrenom() 
        + ") : " + Restaurant.getEquipeActuelle().getServeur1().getNombreDeTablesServies());

        print("Nombre de transaction effectué par le serveur N°2 (" + Restaurant.getEquipeActuelle().getServeur2().getPrenom()
        + ") : " + Restaurant.getEquipeActuelle().getServeur2().getNombreDeTablesServies() + "\n");

        print("Nombre de plats préparés par le cuisinier N°1 (" + Restaurant.getEquipeActuelle().getCuisinier1().getPrenom()
        + ") : " + Restaurant.getEquipeActuelle().getCuisinier1().getNbrPlatsCuisines());

        print("Nombre de plats préparés par le cuisinier N°2 (" + Restaurant.getEquipeActuelle().getCuisinier2().getPrenom()
        + ") : " + Restaurant.getEquipeActuelle().getCuisinier2().getNbrPlatsCuisines() + "\n");

        print("Nombre de plats préparés par le cuisinier N°3 (" + Restaurant.getEquipeActuelle().getCuisinier3().getPrenom()
        + ") : " + Restaurant.getEquipeActuelle().getCuisinier3().getNbrPlatsCuisines() + "\n");

        print("Nombre de plats préparés par le cuisinier N°4 (" + Restaurant.getEquipeActuelle().getCuisinier4().getPrenom()
        + ") : " + Restaurant.getEquipeActuelle().getCuisinier4().getNbrPlatsCuisines() + "\n");

        print("Nombre de commande prise par le barman (" + Restaurant.getEquipeActuelle().getBarman().getPrenom()
        + ") : " + Restaurant.getEquipeActuelle().getBarman().getNbrBoissonsServies());
    }

    // Fonction qui retourne l'élément le plus vendu (plats ou boissons)
    public static String getMostSelectedElem(Map<String, Integer> elements){
        int maxVentes = 0;
        String elemPlusVendu = null;

        // On parcours la map pour trouver l'élément le plus vendu
        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            if (entry.getValue() > maxVentes) {
                maxVentes = entry.getValue();
                elemPlusVendu = entry.getKey();
            }
        }

        // En fonction de la map passée en paramètre, on retourne le bon message
        if(elements == platsVendus){
            return "Plat le plus apprécié : " + elemPlusVendu + " (" + maxVentes + " ventes)";
        } else {
            return "Boisson la plus appréciée : " + elemPlusVendu + " (" + maxVentes + " ventes)";
        }
    }

    // Même chose pour fois les éléments qui ont connus le moins de succés
    public static String getLessSelectedElem(Map<String, Integer> elements){
        int minVentes = 0;
        String elemMoinsVendu = null;

        // On parcours la map pour trouver l'élément le moins vendu
        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            if (entry.getValue() < minVentes) {
                minVentes = entry.getValue();
                elemMoinsVendu = entry.getKey();
            }
        }

        // En fonction de la map passée en paramètre, on retourne le bon message
        if(elements == platsVendus){
            return "Plat le moins apprécié : " + elemMoinsVendu + " (" + minVentes + " ventes)";
        } else {
            return "Boisson la moins appréciée : " + elemMoinsVendu + " (" + minVentes + " ventes)";
        }
    }

    // Fonction qui ajoute un plat vendu aux statistiques
    public static void addPlatVenduStat(Map<String, Integer> plats) {
        nbrPlatsVendues++;
        // On parcours la map des plats vendus pour ajouter les plats
        plats.forEach((key, value) -> platsVendus.put(key, platsVendus.getOrDefault(key, 0) + value));
    }



    /* --------------------------- GETTER ET SETTER USUELLES :  --------------------------- */

    // Fonction qui retourne le chiffre d'affaire du restaurant
    public static double getChiffreAffaire() {
        return chiffreAffaire;
    }

    // Fonction qui ajoute un chiffre d'affaire au restaurant
    public static void addChiffreAffaire(double chiffreAffaire) {
        StatistiqueService.chiffreAffaire += chiffreAffaire;
    }


    // Remet le chiffre d'affaire à 0 (lorsque l'on commence un nouveau service)
    public static void resetChiffreAffaire() {
        StatistiqueService.chiffreAffaire = 0;
    }

    // Fonction qui retourne la map des plats vendus
    public static Map<String, Integer> getPlatsVendus() {
        return platsVendus;
    }

    // Fonction qui ajoute une boisson vendue aux statistiques
    public static void addBoissonVendueStat(Map<String, Integer> boissons) {
        nbrBoissonsVendues++;
        // On parcours la map des boissons vendues pour ajouter les boissons
        boissons.forEach((key, value) -> boissonsVendues.put(key, boissonsVendues.getOrDefault(key, 0) + value));
    }

    // Fonction qui retourne la map des boissons vendues
    public static Map<String, Integer> getBoissonsVendues() {
        return boissonsVendues;
    }

    // Augmente le nombre de transaction de 1
    public static void addTransactionStat(){
        nbrTransaction++;
    }

    // Augmente le nombre de client de (number)
    public static void addClientStat(int number){
        nbrClient += number;
    }

    /**
     * @return int return the nbrTransaction
     */
    public static int getNbrTransactionStat() {
        return nbrTransaction;
    }

    /**
     * @param nbrTransaction the nbrTransaction to set
     */
    public static void setNbrTransactionStat(int newNbrTransaction) {
        nbrTransaction = newNbrTransaction;
    }

    /**
     * @return int return the nbrClient
     */
    public static int getNbrClientStat() {
        return nbrClient;
    }

    /**
     * @param nbrClient the nbrClient to set
     */
    public static void setNbrClientStat(int newNbrClient) {
        nbrClient = newNbrClient;
    }

    /**
     * @return int return the nbrPlatsVendues
     */
    public static int getNbrPlatsVenduesStat() {
        return nbrPlatsVendues;
    }

    /**
     * @param nbrPlatsVendues the nbrPlatsVendues to set
     */
    public static void setNbrPlatsVenduesStat(int newNbr) {
        nbrPlatsVendues = newNbr;
    }

    /**
     * @return int return the nbrBoissonsVendues
     */
    public static int getNbrBoissonsVenduesStat() {
        return nbrBoissonsVendues;
    }

    /**
     * @param nbrBoissonsVendues the nbrBoissonsVendues to set
     */
    public static void setNbrBoissonsVenduesStat(int newNbrBoissonsVendues) {
        nbrBoissonsVendues = newNbrBoissonsVendues;
    }

    public static void print(String text) {
        System.out.println(text);
    }
}