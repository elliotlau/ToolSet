@echo off
SETLOCAL EnableDelayedExpansion
CALL ".\config\_env_config.bat"
CALL ".\config\_PDFUtil_config.bat"

IF [%1]==[] (
	ECHO %ERRRO_MSG% - METHOD
	GOTO FAILURE
)
IF [%1]==[/?] (
	ECHO CMD : PDFUtil.bat [METHOD] [PARAMS]
	ECHO   [METHOD] [PARAMS]
	ECHO 1. decrypt "sourceDir, Password"
	GOTO END
)
SET METHOD=%1
IF [%2]==[] (
	ECHO %ERRRO_MSG% - PARAMS
	GOTO FAILURE
)
SET PARAMS=%2

ECHO [%TIME%] %UTIL_NAME% - START [RUNNING]

java -jar -Dclass=%CLASS% -Dmethod=%METHOD% -Dparams=%PARAMS% %JAR_LOCATION%

:SUCCESS
ECHO [%TIME%] %UTIL_NAME% - END   [SUCCESS]
GOTO END

:FAILURE
ECHO [%TIME%] %UTIL_NAME% - END   [FAILURE]
GOTO END

:END