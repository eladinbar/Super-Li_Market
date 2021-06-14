package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.DalItem;
import DataAccessLayer.DalObjects.SupplierObjects.DalSupplierCard;
import DataAccessLayer.DalObjects.SupplierObjects.DalAgreementItems;

import java.sql.*;
import java.util.List;

public class AgreementItemsDalController extends DalController<DalAgreementItems> {
    private static AgreementItemsDalController instance = null;
    public final static String AGREEMENT_ITEMS_TABLE_NAME = "Agreement_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private AgreementItemsDalController() throws SQLException {
        super(AGREEMENT_ITEMS_TABLE_NAME);
    }

    public static AgreementItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new AgreementItemsDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalAgreementItems.productIdColumnName + " INTEGER NOT NULL," +
                    DalAgreementItems.supplierIdColumnName + " INTEGER NOT NULL,"+
                    DalAgreementItems.productCompIdColumnName + " INTEGER NOT NULL," + DalAgreementItems.priceColumnName + " REAL NOT NULL," +
                    "PRIMARY KEY (" + DalAgreementItems.productIdColumnName +", "+ DalAgreementItems.supplierIdColumnName + ")," +
                    "FOREIGN KEY (" + DalAgreementItems.productIdColumnName + ")" + "REFERENCES " + DalItem.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME + ") ON DELETE CASCADE " +
                    "FOREIGN KEY (" + DalAgreementItems.supplierIdColumnName + ")" + "REFERENCES " + DalSupplierCard.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME + ") ON DELETE CASCADE " +
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalAgreementItems agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, agreementItem.getProductId());
            stmt.setInt(2, agreementItem.getSupplierId());
            stmt.setInt(3, agreementItem.getProductCompId());
            stmt.setDouble(4, agreementItem.getPrice());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalAgreementItems agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + DalAgreementItems.productIdColumnName +"=? AND "+
                    DalAgreementItems.supplierIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, agreementItem.getProductId());
            stmt.setInt(2, agreementItem.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalAgreementItems agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + DalAgreementItems.priceColumnName +
                    "=?, " + DalAgreementItems.productCompIdColumnName + "=? WHERE(" + DalAgreementItems.productIdColumnName +"=? AND "+
                    DalAgreementItems.supplierIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, agreementItem.getPrice());
            stmt.setInt(2, agreementItem.getProductCompId());
            stmt.setInt(3, agreementItem.getProductId());
            stmt.setInt(4, agreementItem.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(DalAgreementItems agreementItem) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                isDesired = resultSet.getInt(1) == agreementItem.getProductId() && resultSet.getInt(2)== agreementItem.getSupplierId();
                if (isDesired) {
                    agreementItem.setProductCompIdLoad(resultSet.getInt(3));
                    agreementItem.setPriceLoad(resultSet.getInt(4));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    public boolean select(DalAgreementItems agreement, List<DalAgreementItems> agreements) throws SQLException {
        boolean hasItem = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(2).equals(""+agreement.getSupplierId());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    int productId = resultSet.getInt(1);
                    int compId = resultSet.getInt(3);
                    double price = resultSet.getDouble(4);
                    DalAgreementItems savedAgreement = new DalAgreementItems(productId,agreement.getSupplierId(), compId,(int)price);
                    agreements.add(savedAgreement);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
