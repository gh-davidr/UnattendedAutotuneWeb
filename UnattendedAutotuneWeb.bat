ECHO   OFF

REM ****************************************************
REM UnattendedAutotuneWeb Windows Batch Script
REM 
REM With minimal mandatory variables
REM
REM See documentation to override other parameters
REM
REM David Richardson
REM 26 July 2019
REM
REM ****************************************************

REM -- Change this to the location of where UnattendedAutotuneWeb.jar is installed
SET    INSTALL_DIR="C:\Local_Data\"

REM -- Change this to your email address
SET    EMAIL="myemail@domain.com"

REM -- Change this to the location of chromedriver.exe
SET    DRIVER_PATH="C:\Local_Data\chromedriver.exe"

REM -- Change this to your nightscout URL
SET    NIGHTSCOUT="http://mynightscout.herokuapp.com/"

java -jar %INSTALL_DIR%\UnattendedAutotuneWeb.jar -autotune-email %EMAIL% -selenium-driver-path %DRIVER_PATH% -nightscout-url %NIGHTSCOUT%
