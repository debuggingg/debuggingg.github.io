---
layout: single
title: 2024/08/14 MyBatis9-Dynamic
---
#### Dynamic SQL


```xml
<!-- Dynamic SQL: Allows SQL commands to be registered differently using provided values -->
<!-- => if element, choose element (when element, otherwise element), foreach element
     trim element (where element, set element), bind element -->
<!-- => Implemented using OGNL (Object Graph Navigation Language) expressions with provided values -->

<select id="selectSearchHewonList" parameterType="map" resultMap="myHewonResultMap">
    <!-- bind: Provides values required for SQL commands -->
    <!-- => Use the parameterType attribute to modify received values for use in SQL commands -->
    <!-- name attribute: Identifier to distinguish values provided by the bind element -->
    <!-- => Use values provided by the bind element in SQL commands in the #{name} format -->
    <!-- value attribute: Sets the value provided by the bind element -->
    <!-- => Set the value of the parameterType attribute with an OGNL expression -->
    <!-- => Use the + operator to concatenate provided values with string values -->
    <bind name="keyword" value="'%'+searchKeyword+'%'"/>
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope 
        from myhewon where ${searchColumn} like #{keyword} order by hewon_id
</select>

<!-- If no name is provided, search all rows in the MYHEWON table; if a name is provided,
     search rows in the MYHEWON table with the provided name and return them as MyHewon objects -->
<select id="selectDynamicNameHewonList" parameterType="string" resultMap="myHewonResultMap">
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon
    <!-- if: Includes the content (SQL statement) of the element in the SQL command only if the condition is true -->
    <!-- test attribute: Set to either false (content not included) or true (content included) -->
    <!-- => Use OGNL expressions to write conditional expressions based on the parameterType value -->
    <if test="name != null and name != ''"><!-- If name is provided -->
        where hewon_name = #{name}
    </if> 
    order by hewon_id
</select>

<!-- If no ID and name are provided, search all rows in the MYHEWON table; if an ID or name is provided,
     search rows in the MYHEWON table where the ID or name matches the provided values and return them as MyHewon objects -->
<!-- => If both ID and name are provided, the SQL command will be incorrect and cause an error -->
<!--  
<select id="selectDynamicIdNameHewonList" parameterType="map" resultMap="myHewonResultMap">
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon
    <if test="id != null and id != ''">
        where hewon_id = #{id}
    </if>
    <if test="name != null and name != ''">
        where hewon_name = #{name}
    </if>
    order by hewon_id
</select>
-->

<!-- Implement dynamic SQL to avoid errors even if both ID and name are provided -->
<!-- => If both ID and name are provided, search only by ID to avoid errors -->
<!-- choose: Includes the content (SQL statement) of one true condition in the SQL command -->
<!-- => Child elements: when elements (one or more), otherwise (0 or 1) -->
<!-- when: Includes the content (SQL statement) of the element in the SQL command only if the condition is true -->
<!-- => If the condition of a when element is true, include the content in the SQL command and end the choose element -->
<!-- test attribute: Set to either false (content not included) or true (content included) -->
<!-- otherwise: Includes the content (SQL statement) of the element in the SQL command if all when elements' conditions are false -->
<!--  
<select id="selectDynamicIdNameHewonList" parameterType="map" resultMap="myHewonResultMap">
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon
    <choose>
        <when test="id != null and id != ''">
            where hewon_id = #{id}
        </when>
        <when test="name != null and name != ''">
            where hewon_name = #{name}
        </when>
    </choose>
    order by hewon_id
</select>
-->

<!-- Implement dynamic SQL to avoid errors even if both ID and name are provided -->
<!-- => Use the trim element to resolve issues when only name is provided and prevent SQL command errors -->
<!-- trim: Adds or removes SQL statements to include the generated SQL statement in the SQL command -->
<!-- => Uses multiple if elements in child elements to modify the provided SQL statement for inclusion in the SQL command -->
<!-- prefix attribute: Sets the SQL statement to be added at the beginning of the trim element's content -->
<!-- suffix attribute: Sets the SQL statement to be added at the end of the trim element's content -->
<!-- prefixOverrides attribute: Sets the SQL statements to be removed from the beginning of the trim element's content -->
<!-- suffixOverrides attribute: Sets the SQL statements to be removed from the end of the trim element's content -->
<!--  
<select id="selectDynamicIdNameHewonList" parameterType="map" resultMap="myHewonResultMap">
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon
    <trim prefix="where" prefixOverrides="or|and">
        <if test="id != null and id != ''">
            hewon_id = #{id}
        </if>
        <if test="name != null and name != ''">
            ${choice} hewon_name = #{name}
        </if>
    </trim>
    order by hewon_id
</select>
-->

<!-- where: Removes unnecessary SQL statements (like or and) from the beginning of the element content and adds [where] to the SQL command -->
<select id="selectDynamicIdNameHewonList" parameterType="map" resultMap="myHewonResultMap">
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon
    <where>
        <if test="id != null and id != ''">
            hewon_id = #{id}
        </if>
        <if test="name != null and name != ''">
            ${choice} hewon_name = #{name}
        </if>
    </where>
    order by hewon_id
</select>

<!-- Updates a row in the MYHEWON table with the provided member information -->
<!-- => Compares the ID and updates all column values except for the ID -->
<update id="updateHewon" parameterType="MyHewon">
    update myhewon set hewon_name=#{name}, hewon_phone=#{phone}, hewon_email=#{email}
        , hewon_scope=#{scope} where hewon_id=#{id}
</update>

<!-- Updates a row in the MYHEWON table with the provided member information -->
<!-- => Uses dynamic SQL to compare the ID and update only columns with provided values, excluding the ID -->
<!-- => Avoids errors by using text-based comparison operators (gt, lt, gte, lte) instead of symbolic comparison operators (>, <, >=, <=) -->
<!--  
<update id="updateDynamicHewon" parameterType="MyHewon">
    update myhewon
    <trim prefix="set" suffixOverrides=",">
        <if test="name != null and name != ''">
            hewon_name=#{name},
        </if>
        <if test="phone != null and phone != ''">
            hewon_phone=#{phone},
        </if>
        <if test="email != null and email != ''">
            hewon_email=#{email},
        </if>
        <if test="scope gte 1 and scope lte 4">
            hewon_scope=#{scope}
        </if>
    </trim>
    where hewon_id=#{id}
</update>
-->

<!-- set: Removes unnecessary SQL statements (,) from the end of the element content and adds [set] to the SQL command -->
<update id="updateDynamicHewon" parameterType="MyHewon">
    update myhewon
    <set>
        <if test="name != null and name != ''">
            hewon_name=#{name},
        </if>
        <if test="phone != null and phone != ''">
            hewon_phone=#{phone},
        </if>
        <if test="email != null and email != ''">
            hewon_email=#{email},
        </if>
        <if test="scope gte 1 and scope lte 4">
            hewon_scope=#{scope}
        </if>
    </set>
    where hewon_id=#{id}
</update>

<!-- Receives one or more IDs and searches for rows in the MYHEWON table with the provided IDs -->
<!-- => If no IDs are provided, search all rows in the MYHEWON table and return them as MyHewon objects -->
<!-- => Sets the parameterType attribute to the ArrayList class (List interface) and uses List objects to provide values for the SQL command -->
<select id="selectMultiIdDynamicHewonList" parameterType="list" resultMap="myHewonResultMap">
    select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon
    <if test="list != null">
        where hewon_id in
        <!-- foreach: Provides elements from the List object and includes them in the SQL command -->
        <!-- collection attribute: Sets the name of the List object to process -->
        <!-- item attribute: Sets the name (variable name) for elements from the List object -->
        <!-- => Use #{name} format to write SQL statements using List elements -->
        <!-- open attribute: Sets the statement to be added at the beginning of the SQL statement -->
        <!-- close attribute: Sets the statement to be added at the end of the SQL statement -->
        <!-- separator attribute: Sets the statement to separate elements -->
        <foreach collection="list" item="id" open="(" close=")" separator=",">
            #{id}
        </foreach>
    </if>
    order by hewon_id
</select>
```

