/**
 * 
 */
package com.task.foodingredients;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import net.bytebuddy.asm.Advice.Enter;

/**
 * @author roshan-pt6347
 *
 *
 */
public class IngredientsList
{
	WebDriver driver;

	By popupOne = By.cssSelector("[title='AGREE']");
	By searchBar = By.cssSelector("[placeholder='Ingredient, dish, keyword...']");
	By enterSearch = By.cssSelector("input[name='q']");
	By popupTwo = By.cssSelector("button.pn-widget__link']");

	public IngredientsList(WebDriver driver)
	{
		this.driver = driver;
	}

	public void clickPopupOne()
	{
		driver.findElement(popupOne).click();
	}

	public void textEnterSearchBar(String FoodDish)
	{
		driver.findElement(searchBar).sendKeys(FoodDish);
	}

	public void enterSearch()
	{
		driver.findElement(enterSearch).sendKeys(Keys.ENTER);
	}

	public void clickPopupTwo()
	{
		driver.findElement(popupTwo).click();
	}
}
