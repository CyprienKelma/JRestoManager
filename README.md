## Introduction

Système de gestion de restaurant basé sur Java, développé dans le cadre d'un projet scolaire.

- **Traitement des commandes des clients**
- **Gestion des employés**
- **Contrôle des stocks**
- **Recueil de données (tickets, productivités des employés, etc..)**

### Organisation du projet

L'espace de travail contient deux dossiers :

- `src` : le dossier pour gérer les sources
- `lib` : le dossier pour gérer les dépendances

Dans `src/main` se trouve le contenu du projet :

- `launcher` : le package pour lancer l'application. Contient le code des différents écrans et le main.
- `staff` : le package qui contient les informations sur les équipes et les employés qui les composent.
- `place` : le package qui contient le cœur de la gestion du restaurant. Le système de transaction, de gestion du restaurant, etc...
- `stock` : le package de gestion du stockage des ingrédients, de l'état des plats et des différentes cartes disponibles.
