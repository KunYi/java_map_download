:: cd /d %~dp0
:: %1 start "" mshta vbscript:createobject("shell.application").shellexecute("""%~0""","::",,"runas",1)(window.close)&exit

@echo off

set JRE_HOME=.\env\jbr17
set PATH=%JRE_HOME%\bin;

start javaw -jar --illegal-access=deny .\bin\map-download.jar
