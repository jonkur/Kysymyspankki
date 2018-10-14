package kysymyspankki.dao;

import java.sql.*;
import java.util.*;

/**
 *
 * @author jonkur
 */
public interface Dao<T, K> {
    T findOne(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    T saveOrUpdate(T obj) throws SQLException;
    boolean delete(K key) throws SQLException;
}
