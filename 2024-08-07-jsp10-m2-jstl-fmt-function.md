---
layout: single
title: 2024/08/07 JSP10-M2-Jstl-Core2
---

#### FMT

#### fmt_formatDate.jsp

```jsp
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>Formatter - Date and Time Conversion Tags</h1>
    <hr>
    <%-- Create a Date object with the current date and time from the server platform and store it in a Scope attribute --%>
    <c:set var="now" value="<%=new Date() %>"/>
    <%-- Using EL to output the Scope attribute (Date object) will call the Date class's toString() method
    to return the date and time stored in the Date object as a string --%>
    <p>now = ${now }</p>
    
    <%-- formatDate Tag: Converts a Date object to a string with the desired pattern --%>
    <%-- => Provides functionality similar to SimpleDateFormat class's format() method --%>
    <%-- value attribute: Sets the Date object as the attribute value - EL can be used to provide the Date object as an attribute value --%>
    <%-- type attribute: date (default), time, both (date and time) --%>
    <%-- => If type attribute is set to [date], the string is converted to the [yyyy.M.d.] pattern --%>
    <p>now (date) = <fmt:formatDate value="${now }" type="date"/></p>
    <%-- dateStyle attribute: Sets one of the values [full] or [short] --%>
    <%-- => If dateStyle is set to [short], the string is converted to the [yy.M.d.] pattern --%>
    <%-- => If dateStyle is set to [full], the string is converted to the [yyyy year M month d day E weekday] pattern --%>
    <p>now (date) = <fmt:formatDate value="${now }" type="date" dateStyle="short"/></p>
    <p>now (date) = <fmt:formatDate value="${now }" type="date" dateStyle="full"/></p>

    <%-- type attribute: date (default), time, both (date and time) --%>
    <%-- => If type attribute is set to [time], the string is converted to the [a h:mm:ss] pattern --%>
    <p>now (time) = <fmt:formatDate value="${now }" type="time"/></p>

    <%-- timeStyle attribute: Sets one of the values [full] or [short] --%>
    <%-- => If timeStyle is set to [short], the string is converted to the [a h:mm] pattern --%>
    <%-- => If timeStyle is set to [full], the string is converted to the [a h: mm ss z] pattern --%>
    <p>now (time) = <fmt:formatDate value="${now }" type="time" timeStyle="short"/></p>
    <p>now (time) = <fmt:formatDate value="${now }" type="time" timeStyle="full"/></p>
    
    <%-- type attribute: date (default), time, both (date and time) --%>
    <%-- => If type attribute is set to [both], the string is converted to the [yyyy.M.d. a h:mm:ss] pattern --%>
    <p>now (date and time) = <fmt:formatDate value="${now }" type="both"/></p>

    <p>now (date and time) = <fmt:formatDate value="${now }" type="both" dateStyle="short" timeStyle="short"/></p>
    <p>now (date and time) = <fmt:formatDate value="${now }" type="both" dateStyle="full" timeStyle="full"/></p>
    
    <%-- pattern attribute: Sets the pattern information for converting the date and time --%>
    <p>now (pattern) = <fmt:formatDate value="${now }" pattern="yyyy-MM-dd HH:mm:ss"/>
    
    <%-- timeZone Tag: Changes the time zone --%>
    <%-- value attribute: Sets the time zone (continent/city) to change --%>
    <fmt:timeZone value="America/New_York">
        <p>Current date and time in New York: <fmt:formatDate value="${now }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
    </fmt:timeZone>
    
    <fmt:timeZone value="Asia/Hong_Kong">
        <p>Current date and time in Hong Kong: <fmt:formatDate value="${now }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
    </fmt:timeZone>
</body>
</html>
```

