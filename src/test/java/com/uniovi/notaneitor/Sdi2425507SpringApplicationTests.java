package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2425507SpringApplicationTests {

    //static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";

    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";

    // Para la  versión de Firefox 121 en adelante la ruta de firefo en MAC es

    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox";

    //Común a Windows y a MACOSX

    //
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);

    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }


    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }
    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
                PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }


    //PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    @Test
    @Order(5)
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    @Order(6)
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText =  "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
// Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate",
                PO_Properties.getSPANISH() );
        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }
    //PR06B. Prueba del formulario de registro. Nombre corto.
// Propiedad: Error.signup.dni.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result =  PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH() );
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    @Test
    @Order(9)
    void PR06C() {  //Comprueba el campo de apellido
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990C", "Josefo", "Pe", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length",
                PO_Properties.getSPANISH());
        // Comprobamos el error de Apellido corto.
        String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(10)
    void PR06D() { //comprueba la contrasena
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990D", "Josefo", "Perez", "123", "123");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length",
                PO_Properties.getSPANISH());
        // Comprobamos el error de Contraseña corta.
        String checkText = PO_HomeView.getP().getString("Error.signup.password.length",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(11)
    void PR06E() { //comprueba la repetición de contraseña
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990E", "Josefo", "Perez", "12345", "54321");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
        // Comprobamos el error de contraseñas no coincidentes.
        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }


    //Ahora de login
    @Test
    @Order(12)
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText =  "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // PR08: Identificación válida con usuario de ROL profesor (99999993D/123456)
    @Test
    @Order(13)
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada de Profesor
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(14)
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Admin
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }


    // PR10: Identificación inválida con usuario de ROL alumno (99999990A/123456)
    @Test
    @Order(15)
    public void PR10() {
        // Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario con credenciales inválidas
        PO_LoginView.fillLoginForm(driver, "99999990A", "wrongpassword");
        // Comprobamos que no entramos en la página privada
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // PR11: Identificación válida y desconexión con usuario de ROL usuario (99999990A/123456)
    @Test
    @Order(16)
    public void PR11() {
        // Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        // Comprobamos que entramos en la página privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Desconectamos
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        // Comprobamos que hemos vuelto a la página de login
        checkText = "Identifícate";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }


    //Pruebas de Private View
    //PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse usando el rol de estudiante
    @Test
    @Order(17)
    public void PR12() {
        //Vamos al formulario de logueo.
        //PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        //PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        PO_PrivateView.loginMethod(driver, "99999990A", "123456");

//COmprobamos que entramos en la pagina privada de Alumno
        String checkText =  "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
//Contamos el número de filas de notas
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(2, marksList.size());
//Ahora nos desconectamos  y comprobamos que aparece el menú de registro
        //String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        //PO_PrivateView.clickOption(driver, "logout", "text", loginText);
        PO_PrivateView.logoutMethod(driver, PO_Properties.getSPANISH());
    }

    /*
    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    @Test
    @Order(18)
    public void PR13() {
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        //SeleniumUtils.esperarSegundos(driver, 1);
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Esperamos por la ventana de detalle
        checkText = "Detalles de la nota";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        //Ahora nos desconectamos comprobamas que aparece el menu de registrarse
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }
     */
    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    @Test
    @Order(18)
    public void PR13() {
        //Comprobamos que entramos en la pagina privada de Alumno
        //PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        PO_PrivateView.loginMethod(driver, "99999990A", "123456");

        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        //Contamos las notas
        //By enlace = By.xpath("//td[contains(text(), 'Nota A4')]/following-sibling::*[2]");
        //driver.findElement(enlace).click();

        PO_View.checkAndClickElement(driver, "free", "//td[contains(text(), 'Nota A4')]/following-sibling::*[2]", 0);

        //Esperamos por la ventana de detalle
        checkText = "Detalles de la nota";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        //Ahora nos desconectamos comprobamas que aparece el menu de registrarse
        //String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        //PO_PrivateView.clickOption(driver, "logout", "text", loginText);
        PO_PrivateView.logoutMethod(driver, PO_Properties.getSPANISH());
    }


    //P14. Loguearse como profesor y Agregar Nota A2.
    //P14. Esta prueba podría encapsularse mejor ...
    @Test
    @Order(19)
    public void PR14() {
        //Vamos al formulario de login.
        //PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        PO_PrivateView.loginMethod(driver, "99999993D", "123456");

        //Cmmprobamos que entramos en la pagina privada del Profesor
        PO_View.checkElementBy(driver, "text", "99999993D");
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free",
        //        "//*[@id='myNavbar']/ul[1]/li[2]");
        //elements.get(0).click();

        PO_View.checkAndClickElement(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]", 0);


        //Esperamos a  que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        //elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/add')]");
        //Pinchamos en agregar Nota.
        //elements.get(0).click();

        PO_View.checkAndClickElement(driver, "free", "//a[contains(@href, 'mark/add')]", 0);

        //Ahora vamos a rellenar la nota con mas de 20 caracteres. //option[contains(@value, '4')]
        String checkText = "Nota sistemas distribuidos";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");
        //Esperamos a que se muestren los enlaces de paginación de la lista de notas
        //elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        //elements.get(4).click();

        PO_View.checkAndClickElement(driver, "free", "//a[contains(@class, 'page-link')]", 4);

        //Comprobamos que aparece la nota en la página
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);

        Assertions.assertEquals(checkText, elements.get(0).getText());
        //Ahora nos desconectamos y comprobamos que aparece el menú de registrarse
        //String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        //PO_PrivateView.clickOption(driver, "logout", "text", loginText);
        PO_PrivateView.logoutMethod(driver, PO_Properties.getSPANISH());
    }


    @Test
    @Order(20)
    public void PR15() {
        //Vamos al formulario de login.
        //PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        PO_PrivateView.loginMethod(driver, "99999993D", "123456");

        //Comprobamos que entramos en la página privada del Profesor
        PO_View.checkElementBy(driver, "text", "99999993D");
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        //List<WebElement>  elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free",
        //        "//*[@id='myNavbar']/ul[1]/li[2]");
        //elements.get(0).click();

        PO_View.checkAndClickElement(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]", 0);

        //Pinchamos en la opción de lista de notas.
        //elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/list')]");
        //elements.get(0).click();

        PO_View.checkAndClickElement(driver, "free", "//a[contains(@href, 'mark/list')]", 0);

        //Esperamos a que se muestren los enlaces de paginación la lista de notas
        //elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la última página
        //elements.get(4).click();

        PO_View.checkAndClickElement(driver, "free", "//a[contains(@class, 'page-link')]", 4);

        //Esperamos a que aparezca la Nueva nota en la última página
        //Y Pinchamos en el enlace de borrado de la Nota "Nota sistemas distribuidos"
        //elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(), 'Nota sistemas distribuidos')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        //elements.get(0).click();

        PO_View.checkAndClickElement(driver, "free", "//td[contains(text(), 'Nota sistemas distribuidos')]/following-sibling::*/a[contains(@href, 'mark/delete')]", 0);

        //Volvemos a la última página
        //elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //elements.get(4).click();

        PO_View.checkAndClickElement(driver, "free", "//a[contains(@class, 'page-link')]", 4);

        //Y esperamos a que NO aparezca la última "Creando una nota nueva"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota sistemas distribuidos",PO_View.getTimeout());
        //Ahora nos desconectamos comprobamos que aparece el menú de registrarse
        //String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        //PO_PrivateView.clickOption(driver, "logout", "text", loginText);
        PO_PrivateView.logoutMethod(driver, PO_Properties.getSPANISH());
    }

    //Refactorizacion de PR12-15
    //Login
    //Logout
    //Click en distintos elementos

    //Anadir un profesor VALIDO
    @Test
    @Order(21)
    public void PR16() {
        //Vamos al formulario de logueo.
        PO_PrivateView.loginMethod(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Admin
        PO_View.checkElementBy(driver, "text", "99999988F");
        //Pinchamos en la opción de menú de Profesores
                                                                                        //3
        PO_View.checkAndClickElement(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]", 0);
        //Pinchamos en la opción de añadir profesor
        PO_View.checkAndClickElement(driver, "free", "//a[contains(@href, 'professor/add')]", 0);
        //Y, ya dentro, rellenamos el formulario
        PO_PrivateView.fillFormAddProfessor(driver, "99999999A", "Josefo", "Perez", "77777");
        //Comprobamos que aparece el profesor en la lista
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", "99999999A");
        Assertions.assertEquals("99999999A", elements.get(0).getText());
    }

    //Ahora invalido
    @Test
    @Order(22)
    public void PR17() {
        //Vamos al formulario de logueo.
        PO_PrivateView.loginMethod(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Admin
        PO_View.checkElementBy(driver, "text", "99999988F");
        //Pinchamos en la opción de menú de Profesores
        //3
        PO_View.checkAndClickElement(driver, "free", "//*[@id='myNavbar']/ul[1]/li[4]", 0);
        //Pinchamos en la opción de añadir profesor
        PO_View.checkAndClickElement(driver, "free", "//a[contains(@href, 'professor/add')]", 0);
        //Y, ya dentro, rellenamos el formulario
        PO_PrivateView.fillFormAddProfessor(driver, "99999988FFF", "Josefo", "Perez", "77777");


        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.professor.dni.format",
                PO_Properties.getSPANISH() );
        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.professor.dni.format",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //Un usuario no valido intenta realizar una peticion de anadir profesor
    @Test
    @Order(23)
    public void PR18() {
        PO_PrivateView.loginMethod(driver, "99999990A", "123456");

        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);

        driver.navigate().to("http://localhost:8090/professor/add");

        //Comprobar que salta la excepcion 403
        checkText = "There was an unexpected error (type=Forbidden, status=403).";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }



}
