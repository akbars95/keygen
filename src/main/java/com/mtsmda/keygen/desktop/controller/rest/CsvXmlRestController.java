package com.mtsmda.keygen.desktop.controller.rest;

import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.MapHelper;
import com.mtsmda.keygen.desktop.configuration.FreeMarkerFacade;
import com.mtsmda.keygen.desktop.model.City;
import com.mtsmda.keygen.desktop.model.Country;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MTSMDA on 15.09.2016.
 */
@RestController
@RequestMapping(value = "/api/desktop", method = RequestMethod.GET)
public class CsvXmlRestController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("freeMarkerFacade")
    private FreeMarkerFacade freeMarkerFacade;

    private static final List<User> USERS;

    static {
        USERS = ListHelper.getListWithData(new User("ion.ionita@mail.md", LocalDateTime.now(), "ion.ionita", "ion.ionita12345"),
                new User("jack.graham@gmail.com", LocalDateTime.now().plusDays(9), "jack.graham", "jack.graham12345"),
                new User("ivanov.ivan@mail.ru", LocalDateTime.now().minusMonths(2), "ivanov.ivan", "ivanov.ivan12345"),
                new User("dmitro.kovalenko@mail.ua", LocalDateTime.now().plusWeeks(5), "dmitro.kovalenko", "dmitro.kovalenko12345"),
                new User("vasile.vasilciuc@mail.md", LocalDateTime.now().plusYears(1), "vasile.vasilciuc", "vasile.vasilciuc12345"));
    }

    /*--------------------------------------------------------------------------------*/

    @RequestMapping(value = "/users/xml", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersXML() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_XML_VALUE);
        return new ResponseEntity<>(USERS, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/xml2", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public List<User> getUsersXML2() {
        return USERS;
    }

    @RequestMapping(value = "/users/xml3", method = RequestMethod.GET)
    public List<User> getUsersXML3(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        return USERS;
    }

    @RequestMapping(value = "/users/xml4", method = RequestMethod.GET)
    public List<User> getUsersXML4() {
        return USERS;
    }

    @RequestMapping(value = "/users/xml5", method = RequestMethod.GET)
    public List<User> getUsersXML5() {
        return USERS;
    }

    @RequestMapping(value = "/users/download/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public List<User> getUsersXMLDownload(HttpServletRequest request, HttpServletResponse response) {
        return USERS;
    }

    @RequestMapping(value = "/users/download/xml2", method = RequestMethod.GET)
    public List<User> getUsersXMLDownload2(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.addHeader("Content-Disposition", "attachment; filename=downloadXML2" + LocalDateTimeHelper.localDateTime(LocalDateTime.now(), LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT) + ".xml");
        return USERS;
    }

    @RequestMapping(value = "/users/download/xml3", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersXMLDownload3() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        responseHeaders.add("Content-Disposition", "attachment; filename=downloadXML3" + LocalDateTimeHelper.localDateTime(LocalDateTime.now(), LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT) + ".xml");
        return new ResponseEntity<>(USERS, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/download/xml4", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public List<User> getUsersXMLDownloadViaFreemarker(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        try {
            Writer writer = new StringWriter();
            List<City> moldova = ListHelper.getListWithData(new City("Chisinau"), new City("Belicy"), new City("Tiraspoli"), new City("Komrat"), new City("Cahul"));

            List<City> ukraine = ListHelper.getListWithData(new City("Kiev"), new City("Odessa"), new City("Dnepr"), new City("Cernigov"), new City("Nikolaev"));

            List<City> russia = ListHelper.getListWithData(new City("Moscow"), new City("Sankt-Peterburg"), new City("Nijnii_Novgorod"), new City("Tula"), new City("Voronej"));

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("users", USERS);
            parameters.put("countries", ListHelper.getListWithData(new Country("Moldova", moldova), new Country("Ukraine", ukraine), new Country("Russia", russia)));

            freeMarkerFacade.writeContent("userXMLTemplate.ftl", writer, parameters);

            OutputStream outputStream = response.getOutputStream();
            outputStream.write(writer.toString().getBytes());
            response.setContentLength(writer.toString().length());
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.addHeader("Content-Disposition", "attachment; filename=downloadXML4" + LocalDateTimeHelper.localDateTime(LocalDateTime.now(), LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT) + ".xml");
        return USERS;
    }

    @RequestMapping(value = "/users/json", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersJSON() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity<>(USERS, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/json2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getUsersJSON2() {
        return USERS;
    }

    @RequestMapping(value = "/users/json3", method = RequestMethod.GET)
    public List<User> getUsersJSON3(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        return USERS;
    }

    @RequestMapping(value = "/users/download/json", method = RequestMethod.GET)
    public String getUsersJSONDownload(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return getJsonOutput();
    }

    @RequestMapping(value = "/users/download/json2", method = RequestMethod.GET)
    public ResponseEntity<String> getUsersJSONDownload2() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return new ResponseEntity<>(getJsonOutput(), responseHeaders, HttpStatus.OK);
    }

    private String getJsonOutput() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("users", jsonArray);
        USERS.forEach(user -> {
            JSONObject jsonObjectUser = new JSONObject();
            jsonObjectUser.put("userEmail", user.getUserEmail());
            jsonObjectUser.put("userCreatedUser", user.getUserCreatedUser());
            jsonObjectUser.put("userName", user.getUserName());
            jsonObjectUser.put("userPassword", user.getUserPassword());
            jsonArray.add(jsonObjectUser);
        });
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/users/csv", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<List<User>> getUsersCSV() {

        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator('\n');

        try (FileWriter fileWriter = new FileWriter(LocalDateTime.now() + ".csv")) {
            final CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
            csvPrinter.printRecord("userEmail,userCreatedUser,userName,userPassword");
            USERS.forEach(user -> {
                try {
                    csvPrinter.printRecord(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.flush();
        } catch (Exception e) {

        }

        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(USERS, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/csv2", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> getUsersCSV2() {

        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(getCSVOutput(), responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/download/csv", method = RequestMethod.GET)
    public String getUsersCSVDownload(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return getCSVOutput();
    }

    @RequestMapping(value = "/users/download/csv2", method = RequestMethod.GET)
    public ResponseEntity<String> getUsersCSVDownload2() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return new ResponseEntity<>(getCSVOutput(), responseHeaders, HttpStatus.OK);
    }

    private String getCSVOutput() {
        String enter = "\n";
        String CSVDelimiter = ",";
        StringBuilder result = new StringBuilder("userEmail,userCreatedUser,userName,userPassword").append(enter);
        USERS.forEach(user -> {
            result.append(user.getUserEmail()).append(CSVDelimiter)
                    .append(user.getUserCreatedUser()).append(CSVDelimiter)
                    .append(user.getUserName()).append(CSVDelimiter)
                    .append(user.getUserPassword()).append(enter);
        });
        return result.toString();
    }

    @RequestMapping(value = "/users/download/text", method = RequestMethod.GET)
    public ResponseEntity<String> getText(HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders responseHeaders = new HttpHeaders();
        StringBuilder result = new StringBuilder();

        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("");
        System.out.println("realPath - " + realPath);
        String fullPath = realPath + "/downloads/keygeneration.log";
        File downloadFile = new File(fullPath);
        try (InputStream inputStream = new FileInputStream(downloadFile);) {
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                result.append(new String(buffer, 0, bytesRead));
            }
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println(e.getMessage() + " = " + e);
        }

        responseHeaders.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return new ResponseEntity<>(result.toString(), responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/download/html", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getUsersHTMLDownloadViaFreemarker(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        Writer writer = null;
        try {
            writer = new StringWriter();

            freeMarkerFacade.writeContent("html/page_html.ftl", writer, new HashMap<>());

            OutputStream outputStream = response.getOutputStream();
            outputStream.write(writer.toString().getBytes());
            response.setContentLength(writer.toString().length());
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.addHeader("Content-Disposition", "attachment; filename=downloadHTML" + LocalDateTimeHelper.localDateTime(LocalDateTime.now(), LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT) + ".xml");
    }

}