package com.belec.app.sql;

import com.belec.app.Main;
import com.belec.app.data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Requests {

    // Méthode pour récupérer tous les produits d'une catégorie
    public static Collection<Product> retrievesAllProducts(Category category) {
        Connection connection = Main.getDbConnection();
        Collection<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_PRODUCTS_BY_CATEGORY);
            statement.setString(1, category.getName());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("ID_produit"),
                        resultSet.getString("nom"),
                        resultSet.getDouble("prix_revient"),
                        resultSet.getString("Nom_categorie")
                );
                products.add(product);
            }
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Product retrievesProduct(int productId) {
        Connection connection = Main.getDbConnection();
        Product product = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_PRODUCTS_BY_ID);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = new Product(resultSet.getInt("ID_produit"),
                                        resultSet.getString("nom"),
                                        resultSet.getFloat("prix_revient"),
                                        resultSet.getString("Nom_categorie"));
            }
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Méthode pour récupérer toutes les catégories
    public static Collection<Category> retrievesAllCategories() {
        Connection connection = Main.getDbConnection();
        Collection<Category> categories = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_CATAGORIES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getString("Nom_categorie"),
                        resultSet.getString("description")
                );
                categories.add(category);
            }
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static boolean pushNewUser(User user) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_INSERT_USER);
            statement.setString(1, user.getMail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getAddress());
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.commit();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException(connection, e);
        }
        return false;
    }

    public static Collection<SalesRoom> retrievesAllSalesRoomsInCategory(Category category) {
        Connection connection = Main.getDbConnection();
        Collection<SalesRoom> salesRooms = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_SALES_ROOM_IN_CATEGORY);
            statement.setString(1, category.getName());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SalesRoom salesRoom = new SalesRoom(
                        resultSet.getInt("ID_salle_vente"),
                        resultSet.getString("Nom_categorie")
                );
                salesRooms.add(salesRoom);
            }
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesRooms;
    }

    public static boolean pushNewSale(Sale sale) {
        Connection connection = Main.getDbConnection();
        try {
            if(sale instanceof LimitedSale) {
                PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_INSERT_NEW_SALE);
                statement.setBoolean(1, sale.isIncrease());
                statement.setBoolean(2, sale.isLimitedOffers());
                statement.setBoolean(3, sale.isCancelable());
                statement.setFloat(4, sale.getPrixDepart()); // Utilisation de `getStartingPrice`
                statement.setTimestamp(5, ((LimitedSale) sale).getEndDate());
                statement.setFloat(6, sale.getPriceDecreaseAmount());
                statement.setInt(7, sale.getQuantity()); // Utilisation de `getQuantity`
                statement.setInt(8, sale.getSaleRoomId());
                statement.setInt(9, sale.getProductId());// ID de la salle de vente
                statement.setString(10, sale.getOwner());
                statement.execute();
                connection.commit();
                statement.close();
            }
            else {
                PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_INSERT_NEW_SALE);
                statement.setBoolean(1, sale.isIncrease());
                statement.setBoolean(2, sale.isLimitedOffers());
                statement.setBoolean(3, sale.isCancelable());
                statement.setFloat(4, sale.getPrixDepart()); // Utilisation de `getStartingPrice`
                statement.setTimestamp(5, null);
                statement.setFloat(6, sale.getPriceDecreaseAmount());
                statement.setInt(7, sale.getQuantity()); // Utilisation de `getQuantity`
                statement.setInt(8, sale.getSaleRoomId());
                statement.setInt(9, sale.getProductId());// ID de la salle de vente
                statement.setString(10, sale.getOwner());
                statement.execute();
                connection.commit();
                statement.close();
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(connection, e);
        }
        return false;
    }

    public static Collection<Characteristics> retrievesAllCharacteristics(Product product) {
        Connection connection = Main.getDbConnection();
        ArrayList<Characteristics> characteristics = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_CARATERITICS_OF_PRODUCT);
            statement.setInt(1, product.getId());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Characteristics characteristic = new Characteristics(
                        resultSet.getInt("ID_produit"),
                        resultSet.getString("Nom_Caracteristique"),
                        resultSet.getString("valeur")
                );
                characteristics.add(characteristic);
            }
            connection.commit();
            return characteristics;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour récupérer toutes les ventes d'une salle de vente
    public static Collection<Sale> retrievesAllSales(SalesRoom salesRoom) {
        Connection connection = Main.getDbConnection();
        Collection<Sale> sales = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_SALES_BY_ROOM);
            statement.setInt(1, salesRoom.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Sale sale = null;
                if(resultSet.getDate("date_fin") == null) {
                    sale = new Sale(resultSet.getInt("ID_Vente"),
                            resultSet.getInt("ID_salle_vente"),
                            resultSet.getInt("ID_Produit"),
                            resultSet.getString("mail"),
                            resultSet.getInt("montante") == 1,
                            resultSet.getInt("revocable") == 1,
                            resultSet.getInt("limite_offre") == 1,
                            resultSet.getFloat("prix_depart"),
                            resultSet.getInt("Stock_lot"),
                            resultSet.getFloat("prix_descente"));
                }
                else {
                    sale = new LimitedSale(resultSet.getInt("ID_Vente"),
                            resultSet.getInt("ID_salle_vente"),
                            resultSet.getInt("ID_Produit"),
                            resultSet.getString("mail"),
                            resultSet.getInt("montante") == 1,
                            resultSet.getInt("revocable") == 1,
                            resultSet.getInt("limite_offre") == 1,
                            resultSet.getFloat("prix_depart"),
                            resultSet.getInt("Stock_lot"),
                            resultSet.getTimestamp("date_fin"),
                            resultSet.getFloat("prix_descente"));
                }
                sales.add(sale);
            }
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return sales;
    }

    // Méthode pour insérer une nouvelle offre
    public static boolean pushNewOffer(Offer offer) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement1 = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_SALE);
            statement1.setInt(1, offer.getIdSale());
            statement1.execute();
            ResultSet resultSet = statement1.getResultSet();
            Sale sale = null;
            if(resultSet.next()) {
                if(resultSet.getDate("date_fin") == null) {
                    sale = new Sale(resultSet.getInt("ID_Vente"),
                            resultSet.getInt("ID_salle_vente"),
                            resultSet.getInt("ID_Produit"),
                            resultSet.getString("mail"),
                            resultSet.getInt("montante") == 1,
                            resultSet.getInt("revocable") == 1,
                            resultSet.getInt("limite_offre") == 1,
                            resultSet.getFloat("prix_depart"),
                            resultSet.getInt("Stock_lot"),
                            resultSet.getFloat("prix_descente"));
                }
                else {
                    sale = new LimitedSale(resultSet.getInt("ID_Vente"),
                            resultSet.getInt("ID_salle_vente"),
                            resultSet.getInt("ID_Produit"),
                            resultSet.getString("mail"),
                            resultSet.getInt("montante") == 1,
                            resultSet.getInt("revocable") == 1,
                            resultSet.getInt("limite_offre") == 1,
                            resultSet.getFloat("prix_depart"),
                            resultSet.getInt("Stock_lot"),
                            resultSet.getTimestamp("date_fin"),
                            resultSet.getFloat("prix_descente"));
                }
            }
            else {
                connection.rollback();
                return false;
            }
            if(sale.isLimitedOffers()){
                PreparedStatement statement2 = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_OFFER_WITH_MAIL);
                statement2.setInt(1, offer.getIdSale());
                statement2.setString(2, offer.getRequest());
                statement2.execute();
                ResultSet res = statement2.getResultSet();
                if(res.next()){
                    connection.rollback();
                    return false;
                }
            }
            if(sale instanceof LimitedSale){
                if(offer.getDate().compareTo(((LimitedSale) sale).getEndDate()) > 0){
                    connection.rollback();
                    return false;
                }
            }
            PreparedStatement statement3 = connection.prepareStatement(SQLRequests.SQL_INSERT_NEW_OFFER);
            statement3.setString(1, offer.getRequest());
            statement3.setInt(2, offer.getIdSale());
            statement3.setTimestamp(3, offer.getDate());
            statement3.setDouble(4, offer.getPrice());
            statement3.setInt(5, offer.getQuantity());
            int rowsAffected = statement3.executeUpdate();
            connection.commit();
            statement3.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException(connection, e);
        }
        return false;
    }

    public static boolean deleteSale(Sale sale){
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_DELETE_SALE);
            statement.setInt(1, sale.getId());
            statement.execute();
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Collection<Offer> getAllOfferOrderedByInterest(Sale sale) {
        Connection connection = Main.getDbConnection();
        Collection<Offer> offers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_OFFERS_BY_INTEREST);
            statement.setInt(1, sale.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Offer offer = new Offer(
                        resultSet.getString("mail"),
                        resultSet.getInt("ID_offre"),
                        resultSet.getTimestamp("date_offre"),
                        resultSet.getDouble("prix_achat"),
                        resultSet.getInt("quantite"),
                        resultSet.getInt("ID_Vente")
                );
                offers.add(offer);
            }
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return offers;
    }

    // Méthode utilitaire pour récupérer un utilisateur
    public static User retrieveUser(String mail) {
        Connection connection = Main.getDbConnection();
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_USER);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("Mail"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse")
                );
            }
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    // Méthode utilitaire pour gérer les erreurs SQL
    private static void handleSQLException(Connection connection, SQLException e) {
        e.printStackTrace();
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();
        }
    }

     public static boolean acceptOffer(int offerId) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement getOfferStatement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_OFFER);
            getOfferStatement.setInt(1, offerId);
            ResultSet offerResult = getOfferStatement.executeQuery();
            if (!offerResult.next()) {
                connection.rollback();
                return false; // offre introuvable
            }

            
            String mail = offerResult.getString("mail");
            int saleId = offerResult.getInt("ID_Vente");
            int quantity = offerResult.getInt("quantite");
            double price = offerResult.getDouble("prix_achat");

            // Mettre à jour le stock du lot
            PreparedStatement updateStockStatement = connection.prepareStatement(SQLRequests.SQL_UPDATE_STOCK_LOT);
            updateStockStatement.setInt(1, quantity); 
            updateStockStatement.setInt(2, saleId);
            int rowsAffected = updateStockStatement.executeUpdate();

            if (rowsAffected == 0) {
                connection.rollback();
                return false; // Échec de la mise à jour du stock
            }

            PreparedStatement deleteOffer = connection.prepareStatement(SQLRequests.SQL_DELETE_OFFER);
            deleteOffer.setInt(1, offerId);
            int rowsAffected2 = deleteOffer.executeUpdate();
            if (rowsAffected2 == 0) {
                connection.rollback();
                return false;
            }

            // Ici j'ai choisi de supprimer les offres incompatibles, je sais pas si c'est une bonne idée ?
            PreparedStatement rejectOffersStatement = connection.prepareStatement(SQLRequests.SQL_REJECT_INCOMPATIBLE_OFFERS);
            rejectOffersStatement.setInt(1, saleId);
            rejectOffersStatement.setInt(2, saleId);
            rejectOffersStatement.executeUpdate();

            PreparedStatement deleteSaleStatement = connection.prepareStatement(SQLRequests.SQL_DELETE_INVALID_SALE);
            deleteSaleStatement.setInt(1, saleId);
            deleteSaleStatement.executeUpdate();

            // Commit de la transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;
        }
    }

    public static boolean denyOffer(int offerId) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_DELETE_OFFER);
            statement.setInt(1, offerId);
            statement.executeUpdate();
            connection.commit();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Sale retrieveSale(int saleId) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_SALE);
            statement.setInt(1, saleId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            Sale sale = null;
            if(resultSet.next()) {
                if(resultSet.getDate("date_fin") == null) {
                    sale = new Sale(resultSet.getInt("ID_Vente"),
                            resultSet.getInt("ID_salle_vente"),
                            resultSet.getInt("ID_Produit"),
                            resultSet.getString("mail"),
                            resultSet.getInt("montante") == 1,
                            resultSet.getInt("revocable") == 1,
                            resultSet.getInt("limite_offre") == 1,
                            resultSet.getFloat("prix_depart"),
                            resultSet.getInt("Stock_lot"),
                            resultSet.getFloat("prix_descente"));
                }
                else {
                    sale = new LimitedSale(resultSet.getInt("ID_Vente"),
                            resultSet.getInt("ID_salle_vente"),
                            resultSet.getInt("ID_Produit"),
                            resultSet.getString("mail"),
                            resultSet.getInt("montante") == 1,
                            resultSet.getInt("revocable") == 1,
                            resultSet.getInt("limite_offre") == 1,
                            resultSet.getFloat("prix_depart"),
                            resultSet.getInt("Stock_lot"),
                            resultSet.getTimestamp("date_fin"),
                            resultSet.getFloat("prix_descente"));
                }
            }
            else {
                connection.rollback();
                return null;
            }
            connection.commit();
            return sale;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static float retrieveBestOffer(int saleId) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_RETRIEVE_BEST_OFFER);
            statement.setInt(1, saleId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()) {
                connection.commit();
                return resultSet.getFloat("ratio");
            }
            connection.rollback();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void deleteUser(String mail) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLRequests.SQL_DELETE_USER);
            statement.setString(1, mail);
            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static boolean decreaseSale(String mail) {
        Connection connection = Main.getDbConnection();
        try {
            PreparedStatement decreaseSalePrice = connection.prepareStatement(SQLRequests.SQL_DECREASE_SALE);
            decreaseSalePrice.setString(1, mail);
            decreaseSalePrice.executeUpdate();
            PreparedStatement setToZero = connection.prepareStatement(SQLRequests.SQL_HANDLE_INVALID_SALES);
            setToZero.setString(1, mail);
            setToZero.executeUpdate();
            connection.commit();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