#### fmt_formatNumber.jsp
Here's the English translation of the JSP code:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>Formatter - Number Conversion Tags</h1>
    <hr>
    <c:set var="price" value="1000000000"/>
    <p>Price = ${price } won</p>
    
    <%-- formatNumber Tag: Converts numeric values to a string with the desired pattern --%>
    <%-- => Provides functionality similar to the DecimalFormat class's format() method --%>
    <%-- value attribute: Sets the numeric value as the attribute value - EL can be used to provide the numeric value --%>
    <%-- => If the value attribute is not a numeric value, a NumberFormatException will occur --%>
    <%-- type attribute: Sets one of [number] (default) or [currency] --%>
    <%-- => If the type attribute is omitted, the [number] value is used --%>
    <%-- => If type is set to [number], the string is converted with [,] inserted every 3 digits --%>
    <%-- => If type is set to [currency], a currency symbol is added to the front and [,] is inserted every 3 digits --%>
    <p>Price = <fmt:formatNumber value="${price }" type="number"/> won</p>
    <p>Price = <fmt:formatNumber value="${price }" type="currency"/></p>
    
    <%-- setLocale Tag: Changes the locale for formatting, such as date, time, or currency units --%>
    <fmt:setLocale value="en_us"/>
    <p>Price = <fmt:formatNumber value="${price }" type="currency"/></p>
    
    <fmt:setLocale value="ja_jp"/>
    <p>Price = <fmt:formatNumber value="${price }" type="currency"/></p>

    <fmt:setLocale value="ko_kr"/>
    <p>Price = <fmt:formatNumber value="${price }" type="currency"/></p>
    
    <c:set var="pi" value="3.141592"/>
    <p>Pi = ${pi }</p>
    
    <%-- pattern attribute: Sets the pattern for converting numeric values --%>
    <%-- => Allows conversion to the desired number of decimal places and rounding --%>
    <p>Pi = <fmt:formatNumber value="${pi }" pattern="#.##"/></p>
    <p>Pi = <fmt:formatNumber value="${pi }" pattern="#.###"/></p>
    
    <c:set var="avg" value="70.5"/>
    <%-- Using the [#] pattern character will omit the decimal places if there are no values --%>
    <p>Average = <fmt:formatNumber value="${avg }" pattern="##.##"/></p>
    <%-- Using the [0] pattern character will show [0] if there are no values in the decimal places --%>
    <p>Average = <fmt:formatNumber value="${avg }" pattern="##.00"/></p>
    <%-- Using both [#] and [0] pattern characters together for decimal places will result in an error --%>
    <%-- <p>Average = <fmt:formatNumber value="${avg }" pattern="##.#0"/> --%>
</body>
</html>
```
---

#### functions.jsp

Here's the English translation of the JSP code:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Set up the Functions tag library for using EL functions in the JSP document - prefix is [fn] --%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC</title>
</head>
<body>
    <h1>Functions - String-Related EL Functions</h1>
    <hr>
    <c:set var="phone" value="010-1234-5678"/>
    <p>Phone Number = ${phone }</p>
    <hr>
    
    <%-- split function: Splits the provided string by a delimiter and returns an array --%>
    <c:set var="array" value="${fn:split(phone, '-') }"/>
    <c:forEach var="num" items="${array }">
        <div>${num }</div>
    </c:forEach>
    <hr>
    <%-- substring function: Splits the provided string into a substring using the start index (inclusive) 
    and end index (exclusive) --%>
    <div>${fn:substring(phone, 0, 3) }</div>
    <div>${fn:substring(phone, 4, 8) }</div>
    <div>${fn:substring(phone, 9, 13) }</div>
    <hr>
    
    <c:set var="content" value="Hello\nNice to meet you."/>
    <div>${content }</div>
    <%-- replace function: Searches for a substring in the provided string and replaces it with another string --%>
    <div>${fn:replace(content, '\\n', '<br>') }</div>
    <hr>
    
    <c:set var="html" value="<font size='7' color='red'>Hello.</font>"/>
    <%-- When the string provided through EL expressions contains HTML tags, the HTML tags are rendered 
    as tags, which can be vulnerable to XSS attacks --%>
    <div>${html }</div>
    
    <%-- Using the out tag will output the string with HTML tags as they are - Defense against XSS attacks --%>
    <div><c:out value="${html }"/></div>
    
    <%-- escapeXml function: Converts HTML-related characters in the provided string to escape characters 
    to defend against XSS attacks --%>
    <div>${fn:escapeXml(html) }</div>
</body>
</html>
```