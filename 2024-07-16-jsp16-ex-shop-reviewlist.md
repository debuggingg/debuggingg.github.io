---
layout: single
title: 2024/07/16 JSP 16-EX-Shop-Review-List
---
## Review

#### Create Table -SQL 
```SQL
create table review(review_num number primary key, review_member_num number constraint 
    review_member_num_fk references member(member_num), review_subject varchar2(500)
    , review_content varchar2(4000), review_image varchar2(100), review_register_date date
    , review_update_date date,review_ip varchar2(20), review_count number, review_ref number, review_restep number
    , review_relevel number, review_status number(1));
   desc review;
create sequence review_seq; 
```

```plaintext
Name                        Null?    Type
------------------------    -------  --------------
REVIEW_NUM                  NOT NULL NUMBER         - Post Number
REVIEW_MEMBER_NUM                    NUMBER         - Author: Member Number of the logged-in user
REVIEW_SUBJECT                       VARCHAR2(500)  - Subject
REVIEW_CONTENT                       VARCHAR2(4000) - Content
REVIEW_IMAGE                         VARCHAR2(100)  - Name of the image file
REVIEW_REGISTER_DATE                 DATE           - Date of creation
REVIEW_UPDATE_DATE                   DATE           - Date of modification
REVIEW_IP                            VARCHAR2(20)   - Client IP Address
REVIEW_COUNT                         NUMBER         - View count
REVIEW_REF                           NUMBER         - Reply: Post group
REVIEW_RESTEP                        NUMBER         - Reply: Post sequence
REVIEW_RELEVEL                       NUMBER         - Reply: Level of posts below
REVIEW_STATUS                        NUMBER(1)      - Post status: 0 (deleted), 1 (normal), 2 (private)
```

### Create DTO (ReviewDTO)- java
```JAVA
private int reviewNum;
private int reviewMemberNum;
private String reviewSubject;
private String reviewContent;
private String reviewImage;
private String reviewRegisterDate;
private String reviewUpdateDate;
private String reviewIp;
private int reviewCount;
private int reviewRef;
private int reviewRestep;
private int reviewRelevel;
private int reviewStatus;
```
### Create DAO(ReviewDAO) - java
- Method that retrieves the number of rows in the REVIEW table containing the search information (search and keyword).
```java
// Method that retrieves the number of rows in the REVIEW table containing the search information (search and keyword).
// If search functionality is not used, it returns the total number of rows in the REVIEW table.
public int selectTotalReview(String search, String keyword) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int count = 0;
    try {
        con = getConnection();
        
        // Compare the values stored in the parameters and send different SQL commands to the DBMS server for execution.
        // => Dynamic SQL
        if (keyword.equals("")) { // If search functionality is not used , 검색없이 전체 글 표현
            String sql = "select count(*) from review";
            pstmt = con.prepareStatement(sql);
        } else {// 테이블에는 작성자 이름이 없기때문에 검색이 안되어서, 조인으로 회원 이름 도 넣어서 찾는 조건을 만들었다. 검색한 글들만 보여질떄.
            String sql = "select count(*) from review join member on review_member_num=member_num"
                       + " where "+search+" like '%'||?||'%' and review_status=1";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
        }
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in selectReviewCount() method = "+e.getMessage());
    } finally {
        close(con, pstmt, rs);
    }
    return count;
}
```
- Method that retrieves a list of REVIEW table rows containing the search information (startRow, endRow, search, keyword)
```java
// Method that retrieves a list of REVIEW table rows containing the search information (startRow, endRow, search, keyword)
// using paging and returns the list of retrieved reviews (List object).
public List<ReviewDTO> selectReviewList(int startRow, int endRow, String search, String keyword) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
    try {
        con = getConnection();
        
        if (keyword.equals("")) {// 모든 글들을 선택한 글들의 순서 부터 (10,20,30..)선택된 갯수만큼만 화면에 나오게
            String sql = "select * from (select rownum rn, temp.* from (select review_num"
                       + ",review_member_num,member_name,review_subject,review_content,review_image"
                       + ",review_register_date,review_update_date,review_ip,review_count,review_ref"
                       + ",review_restep,review_relevel,review_status from review join member on"
                       + " review_member_num=member_num order by review_ref desc,review_restep) temp)"
                       +" where rn between ? and ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
        } else {// 검색된 글들을 선택한 글들의 순서부터 (10,20,30..)선택된 갯수만큼만 화면에 나오게
            String sql = "select * from (select rownum rn, temp.* from (select review_num"
                       + ",review_member_num,member_name,review_subject,review_content,review_image"
                       + ",review_register_date,review_update_date,review_ip,review_count,review_ref"
                       + ",review_restep,review_relevel,review_status from review join member on"
                       + " review_member_num=member_num where "+search+" like '%'||?||'%' and review_status=1"
                       + " order by review_ref desc,review_restep) temp) where rn between ? and ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            pstmt.setInt(2, startRow);
            pstmt.setInt(3, endRow);
        }
        
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            ReviewDTO review = new ReviewDTO();
            review.setReviewNum(rs.getInt("review_num"));
            review.setReviewMemberNum(rs.getInt("review_member_num"));
            review.setMemberName(rs.getString("member_name"));
            review.setReviewSubject(rs.getString("review_subject"));
            review.setReviewContent(rs.getString("review_content"));
            review.setReviewImage(rs.getString("review_image"));
            review.setReviewRegisterDate(rs.getString("review_register_date"));
            review.setReviewUpdateDate(rs.getString("review_update_date"));
            review.setReviewIp(rs.getString("review_ip"));
            review.setReviewCount(rs.getInt("review_count"));
            review.setReviewRef(rs.getInt("review_ref"));
            review.setReviewRestep(rs.getInt("review_restep"));
            review.setReviewRelevel(rs.getInt("review_relevel"));
            review.setReviewStatus(rs.getInt("review_status"));
            
            reviewList.add(review);
        }
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in selectReviewList() method = "+e.getMessage());
    } finally {
        close(con, pstmt, rs);
    }
    return reviewList;
}
```

