Add-Type -Path "C:\selenium\driver\lib\net40\WebDriver.dll"
$driver = New-Object "OpenQA.Selenium.Chrome.ChromeDriver"

$CWD = Split-Path $MyInvocation.MyCommand.Path -parent
$filename = "$CWD\config.xml"
$xmlfile = [xml](Get-Content $filename)

$conf = @{}
foreach($data in $xmlfile.config.data){
    $conf.Add($data.name, $data.value)
}


function waitForElement($locator, $timeInSeconds,[switch]$byClass,[switch]$byName){
    #this requires the WebDriver.Support.dll in addition to the WebDriver.dll
    Add-Type -Path C:\WebDriver.Support.dll
    $webDriverWait = New-Object OpenQA.Selenium.Support.UI.WebDriverWait($script:driver, $timeInSeconds)
    try{
        if($byClass){
            $null = $webDriverWait.Until([OpenQA.Selenium.Support.UI.ExpectedConditions]::ElementIsVisible( [OpenQA.Selenium.by]::ClassName($locator)))
        }
        elseif($byName){
            $null = $webDriverWait.Until([OpenQA.Selenium.Support.UI.ExpectedConditions]::ElementIsVisible( [OpenQA.Selenium.by]::Name($locator)))
        }
        else{
            $null = $webDriverWait.Until([OpenQA.Selenium.Support.UI.ExpectedConditions]::ElementIsVisible( [OpenQA.Selenium.by]::Id($locator)))
        }
        return $true
    }
    catch{
        return "Wait for $locator timed out"
    }
}



# �����̓Ƃ茾�`�����l����URL
$driver.url = $conf.slackUrl

# ���O�C����ʂɃ��_�C���N�g�����̂�҂�
waitForElement 'email' 20

# ���O�C����ʂɂă��O�C��
$email = $driver.FindElementById("email")
$email.SendKeys($conf.slackEmail)

$pass = $driver.FindElementById("password")
$pass.SendKeys($conf.slackPassword)

$btn = $driver.FindElementById("signin_btn")
$btn.Click()

waitForElement 'msg_input' 20
# ����Ȃ�Ƃ��ł��Ȃ��́H
Start-Sleep -s 5
$driver.executeScript("var elm =document.querySelector('#msg_input .ql-editor'); elm.innerHTML = '<p>���������</p>';")
