@echo off
cls
g++ .\src\*.c -I . -o u_release.exe
.\u_release.exe
@echo on