Method that retrieves the next value of the REVIEW_SEQ sequence and returns it.
```java
// Method that retrieves the next value of the REVIEW_SEQ sequence and returns it.
public int selectReviewNextNum() {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int nextNum = 0;
    try {
        con = getConnection();
        
        String sql = "select review_seq.nextval from dual";
        pstmt = con.prepareStatement(sql);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            nextNum = rs.getInt(1);
        }
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in selectReviewNextNum() method = "+e.getMessage());
    } finally {
        close(con, pstmt, rs);
    }
    return nextNum;
}
```
- Method that inserts a review (ReviewDTO object) into the REVIEW table and returns the number of rows inserted.
```java
// Method that inserts a review (ReviewDTO object) into the REVIEW table and returns the number of rows inserted.
public int insertReview(ReviewDTO review) {
    Connection con = null;
    PreparedStatement pstmt = null;
    int rows = 0;
    try {
        con = getConnection();
        
        String sql = "insert into review values(?,?,?,?,?,sysdate,null,?,0,?,?,?,?)";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, review.getReviewNum());
        pstmt.setInt(2, review.getReviewMemberNum());
        pstmt.setString(3, review.getReviewSubject());
        pstmt.setString(4, review.getReviewContent());
        pstmt.setString(5, review.getReviewImage());
        pstmt.setString(6, review.getReviewIp());
        pstmt.setInt(7, review.getReviewRef());
        pstmt.setInt(8, review.getReviewRestep());
        pstmt.setInt(9, review.getReviewRelevel());
        pstmt.setInt(10, review.getReviewStatus());
        
        rows = pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("[Error] SQL error in insertReview() method = "+e.getMessage());
    } finally {
        close(con, pstmt);
    }
    return rows;
}
```

SQL 4일차 보면 ROWNUM 을 공부 다시 하자 
### 
#### Review_list.jsp only for display  - no CSS

#### review_list.jsp (main pages)
- Self Recall Method - page number, number of posts, search target, search word
	- Page Number 
	- Number of Post
	- Search
