package com.jongeorge.isin.dao;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class CountryCodeDAO {
      private static final String GET_EVERY_COUNTRY_CODE =
              "SELECT * FROM `country_codes` ORDER BY `country`";

      private static final String GET_COUNTRY_CODE =
              "SELECT * FROM `country_codes` WHERE `two_letter_code` = ? LIMIT 1";

      private static MysqlDataSource getDataSource() {
            Properties props = new Properties();

            String file = "private/db.properties";

            try(FileInputStream in = new FileInputStream(file)) {
                  props.load(in);
            }
            catch(IOException e) {
                  e.printStackTrace();
            }

            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL(props.getProperty("db.url"));
            dataSource.setUser(props.getProperty("db.user"));
            dataSource.setPassword(props.getProperty("db.passwd"));

            return dataSource;
      }

      protected static Connection getConnection() {
            MysqlDataSource dataSource = getDataSource();

            Connection connection = null;
            try {
                  connection = dataSource.getConnection();
            }
            catch(SQLException ex) {
                  ex.printStackTrace();
            }
            return connection;
      }

      public static ArrayList<String> getAllCountryCodes() {
            ArrayList<String> countryCodes = null;

            Connection connection = getConnection();
            try {
                  PreparedStatement preparedStatement =
                          connection.prepareStatement(GET_EVERY_COUNTRY_CODE);

                  ResultSet result = preparedStatement.executeQuery();

                  countryCodes = new ArrayList<>();
                  while(result.next()) {
                        countryCodes.add(result.getString("two_letter_code"));
                  }
            }
            catch(SQLException e) {
                  e.printStackTrace();
            }
            return countryCodes;
      }

      public static boolean isValidTwoDigitCode(String code) {
            boolean isValid = false;

            Connection connection = getConnection();
            try {
                  PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNTRY_CODE);
                  preparedStatement.setString(1, code);
                  ResultSet result = preparedStatement.executeQuery();

                  if(result.next()) {
                        isValid = true;
                  }
            }
            catch(SQLException ex) {
                  ex.printStackTrace();
            }
            return isValid;
      }
}
