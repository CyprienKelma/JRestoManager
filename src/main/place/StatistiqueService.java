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

    // Statistiques usuelles du restaurant lors d'un service
    private static int nbrTransaction = 0;
    private static int nbrClient = 0;
    private static int nbrPlatsVendues = 0;
    private static int nbrBoissonsVendues = 0;



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
    }

    // Affiche les statistiques actuelles du restaurant
    public static void showActualStatistique(){
        print("Nombre de transaction effectués : " + getNbrTransactionStat() + "\n");
        print("Nombre de clients venu : " + getNbrClientStat() + "\n");
        print("Nombre de plats vendus : " + getNbrPlatsVenduesStat());
        print("Nombre de boissons vendues : " + getNbrBoissonsVenduesStat() + "\n");
        getMostSelectedElem(platsVendus);
        getMostSelectedElem(boissonsVendues);
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

    // Fonction qui ajoute un plat vendu aux statistiques
    public static void addPlatVenduStat(Map<String, Integer> plats) {
        nbrPlatsVendues++;
        // On parcours la map des plats vendus pour ajouter les plats
        plats.forEach((key, value) -> platsVendus.put(key, platsVendus.getOrDefault(key, 0) + value));
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

    

    // GETTER ET SETTER USUELLES :

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