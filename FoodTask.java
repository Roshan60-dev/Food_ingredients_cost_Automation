package com.task.foodingredients;

import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class FoodTask
{

	public static void main(String[] args) throws InterruptedException
	{
		String foodDish;
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> priceStore = new ArrayList<String>();
		Scanner dishName = new Scanner(System.in);
		System.out.println("enter the food dish:");
		foodDish = dishName.nextLine();
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("FoodReportFinal.html");
		extent.attachReporter(spark);
		System.setProperty("webdriver.chrome.driver", "/Users/roshan-pt6347/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		driverAction(driver);
		ExtentTest Test1 = extent.createTest("Food Ingredients Test");
		ExtentTest Test2 = extent.createTest("Amazon Test");
		findIngredients(foodDish, driver, ingredients, Test1);
		storeIngredients(ingredients, driver, Test1);
		findAmazonPrice(foodDish, driver, ingredients, Test2, priceStore);
		calculateTotalAmount(driver, priceStore, Test2, foodDish);
		extent.flush();

	}

	public static void findIngredients(String foodDish, WebDriver driver, ArrayList<String> ingredients, ExtentTest test1) throws InterruptedException
	{
		driver.get("https://www.bbcgoodfood.com/");
		test1.log(Status.PASS, "open Food Site");
		IngredientsList receipe = new IngredientsList(driver);
		ArrayList<String> foodName = new ArrayList<String>();

		driver.switchTo().frame("sp_message_iframe_739867");
		receipe.clickPopupOne();
		driver.switchTo().defaultContent();
		test1.log(Status.PASS, "Site Pop Up Removed");
		receipe.textEnterSearchBar(foodDish);
		receipe.enterSearch();
		test1.log(Status.PASS, "Food dish is started searching");

		// find a user given dish in list of dish

		java.util.List<WebElement> allDishNames = driver.findElements(By.className("_2torGbn_fNOMbGw3UAasPl iwmtpuJa21jtA4y_LuOVI"));
		for(WebElement foodTitle : allDishNames)
		{

			foodName.add(foodTitle.getText());

		}
		test1.log(Status.PASS, "input name is match to the other food dish");
		System.out.println(foodName);
		for(String i : foodName)
		{
			if(i.contains(foodDish))
			{
				WebElement search = driver.findElement(By.linkText(i));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click()", search);
				System.out.println(i);
				break;

			}
			else
			{
				System.out.println("not contain");
			}
		}
		test1.log(Status.PASS, "matched food dish is clicked");

	}
	// get and store a ingredients

	public static void storeIngredients(ArrayList<String> ingredients, WebDriver driver, ExtentTest test1)
	{
		java.util.List<WebElement> ingredientsName = driver.findElements(By.cssSelector("li.pb-xxs"));
		for(WebElement ingredientList : ingredientsName)
		{
			String eachIngredientsName = "";

			// Traverse the String from start to end
			for(int i = 0; i < ingredientList.getText().length(); i++)
			{

				if(!Character.isDigit(ingredientList.getText().charAt(i)))
				{
					eachIngredientsName = eachIngredientsName + ingredientList.getText().replaceAll(",", " ").charAt(i);
				}
			}

			ingredients.add(eachIngredientsName);

		}
		test1.log(Status.PASS, "Ingredients are Stored in list");
		System.out.println(ingredients);

	}

	public static void driverAction(WebDriver driver)
	{
		// mangae window and cookies

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

	}

	public static void findAmazonPrice(String foodDish, WebDriver driver, ArrayList<String> ingredients, ExtentTest test2, ArrayList<String> priceStore) throws InterruptedException
	{
		// open amazon and started search

		driver.get("https://www.amazon.in/");
		test2.log(Status.PASS, "open amazon Site");
		AmazonPrice amazon = new AmazonPrice(driver);

		Integer calulateRate = 0;

		// get a rate of ingredients
		int i = 1;

		for(String ingredientsName : ingredients)
		{

			amazon.searchBarText(ingredientsName);
			amazon.searchBarEnter();

			java.util.List<WebElement> ingredientsAmount = driver.findElements(By.className("a-price-whole"));
			Thread.sleep(1000);
			// looping perform utill find all ingredients price

			for(WebElement price : ingredientsAmount)
			{

				String temp1 = price.getText().replaceAll(",", "");
				int price2 = Integer.parseInt(temp1);
				if(price2 < 1000)
				{

					priceStore.add(price.getText());
					Thread.sleep(2000);
					System.out.println(ingredientsName + ":" + price.getText());
					break;

				}

			}
			test2.log(Status.PASS, i + " rate is Stored  ");
			i = i + 1;

			driver.get("https://www.amazon.in/");
		}

		System.out.println(ingredients);
		System.out.println(priceStore);
		test2.log(Status.PASS, "Ingredients and the Rate of Ingredients are printed");

	}

	// calculate total rate
	public static void calculateTotalAmount(WebDriver driver, ArrayList<String> priceStore, ExtentTest test2, String foodDish)
	{
		Integer findRate = 0;
		for(String calculate : priceStore)
		{
			// adding a all price of ingredients

			String temp = calculate.replaceAll(",", "");
			int number1 = Integer.parseInt(temp);
			findRate = findRate + number1;

			System.out.println(calculate);
		}
		test2.log(Status.PASS, "Amount of Total Ingredients are caluclate");
		System.out.println("total amount to make " + foodDish + ":" + findRate);
		test2.log(Status.PASS, "ingredients and rate are displayed");

		driver.close();
		driver.quit();
	}
}
