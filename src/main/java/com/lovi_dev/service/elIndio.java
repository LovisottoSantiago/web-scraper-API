package com.lovi_dev.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;


@Service
public class elIndio {

    public List<Product> getIndio() {
        List<Product> products = new ArrayList<>();
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Run in headless mode (no GUI)
        WebDriver driver = new FirefoxDriver(options);

        try {
            String url = "https://www.socialwapy.com/elindiomayorista/productos/13935"; 
            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".productBody")));

            // Select all product containers
            List<WebElement> elementProducts = driver.findElements(By.cssSelector(".productBody"));

            for (WebElement element : elementProducts) {
                String name = element.findElement(By.cssSelector(".productBody p")).getText();
                String price = element.findElement(By.cssSelector(".productBody span")).getText();

                String imageUrl = element.findElement(By.cssSelector(".image img")).getAttribute("Vista previa");
                if (imageUrl.isEmpty()) { // Fallback 
                    imageUrl = element.findElement(By.cssSelector(".image img")).getAttribute("src");
                }

                // Debugging output
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Image URL: " + imageUrl);
                System.out.println("---------------");

                if (!name.isEmpty() && !price.isEmpty() && !imageUrl.isEmpty()) {
                    Product product = new Product(name, price, imageUrl);
                    products.add(product);
                }
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            driver.quit(); // Close the browser
        }

        return products;
    }
}
