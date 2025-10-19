package com.belec.app.sql;

public class SQLRequests {

    public static final String SQL_SERVER_LOCATION = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    //"jdbc:mysql://localhost:3306/lemeura"
    public static final String SQL_USER = "lemeura";
    public static final String SQL_PASSWORD = "lemeura";

    public static final String SQL_INSERT_USER = """
            INSERT INTO Utilisateur (Mail, nom, prenom, adresse) VALUES (?,?,?,?)
            """;

    public static final String SQL_RETRIEVE_BEST_OFFER = """
            SELECT ratio FROM Prix_achat WHERE ID_vente = ?
            """;

    public static final String SQL_DELETE_USER = """
            DELETE FROM Utilisateur WHERE mail = ?
            """;

    public static final String SQL_HANDLE_INVALID_SALES = """
            UPDATE Vente SET prix_depart = 0 WHERE prix_depart - prix_descente <= 0 AND montante = 0 AND mail = ?
            """;

    public static final String SQL_DECREASE_SALE = """
            UPDATE Vente SET prix_depart = prix_depart - prix_descente WHERE prix_depart - prix_descente >= 0 AND montante = 0 AND mail = ?
            """;

    public static final String SQL_RETRIEVE_USER = """
            SELECT * FROM Utilisateur WHERE Mail = ?
            """;

    public static final String SQL_RETRIEVE_CATAGORIES = """
            SELECT * FROM Categorie
            """;

    public static final String SQL_RETRIEVE_SALES_ROOM_IN_CATEGORY = """
            SELECT * FROM Salle_de_vente WHERE Nom_categorie = ?
            """;

    public static final String SQL_RETRIEVE_PRODUCTS_BY_CATEGORY = """
    SELECT * FROM Produit WHERE Nom_categorie = ?
    """;

    public static final String SQL_RETRIEVE_PRODUCTS_BY_ID = """
    SELECT * FROM Produit WHERE ID_produit = ?
    """;

    public static final String SQL_RETRIEVE_SALES_BY_ROOM = """
    SELECT * FROM Vente WHERE ID_salle_vente = ?
    """;

    public static final String SQL_RETRIEVE_CARATERITICS_OF_PRODUCT = """
    SELECT * FROM Caracteristique WHERE ID_produit = ?
    """;

    public static final String SQL_INSERT_NEW_SALE = """
    INSERT INTO Vente (montante, limite_offre, revocable, prix_depart,
    date_fin, prix_descente, Stock_lot, ID_salle_vente, ID_produit, mail) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    public static final String SQL_RETRIEVE_OFFERS_BY_INTEREST = """
    SELECT * FROM Offre WHERE ID_Vente = ? ORDER BY prix_achat/quantite DESC
    """;

    public static final String SQL_RETRIEVE_SALE = """
    SELECT * FROM Vente WHERE ID_Vente = ?
    """;

    public static final String SQL_DELETE_SALE = """
    DELETE FROM Vente WHERE ID_Vente = ?
    """;

    public static final String SQL_DELETE_INVALID_SALE = """
    DELETE FROM Vente WHERE ID_Vente = ? AND Stock_lot = 0
    """;

    public static final String SQL_INSERT_NEW_OFFER = """
    INSERT INTO Offre (mail, ID_Vente, date_offre, prix_achat, quantite) VALUES (?, ?, ?, ?, ?)
    """;

    public static final String SQL_RETRIEVE_OFFER_WITH_MAIL = """
    SELECT * FROM Offre WHERE ID_Vente = ? AND mail = ?
    """;

    public static final String SQL_RETRIEVE_OFFER = """
    SELECT * FROM Offre WHERE ID_offre = ?
    """;

    public static final String SQL_UPDATE_STOCK_LOT = """
    UPDATE Vente SET Stock_lot = Stock_lot - ? WHERE ID_Vente = ?
    """;

    public static final String SQL_DELETE_OFFER = """
            DELETE FROM Offre WHERE ID_offre = ?
            """;

    public static final String SQL_REJECT_INCOMPATIBLE_OFFERS = """
    DELETE FROM Offre
    WHERE ID_Vente = ? AND quantite > (SELECT Stock_lot FROM Vente WHERE ID_Vente = ?)
    """;
}
