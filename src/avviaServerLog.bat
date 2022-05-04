@echo off
set L=C:\prg\myapps\DiarioDiabete\dist\lib\
set LIBS=%L%xstream-1.4.7.jar;%L%xmlpull-1.1.3.1.jar;%L%xpp3_min-1.1.4c.jar;%L%mysql-connector-java-5.1.34-bin.jar;
C:\prg\jdk8\bin\javac -classpath %LIBS% ServerLogAttivita.java

ECHO ************************* Registro di Log *************************
c:\prg\jdk8\bin\java -classpath %LIBS%; ServerLogAttivita

pause