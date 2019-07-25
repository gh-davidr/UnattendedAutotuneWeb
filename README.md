# UnattendedAutotuneWeb

A Java program that can be scheduled to run *unattended* at set frequencies to launch and trigger AutotuneWeb runs.

## Usage

The program takes several parameters as described below (Mandatory ones are in **bold**)

 - **-nightscout-url**               (URL to your Nightscout environment)
 - **-autotune-email**               (Your email address)
 - **-selenium-driver-path**         (Location of the Selenium Driver file chromedriver.exe)
 - -selenium-driver                  (Default and at present *only* option is **webdriver.chrome.driver**)
 - -autotuneweb-url                  (Default **https://autotuneweb.azurewebsites.net/**)
 - -autotune-min-carb-impact         (Default **8**)
 - -autotune-pump-basal-increment    (Default **0.01**)
 - -autotune-categorize-uam-as-basal (Default **False**)
 - -autotune-insulin-type            (Default **Rapid** Other possible value is **Ultra**)
 - -autotune-days-data               (Default **7** Range is between **1** and **30**)