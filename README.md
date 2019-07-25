# UnattendedAutotuneWeb

A Java program that can be scheduled to run *unattended* at set frequencies to launch and trigger AutotuneWeb runs.

## Usage

1. Download Selenium Chrome Driver from this [link](https://sites.google.com/a/chromium.org/chromedriver/).  Make sure to store in a path without spaces in the name - for example C:\Local_Data\UnattendedAutotuneWeb\Drivers
2. Download the UnattendedAutotuneWeb.jar file from [here](https://mega.nz/#!T7YEQaxI!zINDYKlTRDehG0rWxmhouQnqLMRwYiTM9nh7epxmp68)
3. Decide the options (See Command Line Options as below) and try the program out
4. Then schedule using Windows Schedule.  See this [page](https://www.ilovefreesoftware.com/11/windows-10/configure-run-scheduled-tasks-windows-10.html) for instructions on scheduling

## Command Line Options

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