- Recall Another JSP Document 
	- Write ->[/review/review_write.jsp]
	- Title -> [/review/review_detail.jsp] ->->review number, page number, number of posts, search target, search word   

#### review_detail.jsp 
- Recall Another JSP Document 
	- write -> [/review/review_write.jsp] ->->  page number, number of posts, search target, search word  (for back to review_list.jsp)
	- Main ->  [/review_list.jsp] ->  page number, number of posts, search target, search word

#### review_write.jsp 
- Recall Another JSP Document 
	- write_action ->[/review_write_action.jsp] ->-> 새글일때 (제목 내용 파일) . 답글일때 부모글(글그룹, 글순서,글깊이) page number, number of posts, search target, search word
#### review_write_action.jsp
- Recall Another JSP Document 
	- Back to Main -> [/review_list.jsp] ->   page number, number of posts, search target, search word
```jsp
<%-- JSP document that retrieves rows from the REVIEW table and responds by embedding them in HTML tags --%>
<%-- => Retrieves rows from the REVIEW table, dividing them into pages and handling response - pagination --%>

<!-- Self-recall method -->
<%-- => When clicking on the [Page Number] tag, request [/review/review_list.jsp] to move to another page
- Pass page number, number of posts, search target, search word for maintaining search functionality --%>
<%-- => When changing the input value of [Number of Posts] tag, request [/review/review_list.jsp] to move to another page
- Pass page number, number of posts, search target, search word --%>
<%-- => When clicking on the [Search] tag, request [/review/review_list.jsp] to move to another page
- Pass search target, search word: page number and number of posts use default values --%>

<!-- Recall another JSP document -->
<%-- => When clicking on the [Write] tag, request [/review/review_write.jsp] to move to another page
- Display the link only to logged-in users --%>
<%-- When clicking on the [Title] of a post, request [/review/review_detail.jsp] to move to another page
- Pass review number, page number, number of posts, search target, search word --%>

<%
    // Retrieve necessary parameters for post search (search target and search word)
    String search = request.getParameter("search");
    if (search == null) {
        search = "";
    }
    
    String keyword = request.getParameter("keyword");
    if (keyword == null) {
        keyword = "";
    }
    
    // Retrieve parameters for pagination (page number and number of posts per page)
    int pageNum = 1; // Default page number if not provided
    if (request.getParameter("pageNum") != null) {
        pageNum = Integer.parseInt(request.getParameter("pageNum"));
    }
    
    int pageSize = 10; // Default number of posts per page if not provided
    if (request.getParameter("pageSize") != null) {
        pageSize = Integer.parseInt(request.getParameter("pageSize"));
    }
    
    // Retrieve total number of reviews from the REVIEW table based on search criteria (search target and keyword)
    int totalReview = ReviewDAO.getDAO().selectTotalReview(search, keyword); // Total number of posts
    
    // Calculate total number of pages
    int totalPage = (int) Math.ceil((double) totalReview / pageSize);
    
    // Adjust pageNum to default first page if pageNum is out of bounds
    if (pageNum <= 0 || pageNum > totalPage) {
        pageNum = 1;
    }
    
    // Calculate starting row number for the current page
    int startRow = (pageNum - 1) * pageSize + 1;
    
    // Calculate ending row number for the current page
    int endRow = pageNum * pageSize;
    
    // Adjust endRow if it exceeds total number of reviews
    if (endRow > totalReview) {
        endRow = totalReview;
    }
    
    // Retrieve list of reviews from REVIEW table based on pagination and search criteria (search target and keyword)
    List<ReviewDTO> reviewList = ReviewDAO.getDAO().selectReviewList(startRow, endRow, search, keyword);// 글의 갯수별로 나누어서 화면에 나오는 조회기능 
    
    // Retrieve authorization-related information stored in session attributes as an object
    // => Provide write permission only to logged-in users
    // => Display titles only to the post author or admin for secret posts
    MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
    
    // Create Date object with current server date and time, convert it to a string using SimpleDateFormat pattern
    // => Customize output of review posting date by comparing it with current date
    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    
    // Calculate starting display number for posts
    // => e.g., if there are 91 posts in total => 1st page: 91, 2nd page: 81, 3rd page: 71, ...
    int displayNum = totalReview - (pageNum - 1) * pageSize;
%>                   
<h1>Product Reviews</h1>
<div id="review_list">
    <%-- Display the total number of searched posts --%>
    <div id="review_title">Product Reviews (<%=totalReview %>)</div>
    
    <div style="text-align: right;">
        Posts per page: 
        <select id="pageSize">
            <option value="10" <% if(pageSize==10) { %> selected <% } %>>&nbsp;10 items&nbsp;</option>    
            <option value="20" <% if(pageSize==20) { %> selected <% } %>>&nbsp;20 items&nbsp;</option>    
            <option value="50" <% if(pageSize==50) { %> selected <% } %>>&nbsp;50 items&nbsp;</option>    
            <option value="100" <% if(pageSize==100) { %> selected <% } %>>&nbsp;100 items&nbsp;</option>    
        </select>
        &nbsp;&nbsp;&nbsp;
        <% if(loginMember != null) {// If the user is logged in %>
            <button type="button" id="writeBtn">Write</button>
        <% } %>    
    </div>
    
    <%-- Display the list of searched posts --%>    
    <table>
        <tr>
            <th width="100">Post ID</th>
            <th width="500">Title</th>
            <th width="100">Author</th>
            <th width="100">Views</th>
            <th width="200">Date</th>
        </tr>
        
        <% if(totalReview == 0) {// If there are no searched posts %>
            <tr>
                <td colspan="5">No posts found.</td>
            </tr>
        <% } else { %>
            <%-- Loop through elements (ReviewDTO objects) in the List object --%>
            <% for(ReviewDTO review : reviewList) { %>
            <tr>
                <%-- Display the serial number of the post --%>
                <td><%=displayNum %></td>
                <% displayNum--; %><%-- Decrease the serial number by 1 and store it --%>
                
                <%-- Display the post title --%>
                <td class="subject">
                    <%-- Handle responses if the post is a reply --%>
                    <% if(review.getReviewRestep() != 0) {// If it's a reply %>
                        <span style="margin-left: <%=review.getReviewRelevel()*20%>px;">┖[Reply]</span>
                    <% } %>
                    
                    <%-- Compare the post status and display the title accordingly --%>
                    <%
                        String url=request.getContextPath()+"/index.jsp?workgroup=review&work=review_detail"
                            +"&reviewNum="+review.getReviewNum()+"&pageNum="+pageNum+"&pageSize="+pageSize
                            +"&search="+search+"&keyword="+keyword;
                    %>
                    <% if(review.getReviewStatus() == 1) {// If it's a normal post %>
                        <a href="<%=url%>"><%=review.getReviewSubject() %></a>
                    <% } else if(review.getReviewStatus() == 2) {// If it's a private post %>
                        <span class="subject_hidden">
                        <%-- Display the title if the logged-in user is the post author or an administrator --%>
                        <% if(loginMember != null && (loginMember.getMemberNum() == 
                            review.getReviewMemberNum() || loginMember.getMemberAuth() == 9)) { %>
                            <a href="<%=url%>"><%=review.getReviewSubject() %></a>
                        <% } else { %>
                            Only the post author or administrator can view this post.
                        <% } %>    
                        </span>
                    <% } else if(review.getReviewStatus() == 0) {// If it's a deleted post %>
                        <span class="subject_hidden">
                            This post has been deleted by the author or administrator.
                        </span>
                    <% } %>
                </td>
                
            <% if(review.getReviewStatus() != 0) {// If it's not a deleted post  %>    
                <%-- Display the post author (member name) --%>
                <td><%=review.getMemberName() %></td>
                
                <%-- Display the post view count --%>
                <td><%=review.getReviewCount() %></td>
                
                <%-- Display the post date --%>
                <td>
                <%-- Display only the time if the post was written today, otherwise display date and time --%>
                    <% if(currentDate.equals(review.getReviewRegisterDate().substring(0, 10))) { %>
                        <%=review.getReviewRegisterDate().substring(11) %>    
                    <% } else { %>
                        <%=review.getReviewRegisterDate() %>    
                    <% } %>
                </td>
            <% } else {// If it's a deleted post %>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>        
            <% } %>
            </tr>
            <% } %>
        <% } %>
    </table>
    
    <%-- Display page numbers --%>
    <%
        // Number of pages to display in one page block
        int blockSize=5;
    
        // Calculate the start page number for the page block
        // ex) 1st block: 1, 2nd block: 6, 3rd block: 11, 4th block: 16,...
        int startPage=(pageNum-1)/blockSize*blockSize+1; 
    
        // Calculate the end page number for the page block
        // ex) 1st block: 5, 2nd block: 10, 3rd block: 15, 4th block: 20,...
        int endPage=startPage+blockSize-1;
        
        // Adjust the end page number if it exceeds the total number of pages
        if(endPage > totalPage) {
            endPage=totalPage;
        }
    %>
    <%
        String myUrl=request.getContextPath()+"/index.jsp?workgroup=review&work=review_list"
            +"&pageSize="+pageSize+"&search="+search+"&keyword="+keyword;
    %>
    
    <div id="page_list">
        <%-- Provide a link to display the previous block --%>
        <% if(startPage > blockSize) { %>
            <a href="<%=myUrl%>&pageNum=<%=startPage-blockSize%>">[Previous]</a>
        <% } else { %>
            [Previous]
        <% } %>
    
        <%-- Display page numbers within the current block --%>
        <% for(int i = startPage ; i <= endPage ; i++) { %>
            <%-- Provide a link if the current page number being processed is different from the displayed page number --%>
            <% if(pageNum != i) { %>
                <a href="<%=myUrl%>&pageNum=<%=i%>">[<%=i %>]</a>
            <%} else { %>
                [<%=i %>]
            <% } %>
        <% } %>

        <%-- Provide a link to display the next block --%>
        <% if(endPage != totalPage) { %>
            <a href="<%=myUrl%>&pageNum=<%=startPage+blockSize%>">[Next]</a>
        <% } else { %>
            [Next]
        <% } %>
    </div>

    <%-- Form tag for providing search functionality --%>    
    <form action="<%=request.getContextPath() %>/index.jsp?workgroup=review&work=review_list" method="post">
        <%-- The select tag must pass the column name --%>
        <select name="search">// 이 조건이 인것만 검색한다는 이름의 search 
            <option value="member_name" <% if(search.equals("member_name")) { %>selected<% } %>>&nbsp;Author&nbsp;</option>
            <option value="review_subject" <% if(search.equals("review_subject")) { %>selected<% } %>>&nbsp;Title&nbsp;</option>
            <option value="review_content" <% if(search.equals("review_content")) { %>selected<% } %>>&nbsp;Content&nbsp;</option>
        </select>
        <input type="text" name="keyword" value="<%=keyword%>">
        <button type="submit">Search</button>
    </form>
</div>

<script type="text/javascript">
// Event handler registered when the input tag (post count) value changes
$("#pageSize").change(function() {    
    location.href="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_list"
        +"&pageNum=<%=pageNum%>&pageSize="+$("#pageSize").val()+"&search=<%=search%>&keyword=<%=keyword%>";
});

$("#writeBtn").click(function() {
    location.href="<%=request.getContextPath()%>/index.jsp?workgroup=review&work=review_write";
});
</script>
```

### Add DTO- # Not Necessarily DTO 의 목적으로는 검색결과를 받환 받기 위해 만들어 지는 것이 가장 중요한 목적이기 때문에 , db테이블 수만 큰 만드는것보다는 더 많이 만들어야 할때가 많아서, 만약 join 을 사용 해야 한다면 join 되어진  DTO 를 새로 만들어 주는것도 좋다. 
- private String memberName; <-- added 

