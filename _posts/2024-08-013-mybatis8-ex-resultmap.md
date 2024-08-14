---
layout: single
title: 2024/08/12 MyBatis8-resultMap
---

#### MyHewon.java DTO


```java
package xyz.itwill.dto;

/*
MYHEWON Table: Table to store member information  
create table myhewon(
    hewon_id varchar2(50) primary key, 
    hewon_name varchar2(50),
    hewon_phone varchar2(20), 
    hewon_email varchar2(100), 
    hewon_scope number(1)
);

Column       Nullable?  Type            
----------- -------- ------------- 
HEWON_ID    NOT NULL VARCHAR2(50)  - ID
HEWON_NAME           VARCHAR2(50)  - Name
HEWON_PHONE          VARCHAR2(20)  - Phone Number
HEWON_EMAIL          VARCHAR2(100) - Email
HEWON_SCOPE          NUMBER(1)     - Scope

Scope: 1 (ID), 2 (ID & Name), 3 (ID & Name & Phone Number), 4 (ID & Name & Phone Number & Email)
*/

public class MyHewon {
    private String id;
    private String name;
    private String phone;
    private String email;
    private int scope;

}
```



#### MyHewonMapper.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="xyz.itwill.mapper.MyHewonMapper">
    <insert id="insertHewon" parameterType="MyHewon">
        insert into myhewon values(#{id}, #{name}, #{phone}, #{email}, #{scope})
    </insert>

    <resultMap type="MyHewon" id="myHewonResultMap">
        <id column="hewon_id" property="id"/>
        <result column="hewon_name" property="name"/>
        <result column="hewon_phone" property="phone"/>
        <result column="hewon_email" property="email"/>
        <result column="hewon_scope" property="scope"/>
    </resultMap>

    <select id="selectHewonList" resultMap="myHewonResultMap">
        select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon order by hewon_id
    </select>

    <!-- discriminator: An element used to provide different mapping information based on the value of a column in the result row -->
    <!-- Use <case> elements to specify different mappings based on the value of the column -->
    <!-- javaType attribute: Specifies the Java type for comparing column values -->
    <!-- column attribute: Specifies the name of the column to compare -->
    <!-- case: Provides mapping information for specific column values -->
    <!-- value attribute: Specifies the value to compare -->
    <!-- 
    <resultMap type="MyHewon" id="myHewonDiscriminatorResultMap">
        <discriminator javaType="int" column="hewon_scope">
            <case value="1">
                <id column="hewon_id" property="id"/>
                <result column="hewon_scope" property="scope"/>
            </case>
            <case value="2">
                <id column="hewon_id" property="id"/>
                <result column="hewon_name" property="name"/>
                <result column="hewon_scope" property="scope"/>
            </case>
            <case value="3">
                <id column="hewon_id" property="id"/>
                <result column="hewon_name" property="name"/>
                <result column="hewon_phone" property="phone"/>                
                <result column="hewon_scope" property="scope"/>
            </case>
            <case value="4">
                <id column="hewon_id" property="id"/>
                <result column="hewon_name" property="name"/>
                <result column="hewon_phone" property="phone"/>
                <result column="hewon_email" property="email"/>                
                <result column="hewon_scope" property="scope"/>
            </case>
        </discriminator>
    </resultMap>
    -->

    <!-- If all case elements have common mapping information, it is recommended to use other elements instead of discriminator -->
    <!-- 
    <resultMap type="MyHewon" id="myHewonDiscriminatorResultMap">
        <id column="hewon_id" property="id"/>
        <result column="hewon_scope" property="scope"/>
        <discriminator javaType="int" column="hewon_scope">
            <case value="2">
                <result column="hewon_name" property="name"/>
            </case>
            <case value="3">
                <result column="hewon_name" property="name"/>
                <result column="hewon_phone" property="phone"/>                
            </case>
            <case value="4">
                <result column="hewon_name" property="name"/>
                <result column="hewon_phone" property="phone"/>
                <result column="hewon_email" property="email"/>                
            </case>
        </discriminator>
    </resultMap>
    -->

    <resultMap type="MyHewon" id="myHewonOneResultMap">
        <id column="hewon_id" property="id"/>
        <result column="hewon_scope" property="scope"/>
    </resultMap> 

    <!-- extends attribute: Specifies the identifier (id attribute value) of the resultMap element to inherit mapping information from -->
    <!-- Inherits and uses mapping information from the resultMap element -->
    <resultMap type="MyHewon" id="myHewonTwoResultMap" extends="myHewonOneResultMap">
        <result column="hewon_name" property="name"/>
    </resultMap>

    <resultMap type="MyHewon" id="myHewonThreeResultMap" extends="myHewonTwoResultMap">
        <result column="hewon_phone" property="phone"/>
    </resultMap>

    <resultMap type="MyHewon" id="myHewonFourResultMap" extends="myHewonThreeResultMap">
        <result column="hewon_email" property="email"/>                
    </resultMap>

    <resultMap type="MyHewon" id="myHewonDiscriminatorResultMap">
        <discriminator javaType="int" column="hewon_scope">
            <!-- Use the resultMap attribute of <case> elements to provide mapping information from other resultMap elements -->
            <case value="1" resultMap="myHewonOneResultMap"/>
            <case value="2" resultMap="myHewonTwoResultMap"/>
            <case value="3" resultMap="myHewonThreeResultMap"/>
            <case value="4" resultMap="myHewonFourResultMap"/>
        </discriminator>
    </resultMap>

    <select id="selectDiscriminatorHewonList" resultMap="myHewonDiscriminatorResultMap">
        select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope from myhewon order by hewon_id
    </select>

    <!-- How to pass values to SQL commands registered in the mapper -->
    <!-- => Values are passed in SQL commands using the #{variableName|fieldName|mapKey} format -->
    <!-- 1. In XML-based mapper files, use the parameterType attribute to specify a Java type (alias) to pass values (objects) -->
    <!-- => Use primitive types (wrapper classes) or String classes for single values - use #{variableName} format in SQL commands -->
    <!-- => For multiple values, specify a DTO class in parameterType - use #{fieldName} format in SQL commands -->
    <!-- => For multiple values, use the Map interface in parameterType - use #{mapKey} format in SQL commands -->
    <!-- 2. In interface-based mapper files, use @Param annotations on method parameters to pass values to SQL commands -->
    <!-- => When binding to an interface-based mapper file, omit the parameterType attribute if the abstract method parameters are used in XML-based mapper elements -->
    
    <!-- Element to fetch rows from MYHEWON table with a specific scope and provide them as Java objects -->
    <!-- => For a single value (scope), omit parameterType and use a primitive type (wrapper class) or String -->
    <!-- 
    <select id="selectScopeHewonList" parameterType="int" resultMap="myHewonResultMap">
        select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope 
            from myhewon where hewon_scope=#{scope} order by hewon_id
    </select>
    -->

    <!-- If a single value is used, omit parameterType in XML-based mapper elements -->
    <select id="selectScopeHewonList" resultMap="myHewonResultMap">
        select hewon_id, hewon_name, hewon_phone, hewon_email, hewon_scope 
            from myhewon where hewon_scope=#{scope} order by hewon_id
    </select>

    <!-- Element to fetch the ID from MYHEWON table where name and email match provided values -->
    <!-- => For multiple values, use a DTO class in parameterType and pass a DTO object -->
    <!-- 
    <select id="selectDtoHewonId" parameterType="MyHewon" resultType="string">
        select hewon_id from myhewon where hewon_name=#{name} and hewon_email=#{email}
    </select>
    -->

    <!-- When using mapper binding with interfaces, omit parameterType regardless of passed values -->
    <select id="selectDtoHewonId" resultType="string">
        select hewon_id from myhewon where hewon_name=#{name} and hewon_email=#{email}
    </select>

    <!-- Element to fetch the ID from MYHEWON table where name and email match provided values -->
    <!-- => For multiple values, use the Map interface in parameterType and pass a Map object -->
    <!-- 
    <select id="selectMapHewonId" parameterType="map" resultType="string">
        select hewon_id from myhewon where hewon_name=#{name} and hewon_email=#{email}
    </select>
    -->

    <!-- Element to fetch the ID from MYHEWON table where name and email match provided values -->
    <!-- => When using mapper binding with interfaces, use @Param annotations in method parameters -->
    <!-- => Always omit parameterType -->
    <!-- 
    <select id="selectParamHewonId" resultType="string">
        select hewon_id from myhewon where hewon_name=#{name} and hewon_email
```

#### MyHewonMapper.java (interface)

```java
package xyz.itwill.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xyz.itwill.dto.MyHewon;

public interface MyHewonMapper {
    int insertHewon(MyHewon hewon);
    List<MyHewon> selectHewonList();
    List<MyHewon> selectDiscriminatorHewonList();
    List<MyHewon> selectScopeHewonList(int scope);
    String selectDtoHewonId(MyHewon hewon);
    
    // When using HashMap or the Map interface as a parameter, set the generic configuration 
    // with map key = String and map value = Object to accommodate any data.
    String selectMapHewonId(Map<String, Object> map);
    
    // When using the @Param annotation for method parameters, specify the value for 
    // each parameter to match the SQL query placeholders.
    String selectParamHewonId(@Param(value = "name") String name, @Param("email") String email);
}
```



hewonListSelect.jsp
```jsp
List<MyHewon> hewonList=MyHewonDAO.getDAO().selectHewonList();
```

hewonListScopeSelect.jsp
```jsp
int scope=0;
if(request.getParameter("scope") != null) {
scope=Integer.parseInt(request.getParameter("scope"));
}
List<MyHewon> hewonList=null;
if(scope == 0) {
hewonList=MyHewonDAO.getDAO().selectHewonList();//전체 검색
} else {
hewonList=MyHewonDAO.getDAO().selectScopeHewonList(scope);//조건 검색
}
```

hewonListDiscriminatorSelect.jsp
```jsp
List<MyHewon> hewonList=MyHewonDAO.getDAO().selectDiscriminatorHewonList();
```
hewonIdDtoSelect.jsp
```jsp
request.setCharacterEncoding("utf-8");
String name=request.getParameter("name");
String email=request.getParameter("email");
String id="";
if(name != null && email != null) {
MyHewon hewon=new MyHewon();
hewon.setName(name);
hewon.setEmail(email);
id=MyHewonDAO.getDAO().selectDtoHewonId(hewon);
}
```
hewonIdMapSelect.jsp
```jsp
request.setCharacterEncoding("utf-8");
String name=request.getParameter("name");
String email=request.getParameter("email");
String id="";
if(name != null && email != null) {
Map<String,Object> map=new HashMap<>();
map.put("name",name);
map.put("email",email);
id=MyHewonDAO.getDAO().selectMapHewonId(map);
}
```
hewonIdParamSelect.jsp
```jsp
request.setCharacterEncoding("utf-8");
String name=request.getParameter("name");
String email=request.getParameter("email");
String id="";
if(name != null && email != null) {
MyHewon hewon=new MyHewon();
hewon.setName(name);
hewon.setEmail(email);
id=MyHewonDAO.getDAO().selectParamHewonId(name,email);
}
```