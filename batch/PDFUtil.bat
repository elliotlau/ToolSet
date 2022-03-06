@ECHO OFF
CALL ".\config\_env_config.bat"

java -jar %JAR_LOCATION% -Dclass=test -Dmethod=dsad -Dparams=dsadsa