### Explanation:
- **Dynamic SQL Elements:**
  - **`<bind>`**: Sets and binds variables used in SQL statements.
  - **`<if>`**: Conditionally includes SQL fragments based on tests.
  - **`<choose>`, `<when>`, `<otherwise>`**: Provides conditional logic to choose one of several SQL fragments.
  - **`<trim>`**: Removes unnecessary SQL elements (like extra commas) and adds necessary SQL keywords (like `WHERE`).
  - **`<set>`**: Similar to `<trim>`, but specifically for setting fields in `UPDATE` statements.
  - **`<foreach>`**: Iterates over a collection to include multiple values in a SQL `IN` clause.
  - **`<where>`**: Automatically adds a `WHERE` clause and removes any leading `AND` or `OR`.

#### Java (interface)
```java 
List<MyHewon> selectDynamicNameHewonList(String name);

List<MyHewon> selectDynamicIdNameHewonList(Map<String, Object> map);

int updateHewon(MyHewon hewon);

int updateDynamicHewon(MyHewon hewon);
```

### jsp

#### Select
```jsp
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="xyz.itwill.dao.MyHewonDAO"%>
<%@page import="xyz.itwill.dto.MyHewon"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");

    String id = request.getParameter("id");
    String choice = request.getParameter("choice");
    String name = request.getParameter("name");

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("choice", choice);
    map.put("name", name);
    
    List<MyHewon> hewonList = MyHewonDAO.getDAO().selectDynamicIdNameHewonList(map);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYBATIS</title>
<style type="text/css">
table {
    border: 1px solid black;
    border-collapse: collapse;
}

td {
    border: 1px solid black;
    text-align: center;
    padding: 3px;    
}

.id { width: 150px; }
.name { width: 150px; }
.phone { width: 200px; }
.email { width: 200px; }
.scope { width: 100px; }
</style>
</head>
<body>
    <h1>Member List</h1>
    <hr>
    <table>
        <tr>
            <td class="id">ID</td>
            <td class="name">Name</td>
            <td class="phone">Phone Number</td>
            <td class="email">Email</td>
            <td class="scope">Scope</td>
        </tr>
        <% if (hewonList.isEmpty()) { %>
        <tr>
            <td colspan="5">No member information found.</td>
        </tr>
        <% } else { %>
            <% for (MyHewon hewon : hewonList) { %>
            <tr>
                <td class="id"><%= hewon.getId() %></td>
                <td class="name"><%= hewon.getName() %></td>
                <td class="phone"><%= hewon.getPhone() %></td>
                <td class="email"><%= hewon.getEmail() %></td>
                <td class="scope"><%= hewon.getScope() %></td>
            </tr>    
            <% } %>
        <% } %>
    </table>
    <br>
    
    <form method="post">
        ID: <input type="text" name="id">
        <select name="choice">
            <option value="or" selected="selected">&nbsp;OR&nbsp;</option>
            <option value="and">&nbsp;AND&nbsp;</option>
        </select>
        Name: <input type="text" name="name">
        <button type="submit">Search</button>
    </form>
</body>
</html>
```

