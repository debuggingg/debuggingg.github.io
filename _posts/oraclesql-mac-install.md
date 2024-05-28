---
layout: single
title:  " Oracle SQL-Install with Docker"
---
# Install Oracle SQL 
- 

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

    
