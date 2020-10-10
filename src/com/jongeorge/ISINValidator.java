package com.jongeorge;

import com.jongeorge.isin.dao.CountryCodeDAO;

public class ISINValidator {

      public Boolean validate(String isin) {
            if(isin.length() != 12) {
                  return false;
            }

            String countryCode = isin.substring(0, 2);
            boolean isValidCode = CountryCodeDAO.isValidTwoDigitCode(countryCode);

            if(!isValidCode) {
                  return false;
            }

            String securityCode = isin.substring(2, 10);
            String checksum = isin.substring(10);

            return true;
      }

}
