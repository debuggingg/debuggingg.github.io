---
layout: single
title: 2024/07/05 Servlet- 05-ConnectOracleSQL
---
# Connect Oracle

## ## Ex) Guest  
#### DAO, DTO, TABLE

## TABLE
// Creating the GUEST table to store posts
// create table guest(num number primary key, writer varchar2(50), subject varchar2(200)
//   , content varchar2(1000), regdate date);

// Creating the GUEST_SEQ sequence to provide post numbers
// create sequence guest_seq;

/*
Name      Null?    Type              
------- -------- -------------- 
NUM     NOT NULL NUMBER          - Post number: Stores a numeric value provided by the sequence        
WRITER           VARCHAR2(50)    - Post writer: Stores the value input by the user
SUBJECT          VARCHAR2(200)   - Post subject: Stores the value input by the user
CONTENT          VARCHAR2(1000)  - Post content: Stores the value input by the user
REGDATE          DATE            - Post date: Stores the current date and time provided by the DBMS server
*/
## DTO 

```java
package xyz.itwill.dto;



// Class to store and transfer rows (posts) from the GUEST table
public class GuestDTO {
    private int num;
    private String writer;
    private String subject;
    private String content;
    private String regdate;
    
    public GuestDTO() {
        // TODO Auto-generated constructor stub
    }

    public GuestDTO(int num, String writer, String subject, String content, String regdate) {
        super();
        this.num = num;
        this.writer = writer;
        this.subject = subject;
        this.content = content;
        this.regdate = regdate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
```

## DAO
```java
// Class that provides functionality to insert, update, delete, and search rows (posts) in the GUEST table
public class GuestDAO extends JdbcDAO {
    private static GuestDAO _dao;

    private GuestDAO() {
        // TODO Auto-generated constructor stub
    }

    static {
        _dao = new GuestDAO();
    }

    public static GuestDAO getDAO() {
        return _dao;
    }

    // Method that receives a post (GuestDTO object), inserts it as a row in the GUEST table, and returns the number of inserted rows (int)
    public int insertGuest(GuestDTO guest) {
        Connection con = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            con = getConnection();
            
            String sql = "insert into guest values(guest_seq.nextval,?,?,?,sysdate)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, guest.getWriter());
            pstmt.setString(2, guest.getSubject());
            pstmt.setString(3, guest.getContent());
            
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Error] insertGuest() method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt);
        }
        return rows;
    }
    
    // Method that receives a post (GuestDTO object), updates the corresponding row in the GUEST table, and returns the number of updated rows (int)
    public int updateGuest(GuestDTO guest) {
        Connection con = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            con = getConnection();
            
            String sql = "update guest set writer=?, subject=?, content=? where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, guest.getWriter());
            pstmt.setString(2, guest.getSubject());
            pstmt.setString(3, guest.getContent());
            pstmt.setInt(4, guest.getNum());
            
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Error] updateGuest() method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt);
        }
        return rows;
    }

    // Method that receives the post number (int), deletes the corresponding row from the GUEST table, and returns the number of deleted rows (int)
    public int deleteGuest(int num) {
        Connection con = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            con = getConnection();
            
            String sql = "delete from guest where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, num);
            
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Error] deleteGuest() method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt);
        }
        return rows;
    }
    
    // Method that receives the post number (int), searches for the corresponding row in the GUEST table, and returns the searched post (GuestDTO object)
    public GuestDTO selectGuest(int num) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        GuestDTO guest = null;
        try {
            con = getConnection();
            
            String sql = "select num, writer, subject, content, regdate from guest where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, num);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                guest = new GuestDTO();
                guest.setNum(rs.getInt("num"));
                guest.setWriter(rs.getString("writer"));
                guest.setSubject(rs.getString("subject"));
                guest.setContent(rs.getString("content"));
                guest.setRegdate(rs.getString("regdate"));
            }
        } catch (SQLException e) {
            System.out.println("[Error] selectGuest() method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
        return guest;
    }

    // Method that searches for all rows in the GUEST table and returns the searched posts (List object)
    public List<GuestDTO> selectGuestList() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<GuestDTO> guestList = new ArrayList<GuestDTO>();
        try {
            con = getConnection();
            
            String sql = "select num, writer, subject, content, regdate from guest order by num desc";
            pstmt = con.prepareStatement(sql);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                GuestDTO guest = new GuestDTO();
                guest.setNum(rs.getInt("num"));
                guest.setWriter(rs.getString("writer"));
                guest.setSubject(rs.getString("subject"));
                guest.setContent(rs.getString("content"));
                guest.setRegdate(rs.getString("regdate"));
                
                guestList.add(guest);
            }
        } catch (SQLException e) {
            System.out.println("[Error] selectGuestList() method SQL error = " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
        return guestList;
    }
}
```
