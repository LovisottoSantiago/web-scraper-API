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
public class ScrapingServiceExample {

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        // Set the path to geckodriver
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

        // Set Firefox options
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Run in headless mode (no GUI)

        // Initialize the WebDriver
        WebDriver driver = new FirefoxDriver(options);

        try {
            String url = "http://books.toscrape.com/";
            driver.get(url);

            // Wait until the book elements are present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product_pod")));

            // Select all book containers
            List<WebElement> elementProducts = driver.findElements(By.cssSelector(".product_pod"));

            for (WebElement element : elementProducts) {
                String name = element.findElement(By.cssSelector("h3 a")).getAttribute("title");
                String price = element.findElement(By.cssSelector(".price_color")).getText();

                if (!name.isEmpty() && !price.isEmpty()) {
                    Product product = new Product(name, price);
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
