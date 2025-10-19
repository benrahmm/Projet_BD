CREATE TABLE Categorie (
    Nom_categorie VARCHAR(50) PRIMARY KEY,
    description TEXT
);

CREATE TABLE Salle_de_vente (
    ID_salle_vente SERIAL PRIMARY KEY,
    Nom_categorie VARCHAR(50) NOT NULL,
    CONSTRAINT fk_salle_categorie FOREIGN KEY (Nom_categorie)
    REFERENCES Categorie(Nom_categorie)
);


CREATE TABLE Produit (
    ID_produit SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE,
    prix_revient FLOAT NOT NULL,
    Nom_categorie VARCHAR(50) NOT NULL,
    CONSTRAINT fk_produit_categorie FOREIGN KEY (Nom_categorie)
    REFERENCES Categorie(Nom_categorie),
    CONSTRAINT chk_prix_revient CHECK (prix_revient > 0)
);


CREATE TABLE Caracteristique (
    ID_produit BIGINT UNSIGNED NOT NULL,
    Nom_Caracteristique VARCHAR(50) NOT NULL,
    valeur TEXT,
    PRIMARY KEY (ID_produit, Nom_Caracteristique),
    CONSTRAINT fk_caracteristique_produit FOREIGN KEY (ID_produit)
    REFERENCES Produit(ID_produit) ON DELETE CASCADE
);

CREATE TABLE Utilisateur (
    mail VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    adresse TEXT NOT NULL,
    CONSTRAINT chk_mail_format CHECK (mail LIKE '%@%.%')
);


CREATE TABLE Vente (
    ID_Vente SERIAL PRIMARY KEY,
    montante TINYINT(1) DEFAULT 1, -- 1 = TRUE, 0 = FALSE
    limite_offre TINYINT(1) DEFAULT 0, -- pareil
    revocable TINYINT(1) DEFAULT 0, -- pareil
    prix_depart FLOAT NOT NULL,
    date_fin TIMESTAMP DEFAULT NULL,
    prix_descente FLOAT DEFAULT 0,
    Stock_lot INT NOT NULL,
    ID_salle_vente BIGINT UNSIGNED NOT NULL,
    ID_produit BIGINT UNSIGNED NOT NULL,
    mail VARCHAR(50) NOT NULL,
    CONSTRAINT fk_vente_salle FOREIGN KEY (ID_salle_vente)
    REFERENCES Salle_de_vente(ID_salle_vente) ON DELETE CASCADE,
    CONSTRAINT fk_vente_produit FOREIGN KEY (ID_produit)
    REFERENCES Produit(ID_produit),
    CONSTRAINT fk_vente_utilisateur FOREIGN KEY (mail)
    REFERENCES Utilisateur(mail) ON DELETE CASCADE,
    CONSTRAINT chk_prix_depart CHECK (prix_depart >= 0),
    CONSTRAINT chk_Stock_lot CHECK (Stock_lot >= 0)
);

CREATE TABLE Offre (
    ID_offre SERIAL PRIMARY KEY,
    mail VARCHAR(50) NOT NULL,
    ID_Vente BIGINT UNSIGNED NOT NULL,
    date_offre TIMESTAMP NOT NULL,
    prix_achat FLOAT NOT NULL,
    quantite INT NOT NULL,
    CONSTRAINT fk_offre_utilisateur FOREIGN KEY (mail)
    REFERENCES Utilisateur(mail) ON DELETE CASCADE,
    CONSTRAINT fk_offre_vente FOREIGN KEY (ID_Vente)
    REFERENCES Vente(ID_Vente) ON DELETE CASCADE,
    CONSTRAINT chk_prix_achat CHECK (prix_achat >= 0),
    CONSTRAINT chk_quantite CHECK (quantite > 0)
);

CREATE VIEW Prix_achat AS
    SELECT MAX(prix_achat/quantite) AS ratio, ID_Vente FROM Offre GROUP BY ID_Vente;