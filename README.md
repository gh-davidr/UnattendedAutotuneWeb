# UnattendedAutotuneWeb

A Java program that can be scheduled to run *unattended* at set frequencies to launch and trigger [AutotuneWeb](https://github.com/MarkMpn/AutotuneWeb) runs.

## Usage

1. Download Selenium Chrome Driver from this [link](https://sites.google.com/a/chromium.org/chromedriver/).  Make sure to store in a path without spaces in the name - for example C:\Local_Data\UnattendedAutotuneWeb\Drivers
2. Download the UnattendedAutotuneWeb.jar and UnattendedAutotuneWeb.bat files from [here](https://mega.nz/#!T7YEQaxI!zINDYKlTRDehG0rWxmhouQnqLMRwYiTM9nh7epxmp68).
3. Edit UnattendedAutotuneWeb.bat and set the four key variables needed to run the program
4. Run UnattendedAutotuneWeb.bat to see it in action!
5. Then schedule using Windows Schedule.  See this [page](https://www.ilovefreesoftware.com/11/windows-10/configure-run-scheduled-tasks-windows-10.html) for instructions on scheduling

## What happens

The Java program uses the Selenium tool to launch the Web browser, navigate to the AutotuneWeb page and then manipulate the controls based on parameters it receives.  Once complete, it will close the window.  The behaviour is just the same as if you had performed the same actions manually.

## Diagnostics

The program generates output in C:\temp\UnattendedAutotuneWeb.log.  Look in this file to see that the program has run and also where you are in the queue as it grabs this reply back before closing the browser.

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