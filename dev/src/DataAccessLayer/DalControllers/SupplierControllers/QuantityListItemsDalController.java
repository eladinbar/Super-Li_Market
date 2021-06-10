package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.DalItem;
import DataAccessLayer.DalObjects.SupplierObjects.DalQuantityListItems;
import DataAccessLayer.DalObjects.SupplierObjects.DalSupplierCard;

import java.sql.*;
import java.util.List;

public class QuantityListItemsDalController extends DalController$<DalQuantityListItems> {
    private static QuantityListItemsDalController instance = null;
    public final static String QUANTITY_LIST_ITEMS_TABLE_NAME = "Quantity_List_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private QuantityListItemsDalController() throws SQLException {
        super(QUANTITY_LIST_ITEMS_TABLE_NAME);
    }

    public static QuantityListItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new QuantityListItemsDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalQuantityListItems.productIdColumnName + " INTEGER NOT NULL," +
                    DalQuantityListItems.supplierIdColumnName + " TEXT NOT NULL,"+
                    DalQuantityListItems.amountColumnName+ " INTEGET NOT NULL," + DalQuantityListItems.discountColumnName+ " REAL NOT NULL,"+
                    "PRIMARY KEY (" + DalQuantityListItems.productIdColumnName + ", "+
                    DalQuantityListItems.supplierIdColumnName+ "),"+
                    "FOREIGN KEY (" + DalQuantityListItems.productIdColumnName + ")" + "REFERENCES " + DalItem.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE CASCADE "+
                    "FOREIGN KEY (" + DalQuantityListItems.supplierIdColumnName + ")" + "REFERENCES " + DalSupplierCard.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME +") ON DELETE CASCADE "+
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalQuantityListItems quantityListItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantityListItem.getProductId());
            stmt.setString(2, quantityListItem.getSupplierId());
            stmt.setInt(3, quantityListItem.getAmount());
            stmt.setDouble(4, quantityListItem.getDiscount());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalQuantityListItems quantityListItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + DalQuantityListItems.productIdColumnName+ "=? AND " +
                    DalQuantityListItems.supplierIdColumnName+"=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantityListItem.getProductId());
            stmt.setString(2, quantityListItem.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalQuantityListItems quantityListItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + DalQuantityListItems.amountColumnName +
                    "=?, "+ DalQuantityListItems.discountColumnName+"=? WHERE(" + DalQuantityListItems.productIdColumnName+ "=? AND "+
                    DalQuantityListItems.supplierIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, quantityListItem.getAmount());
            stmt.setDouble(2, quantityListItem.getDiscount());
            stmt.setInt(3, quantityListItem.getProductId());
            stmt.setString(4, quantityListItem.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(DalQuantityListItems quantityListItem) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getInt(1) == quantityListItem.getProductId()
                && resultSet.getString(2).equals(quantityListItem.getSupplierId());
                if (isDesired) {
                    quantityListItem.setAmountLoad(resultSet.getInt(3));
                    quantityListItem.setDiscountLoad(resultSet.getInt(4));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    public boolean select(DalQuantityListItems ql, List<DalQuantityListItems> qlList) throws SQLException {
        boolean hasItem = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(2).equals(ql.getSupplierId());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    int productId = resultSet.getInt(1);
                    int amount = resultSet.getInt(3);
                    double discount = resultSet.getDouble(4);
                    DalQuantityListItems savedQl = new DalQuantityListItems(productId,ql.getSupplierId(), amount, (int)discount);
                    qlList.add(savedQl);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
