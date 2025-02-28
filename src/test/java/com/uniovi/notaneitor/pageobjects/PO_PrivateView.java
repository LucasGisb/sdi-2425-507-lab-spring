package com.uniovi.notaneitor.pageobjects;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
//Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
//Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
//Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void loginMethod(WebDriver driver, String user, String password) {
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, user, password);
    }

    static public void logoutMethod(WebDriver driver, int idioma) {
        String loginText = PO_HomeView.getP().getString("signup.message", idioma);
        PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
    }

    static public void fillFormAddProfessor(WebDriver driver, String dni, String name, String lastName, String category) {
        WebElement dniElement = driver.findElement(By.name("dni"));
        dniElement.click();
        dniElement.clear();
        dniElement.sendKeys(dni);
        WebElement nameElement = driver.findElement(By.name("name"));
        nameElement.click();
        nameElement.clear();
        nameElement.sendKeys(name);
        WebElement lastNameElement = driver.findElement(By.name("surname"));
        lastNameElement.click();
        lastNameElement.clear();
        lastNameElement.sendKeys(lastName);

        WebElement categoryElement = driver.findElement(By.name("category"));
        categoryElement.click();
        categoryElement.clear();
        categoryElement.sendKeys(category);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}