### Explanation:
- **Imports:** Includes classes for handling data and objects.
- **Request Handling:** Sets character encoding and retrieves parameters from the request.
- **Data Processing:** Uses the `MyHewonDAO` to fetch data based on provided parameters and stores it in `hewonList`.
- **HTML Structure:**
  - **Table:** Displays member data or a message if no data is found.
  - **Form:** Allows users to input an ID and name for searching, with a choice between `OR` and `AND` for the search condition.
#### update


```jsp
<%@page import="xyz.itwill.dto.MyHewon"%>
<%@page import="xyz.itwill.dao.MyHewonDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Updates the name of the member with ID [xxx] to [Hong Kyung-rae] in the MYHEWON table --%>
<%
    MyHewon hewon = new MyHewon();
    hewon.setId("xxx");
    hewon.setName("Hong Kyung-rae");

    MyHewonDAO.getDAO().updateDynamicHewon(hewon);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYBATIS</title>
</head>
<body>
    <h1>Member Update</h1>
    <hr>
    <h3>The member information has been successfully updated.</h3>
</body>
</html>
```

### Explanation:
- **Imports:** Includes classes for handling the data and database operations.
- **JSP Scriptlet:**
  - **Updates:** Creates a `MyHewon` object and sets its ID to `"xxx"` and name to `"Hong Kyung-rae"`.
  - **DAO Call:** Calls the `updateDynamicHewon` method to update the member's information in the database.
- **HTML Structure:**
  - **Message:** Displays a confirmation message indicating that the member information has been successfully updated.