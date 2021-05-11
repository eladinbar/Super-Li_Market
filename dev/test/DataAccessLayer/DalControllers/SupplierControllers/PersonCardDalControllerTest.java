package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalObjects.SupplierObjects.PersonCardDal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonCardDalControllerTest {
    static PersonCardDalController pc;
    PersonCardDal pcd;
    @BeforeAll
    static void setup() throws SQLException {
        pc = PersonCardDalController.getInstance();

    }

    @Test
    void insert() throws SQLException {
        pcd = new PersonCardDal("hi", "bye","enail@mail.com","333333333","0544675421");
        boolean res = pc.insert(pcd);
        //assert
        assertTrue(res);
    }

    //@Test
    void delete() throws SQLException {
        pcd = new PersonCardDal("hi", "bye","enail@mail.com","333333333","0544675421");
        boolean res = pc.delete(pcd);
        //assert
        assertTrue(res);
    }

    @Test
    void update() throws SQLException {
        pcd = new PersonCardDal("hi", "hello","enail@mail.com","333333333","0544675421");
        boolean res = pc.delete(pcd);
        //assert
        assertTrue(res);
    }

//    @Test
    void select() throws SQLException {
        pcd = new PersonCardDal("hi", "bye","enail@mail.com","333333333","0544675421");
        boolean res = pc.select(pcd);
        //assert
        assertTrue(res);


    }
}