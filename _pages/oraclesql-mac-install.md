---
layout: single
title: " Oracle SQL-Install with Docker M1 MACBOOK"
permalink: /info/
---
# Install Oracle SQL 
- SID: ORCLCDB  <- oracle enterprice version provided SID :
---
## Oracle SQL developer 
- oracle.com
  - Resources on top of menu ->developers download -> SQL Developer -> Mac ARM64 with JDK 11 included

## docker download
- googling docker desktop download for mac -> download for mac -apple chip
- create a folder in an easily accessible location. ex) workspace
  
  
## download sql from oracle 
- oracle.com
  - Resources on top of menu ->developers download -> Database Enterprise/Standard Editions ->Oracle Database 19c for LINUX ARM (aarch64)
---
If you have downloaded both files, start Docker and place the Oracle file in the Downloads folder temporarily. Then, open a terminal (iTerm) and proceed with the following steps.

- cd whatever you create folder path -> ex)cd /Users/workspace
- clone the repository from GitHub. -> ex) git clone https://github.com/oracle/docker-images
- the docker-images from the GitHub repository will be downloaded into the folder I specified.
-Now, copy the file you downloaded from Oracle. Then, paste it into the following path:
your-folder(workspace)/docker-images/OracleDatabase/SingleInstance/dockerfiles/19.3.0

## To create an Oracle SQL container using Docker,
- Navigate to the docker-images directory
  - cd your-folder(workspace)/docker-images/OracleDatabase/SingleInstance/dockerfiles/
- Build the Docker image: This script will create a Docker image for Oracle Database 19.3.0.
  - ./buildContainerImage.sh -v 19.3.0 -e
- Run the Docker container: After the image is successfully built, you can run the Docker container with the following command:
  - docker run -d --name oracle19 -e ORACLE_PWD=xxxxx -p 1521:1521 oracle/database:19.3.0-ee
    - name oracle19 -e ORACLE_PWD=xxxxx <- your own setting.
- Check the container status:
  - docker ps -> might (health starting )- Wait until the container installation is complete.

![[Screenshot 2024-05-29 at 11.37.50 PM.png]]
 
---

- open Oracle Sql developer
 create connetion.-> new(+) symbol -change role Sysdba -> username : sys -> password: yourpassword
 -> other thing same except SID -> change SID xe to ORCLCDB -> test : success -> connet
---
---
From now on,proceed with installing the SQL data for learning purposes in the classroom.

- drag and drop scott_create.sql to your work sheet.
```
.
.
GRANT CONNECT,RESOURCE,UNLIMITED TABLESPACE TO scott IDENTIFIED BY tiger;
ALTER USER scott DEFAULT TABLESPACE USERS;
ALTER USER scott TEMPORARY TABLESPACE TEMP;
CONNECT scott/tiger;
.
.
```
- (alter session set "_ORACLE_SCRIPT"=true; ) copy and paste line just above the GRANT statement and run
  -  ask:  which user to use, then you can simply use 'sys'
  -  and run again alter session set "_ORACLE_SCRIPT"=true;

- create new again.
  - this time role: default -> username  :scott pw: tiger -> SID ORCLCDB -> test
   - copy and past all cott_create.sql to new sheet. and run everything until right above
     SET TERMOUT ON
---
# now enjoy your SQL 

![](obsidian://open?vault=debuggingg.github.io&file=assets%2Fimages%2Fbear-ezgif.com-resize.gif)

--- 

#### reference 
>- youtube:
>https://www.youtube.com/watch?v=uxvoMhkKUPE&t=556s
  

#### web
>- Oracle sql developer:
>https://www.oracle.com/database/sqldeveloper/technologies/download/
>- Oracle sql :
https://www.oracle.com/database/technologies/oracle-database-software-downloads.html
- docker:
https://www.docker.com/products/docker-desktop/

---
---
---
# extra information 
# Docker 
- Imagine Legos for building software. Each Lego piece is a container that holds everything an app needs to run, like tiny building blocks. These containers can snap together (like Legos!) to create complex applications.
  - Docker is the box that holds all the Legos (containers) and gives you instructions to build cool things (applications).  There's also a Lego store (Docker Hub) where you can find already built creations (pre-built containers) to use instead of making your own.
    - Download docker for Mac. -
      - (iterm) docker --version, docker ps, docker ps-a , docker context ls(docker context)
# HomeBrew 
- go to website https://brew.sh/
  - copy code (2024/05/28) currently they privode code below:
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    - pw: -> following order -> checkv version
       -(iterm) brew --version
# colima 
- brew install colima
  - colima start --memory 4 --arch x86_64 ( colima start -- whatever you want to setting)
  - According to Google default colima start = memory 2

    
