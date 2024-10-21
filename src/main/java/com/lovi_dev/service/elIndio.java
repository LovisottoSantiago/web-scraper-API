package com.lovi_dev.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
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

        // Lista de URLs de categorías
        List<String> categorias = Arrays.asList(
            "https://www.socialwapy.com/elindiomayorista/productos/13775", // aromatizantes
            "https://www.socialwapy.com/elindiomayorista/productos/13794", // antigrasas
            "https://www.socialwapy.com/elindiomayorista/productos/13935", // baldes
            "https://www.socialwapy.com/elindiomayorista/productos/13776"  // cepillos
        );

        try {
            for (String url : categorias) {
                driver.get(url);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".item.row.cursor"))); // Ajusta el selector si es necesario

                // Select all products
                List<WebElement> elementProducts = driver.findElements(By.cssSelector(".item.row.cursor"));

                for (WebElement element : elementProducts) {
                    String name = element.findElement(By.cssSelector(".productBody p")).getText();
                    String webPrice = element.findElement(By.cssSelector(".price span")).getText();
                    String imageUrl = element.findElement(By.cssSelector(".image img")).getAttribute("src");

                    String cleanPrice = webPrice.replace("$", "").replace(".", "").replace(",", ".").trim();
                    double profit = (Double.parseDouble(cleanPrice) * 135)/100;
                    double roundedProfit = Math.round(profit / 100.0) * 100.0;
                    String sellPrice = String.format("$%.0f", roundedProfit);
                    
                    
                    // Debugging output
                    System.out.println("Categoría: " + url);
                    System.out.println("Name: " + name);
                    System.out.println("Price: " + webPrice);
                    System.out.println("Image URL: " + imageUrl);
                    System.out.println("---------------");

                    

                    if (!name.isEmpty() && !webPrice.isEmpty() && !imageUrl.isEmpty()) {
                        Product product = new Product(name, sellPrice, imageUrl);
                        products.add(product);
                    }
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
