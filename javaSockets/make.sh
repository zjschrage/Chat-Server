#!/bin/bash

javac Server.java Client.java
rm ../javaSocketsLib/*.class
mv ./*.class ../javaSocketsLib