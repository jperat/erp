package com.erp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtility {

    private DAOUtility() {

    }

    public static void silentClose( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close ResultSet" + e.getMessage() );
            }
        }
    }

    public static void silentClose( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close Statement" + e.getMessage() );
            }
        }
    }

    public static void silentClose( Connection connection ) {
        if ( connection != null ) {
            try {
                connection.close();
            } catch ( SQLException e ) {
                System.out.println( "Failed to close Connection" + e.getMessage() );
            }
        }
    }

    public static void silentsCloses( Statement statement, Connection connection ) {
        silentClose( statement );
        silentClose( connection );
    }

    public static void silentsCloses( ResultSet resultSet, Statement statement, Connection connection ) {
        silentClose( resultSet );
        silentClose( statement );
        silentClose( connection );
    }

    public static PreparedStatement initializePreparedQuery( Connection connection, String sql,
            boolean returnGeneratedKeys,
            Object... objects ) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement( sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
//        PreparedStatement preparedStatement;
//        if (returnGeneratedKeys) {
//            preparedStatement = connection.prepareStatement( sql,
//                Statement.RETURN_GENERATED_KEYS );
//        } else {
//            preparedStatement = connection.prepareStatement( sql,
//                Statement.NO_GENERATED_KEYS );
//        }
        for ( int i = 0; i < objects.length; i++ ) {
            preparedStatement.setObject( i + 1, objects[i] );
        }
        return preparedStatement;
    }

}
