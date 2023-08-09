package Tdd.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BookMyShow {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		driver.get("https://in.bookmyshow.com/explore/home/");

		WebDriverManager.chromedriver().setup();
		WebDriver driver1 = new ChromeDriver();
		driver1.get("https://in.bookmyshow.com/explore/home/");
		driver1.manage().window().maximize();

		WebElement City = driver1.findElement(By.xpath("//span[text()='Bengaluru']"));
		City.click();

		WebElement Signin = driver1.findElement(By.xpath("//div[@class='sc-dtInlm bnklgU']"));
		Signin.click();

		WebElement Sign = driver1.findElement(By.xpath("//div[text()='Continue with Email']"));
		Sign.click();

		WebElement Email = driver1.findElement(By.xpath("//input[@id='emailId']"));
		Email.click();
		Email.sendKeys("selauto@yopmail.com");

		WebElement button1 = driver1.findElement(By.xpath("//button[text()='Continue']"));
		button1.click();

		driver.get("https://yopmail.com/wm");

		WebElement selauto = driver.findElement(By.xpath("//input[@id='login']"));
		selauto.sendKeys("selauto@yopmail.com");

		WebElement clickarrow = driver.findElement(By.xpath("//div[@id='refreshbut']"));
		clickarrow.click();
		driver.findElement(By.cssSelector("body")).sendKeys("Keys.CONTROL + t");
		Thread.sleep(2000);

		String originalTab = driver.getWindowHandle();
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(originalTab)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		driver.get("http://www.yopmail.com");
		driver.findElement(By.id("login")).sendKeys("selauto@yopmail.com");
		driver.findElement(By.cssSelector("input[value='Check Inbox']")).click();
		Thread.sleep(3000);

		WebElement latestEmail = driver.findElement(By.cssSelector(".m"));
		latestEmail.click();

		String emailContent = driver.findElement(By.id("mailmillieu")).getText();
		String otp = extractOTPFromEmail(emailContent);

		driver.switchTo().window(originalTab);

		driver.findElement(By.id("otp")).sendKeys(otp);
		driver.findElement(By.id("loginOTP")).click();

		WebElement greetings = driver.findElement(By.cssSelector(".user-greeting span"));
		if (greetings.getText().equals("Hi, Guest")) {
			System.out.println("User successfully signed in.");
		} else {
			System.out.println("Sign-in failed.");
		}

		driver.quit();
	}

	private static String extractOTPFromEmail(String emailContent) {

		return emailContent.substring(emailContent.indexOf("OTP:") + 5, emailContent.indexOf("OTP:") + 10);
	}

}
