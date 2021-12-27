package com.Epam_training.hooks;

import com.Epam_training.pages.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTests {
    public static WebDriver driver;
    public static Properties appProps;

    /**
     * En esta clase cargamos las configuraciones del sistema y definimos el webdriver o navegador
     * a ser utilizado en la ejecucion de las pruebas cualquier navegador diferente a Firefox lanzara
     * por defecto el navegador de Chrome como ejecutor de pruebas
     */
    @Before
    public void openNavigator() {
        //Carga de propiedades
        String appConfigPath = "src/test/resources/config.properties";
        appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Seleccion de webdriver
        if (appProps.getProperty("navigator").equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = (new FirefoxDriver());
            cleanNavigator();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = (new ChromeDriver());
            cleanNavigator();
        }
    }

    /**
     * Esta clase cerrara nuestro navegador o webdriver tras la ejecucin de cada una de las pruebas establecidads
     */
    @After
    public void closeNavigator() {
        driver.quit();
    }

    /**
     * Este metodo nos ayudara no solo a limpiar cookies si no tambien nos ayudara a cerrar cualquier pestaña
     * residuo o producto de la ejecucion de alguna prueba
     */
    public void cleanNavigator() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    /**
     * Este metodo nos permitira usar nuestro webdriver para navegar a cualquier URL
     *
     * @param url parametro que recibe un String y se espera que se indique la URL deseada en este
     */
    public static void goTo(String url) {
        driver.get(url);
    }

    /**
     * Este parametro sera el responsable de la toma de evidencias tras la ejecucion de cada paso de prueba
     * guardando una foto por cada paso como correspondiente evidencia
     *
     * @param scenario
     */
    @AfterStep
    public void addScreenshot(Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "image");
    }

    /**
     * Metodo de inicializacion nos permitira pasar el webdriver a la pagina principal o inicial entregando
     * un objeto de este tipo el cual permitira la interaccion con sus elementos
     *
     * @return objeto de tipo Homepage que permitira la interaccion con sus elementos
     */
    public static HomePage loadingHomePage() {
        return new HomePage(driver);
    }

}
