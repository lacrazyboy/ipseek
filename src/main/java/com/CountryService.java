package com;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Subdivision;

public class CountryService {

    private final static Logger log = LoggerFactory.getLogger(CountryService.class);
    
    public static Map<String, Object> getIpInfo(String ip) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DatabaseReader reader = BasicDataBase.getDatabaseReader("");
            log.info("ip:" + ip);
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(ipAddress);

            Country country = response.getCountry();
            map.put("country", country.getName());
            map.put("country_id", country.getIsoCode());
            Subdivision subdivision = response.getMostSpecificSubdivision();
            map.put("area", subdivision.getName());
            map.put("area_id", subdivision.getIsoCode());

            City city = response.getCity();
            map.put("city", city.getName());

            Location location = response.getLocation();
            map.put("latitude", location.getLatitude());
            map.put("longitude", location.getLongitude());

            map.put("ip",ip);
            log.info("ip city:" + city.getName());
        } catch (UnknownHostException e) {
            log.info(e.getMessage());
        } catch (IOException e) {
            log.info(e.getMessage());
        } catch (GeoIp2Exception e) {
            log.info(e.getMessage());
        }
        return map;

    }

}
