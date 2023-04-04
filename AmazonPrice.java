/**
 * 
 */
package com.task.foodingredients;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author roshan-pt6347
 *
 */
public class AmazonPrice
{
	WebDriver driver;

	By searchBar = By.id("twotabsearchtextbox");
	By searchClick = By.id("nav-search-submit-button");

	public AmazonPrice(WebDriver driver)
	{
		this.driver = driver;
	}

	public void searchBarText(String ingredients)
	{
		driver.findElement(searchBar).sendKeys(ingredients);
	}

	public void searchBarEnter()
	{
		driver.findElement(searchClick).click();
	}

}
