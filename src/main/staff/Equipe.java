package main.staff;

import java.util.*;

public class Equipe {

    /*
     * Une équipe est composée de 2 serveurs, 4 cuisiniers, 1 barman et 1 manager
     * Elle est créée par le manager et indispensable pour ouvrir le restaurant
     * Pour créer une équipe, on choisi des employé parmis la liste des employés
     * disponibles du restaurant
     */
    private Serveur serveur1;
    private Serveur serveur2;
    private Cuisinier cuisinier1;
    private Cuisinier cuisinier2;
    private Cuisinier cuisinier3;
    private Cuisinier cuisinier4;
    private Barman barman;
    private Manager manager;

    /*
     * !!! ATTENTION !!!
     * NE PAS CHANGER L'ORDRE DES PARAMETRES DU CONSTRUCTEUR NI LES SETTERS
     * Sinon le code des écrans de crations d'équipe ne fonctionneront plus
     */

    public Equipe(Serveur serveur1, Serveur serveur2, Cuisinier cuisinier1, Cuisinier cuisinier2, Cuisinier cuisinier3,
            Cuisinier cuisinier4, Barman barman, Manager manager) {
        this.serveur1 = serveur1;
        this.serveur2 = serveur2;
        this.cuisinier1 = cuisinier1;
        this.cuisinier2 = cuisinier2;
        this.cuisinier3 = cuisinier3;
        this.cuisinier4 = cuisinier4;
        this.barman = barman;
        this.manager = manager;
    }

    /**
     * @return Manager return the manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * @return Serveur return the serveur1
     */
    public Serveur getServeur1() {
        return serveur1;
    }

    /**
     * @param serveur1 the serveur1 to set
     */
    public void setServeur1(Serveur serveur1) {
        this.serveur1 = serveur1;
    }

    /**
     * @return Serveur return the serveur2
     */
    public Serveur getServeur2() {
        return serveur2;
    }

    /**
     * @param serveur2 the serveur2 to set
     */
    public void setServeur2(Serveur serveur2) {
        this.serveur2 = serveur2;
    }

    /**
     * @return Barman return the barman
     */
    public Barman getBarman() {
        return barman;
    }

    /**
     * @param barman the barman to set
     */
    public void setBarman(Barman barman) {
        this.barman = barman;
    }

    /**
     * @return Cuisinier return the cuisinier1
     */
    public Cuisinier getCuisinier1() {
        return cuisinier1;
    }

    /**
     * @param cuisinier1 the cuisinier1 to set
     */
    public void setCuisinier1(Cuisinier cuisinier1) {
        this.cuisinier1 = cuisinier1;
    }

    /**
     * @return Cuisinier return the cuisinier2
     */
    public Cuisinier getCuisinier2() {
        return cuisinier2;
    }

    /**
     * @param cuisinier2 the cuisinier2 to set
     */
    public void setCuisinier2(Cuisinier cuisinier2) {
        this.cuisinier2 = cuisinier2;
    }

    /**
     * @return Cuisinier return the cuisinier3
     */
    public Cuisinier getCuisinier3() {
        return cuisinier3;
    }

    /**
     * @param cuisinier3 the cuisinier3 to set
     */
    public void setCuisinier3(Cuisinier cuisinier3) {
        this.cuisinier3 = cuisinier3;
    }

    /**
     * @return Cuisinier return the cuisinier4
     */
    public Cuisinier getCuisinier4() {
        return cuisinier4;
    }

    /**
     * @param cuisinier4 the cuisinier4 to set
     */
    public void setCuisinier4(Cuisinier cuisinier4) {
        this.cuisinier4 = cuisinier4;
    }

}