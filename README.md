# Coffee Shop - Application Android

## 1. Description

**Coffee Shop** est une application Android simple conçue pour simuler le processus de commande de café. Elle permet aux utilisateurs de parcourir différentes catégories et produits, de personnaliser leur choix (niveau de sucre), d'ajouter des articles à un panier virtuel et de visualiser un écran de paiement simplifié avec le montant total en Dirhams Marocains (MAD).


## 2. Fonctionnalités Principales

*   **Navigation par Catégories :** Une barre de "Chips" (puces) défilante horizontalement en haut de l'écran principal permet de sélectionner une catégorie de café (Cappuccino, Espresso, etc.) ou de choisir "Tout". Chaque puce de catégorie affiche une icône et le nom correspondant.
*   **Affichage des Produits :** Une liste (`RecyclerView`) affiche les cafés sous forme de cartes (`MaterialCardView`). Chaque carte contient :
    *   L'image principale du produit.
    *   Le nom du produit.
    *   Le prix formaté en MAD (ex: `15,00 MAD`).
    *   Un compteur de sucre (+/-) permettant à l'utilisateur de choisir le niveau désiré (0, 1, 2, etc.).
    *   Un bouton avec une icône pour ajouter l'article au panier.
*   **Filtrage :** La liste des produits se met à jour automatiquement pour n'afficher que les produits de la catégorie sélectionnée.
*   **Panier Virtuel (Interne) :**
    *   La `MainActivity` gère un panier interne sous forme de `HashMap`.
    *   La clé du `HashMap` est unique et combine l'ID du produit et le niveau de sucre choisi (`productId_sugarLevel`), permettant d'avoir le même café avec différents niveaux de sucre comme des articles distincts dans le panier.
    *   L'ajout répété d'un article strictement identique (même produit, même sucre) incrémente simplement sa quantité.
*   **Calcul du Total :** Le prix total est calculé en temps réel (au moment de passer au paiement) en fonction des prix et quantités des articles dans le panier.
*   **Écran de Paiement Simplifié :**
    *   Accessible via un Bouton d'Action Flottant (FAB) en bas à droite de l'écran principal.
    *   Affiche uniquement le montant total dû, formaté en MAD.
    *   Possède un bouton "Payer" avec une icône qui simule l'action de paiement (via un message `Toast`).

## 3. Structure du Projet et Rôles des Classes

Le projet utilise une structure simple avec les composants clés suivants :

*   **Layouts (XML - `res/layout`)**
    *   `activity_main.xml`: Définit la structure de l'écran principal (Chips catégories, RecyclerView produits, FAB).
    *   `item_product_card.xml`: Définit l'apparence d'une seule carte produit dans la liste (image, textes, compteur sucre, bouton ajout).
    *   `activity_payment.xml`: Définit la structure très simple de l'écran de paiement final (titre, total, bouton Payer).
*   **Classes Java (`com.example.coffee_shop_app`)**
    *   **`MainActivity.java`**: L'Activité principale. Gère l'interface principale, charge les données, configure l'adaptateur, gère le filtrage par catégorie, gère le panier virtuel (`HashMap`), calcule le total et lance `PaymentActivity`. Implémente `ProductAdapter.ProductActionListener`.
    *   **`PaymentActivity.java`**: L'Activité secondaire affichant le résumé et le bouton de paiement. Reçoit uniquement le montant total via `Intent`.
    *   **`ProductAdapter.java`**: L'adaptateur pour le `RecyclerView`. Lie les données de la liste `Product` aux vues définies dans `item_product_card.xml`, gère l'état du compteur de sucre local à chaque carte, et communique les clics "ajouter au panier" (avec le niveau de sucre) à `MainActivity`.
    *   **`Product.java`**: Classe Modèle (POJO) pour représenter un produit café (id, nom, prix, `imageResourceId`, `categoryId`).
    *   **`Category.java`**: Classe Modèle (POJO) pour représenter une catégorie (id, nom, `iconDrawableId`).
    *   **`CartItem.java`**: Classe Modèle (POJO) pour représenter un article dans le panier virtuel (`product`, `quantity`, `sugarLevel`).
*   **Ressources (`res`)**
    *   `drawable`: Contient les icônes vectorielles (`ic_*.xml`) et les images des produits/catégories (`img_*.png`).
    *   `values`: Contient `strings.xml` (textes par défaut), `colors.xml`, `themes.xml`.
    *   `values-fr`: Contient `strings.xml` avec les traductions françaises des textes de l'interface.
    *   `mipmap`: Contient les icônes de lancement de l'application (`ic_launcher`).
*   **Manifest (`AndroidManifest.xml`)**: Déclare les activités (`MainActivity`, `PaymentActivity`), les permissions (ex: Internet si les images étaient chargées depuis le web) et d'autres configurations globales de l'application.

## 4. Technologies et Librairies Utilisées

*   **Langage :** Java
*   **Kit de développement :** Android SDK
*   **Interface Utilisateur :** XML Layouts
*   **Composants Android Jetpack / Google:**
    *   AppCompat (pour la compatibilité)
    *   ConstraintLayout (pour les mises en page flexibles)
    *   RecyclerView (pour l'affichage efficace des listes)
    *   Material Design Components (pour `MaterialCardView`, `Chip`, `FloatingActionButton`, `MaterialButton`)
*   **Librairie Externe :**
    *   `Glide` : Pour le chargement et l'affichage efficaces des images (depuis les ressources locales dans ce cas).
*   **Système de Build :** Gradle

