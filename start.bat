@echo off
set /p "port=Enter Port: "
javac Server.java NumberServer.java
java NumberServer %port%
pause