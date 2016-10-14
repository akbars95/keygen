<%--
  Created by IntelliJ IDEA.
  User: MTSMDA
  Date: 13.09.2016
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>

<h2>XMLs</h2>

<c:url value="/api/desktop/users/xml" var="users_xml"/>
<a href="${users_xml}">users XML</a>
<br>
<c:url value="/api/desktop/users/xml2" var="users_xml2"/>
<a href="${users_xml2}">users XML2</a>
<br>
<c:url value="/api/desktop/users/xml3" var="users_xml3"/>
<a href="${users_xml3}">users XML3</a>
<br>
<c:url value="/api/desktop/users/xml4" var="users_xml4"/>
<a href="${users_xml4}">users XML4</a>
<br>
<c:url value="/api/desktop/users/xml5" var="users_xml5"/>
<a href="${users_xml5}">users XML5</a>
<br>
<c:url value="/api/desktop/users/download/xml" var="users_xml_download"/>
<a href="${users_xml_download}">users XML download</a>
<br>
<c:url value="/api/desktop/users/download/xml2" var="users_xml_download2"/>
<a href="${users_xml_download2}">users XML download2</a>
<br>
<c:url value="/api/desktop/users/download/xml3" var="users_xml_download3"/>
<a href="${users_xml_download3}">users XML download3</a>
<br>
<c:url value="/api/desktop/users/download/xml4" var="users_xml_download4"/>
<a href="${users_xml_download4}">users XML download4</a>

<hr>

<h2>JSONs</h2>

<c:url value="/api/desktop/users/json" var="users_json"/>
<a href="${users_json}">users JSON</a>
<br>
<c:url value="/api/desktop/users/json2" var="users_json2"/>
<a href="${users_json2}">users JSON2</a>
<br>
<c:url value="/api/desktop/users/json3" var="users_json3"/>
<a href="${users_json3}">users JSON3</a>
<br>
<c:url value="/download/json" var="download_json"/>
<a href="${download_json}">download JSON</a>
<br>
<c:url value="/api/desktop/users/download/json" var="download_json1"/>
<a href="${download_json1}">download JSON1</a>
<br>
<c:url value="/api/desktop/users/download/json2" var="download_json2"/>
<a href="${download_json2}">download JSON2</a>
<br>

<h2>CSVs</h2>
<c:url value="/api/desktop/users/csv" var="users_csv"/>
<a href="${users_csv}">users CSV</a>
<br>
<c:url value="/api/desktop/users/csv2" var="users_csv2"/>
<a href="${users_csv2}">users CSV2</a>
<br>
<c:url value="/api/desktop/users/download/csv" var="users_download_csv"/>
<a href="${users_download_csv}">users download CSV</a>
<br>
<c:url value="/api/desktop/users/download/csv2" var="users_download_csv2"/>
<a href="${users_download_csv2}">users download CSV2</a>
<br>
<c:url value="/api/desktop/users/download/text" var="users_download_text"/>
<a href="${users_download_text}">users download Text</a>
<br>
<c:url value="/api/desktop/users/download/html" var="users_download_html"/>
<a href="${users_download_html}">users download HTML</a>
<br>



</body>
</html>
