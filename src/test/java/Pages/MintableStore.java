package Pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.selectors.Selectors;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MintableStore extends PageObject {


        MintableStorePage Storepage;

        private JavascriptExecutor js;

        @Step
        public void openApplication(){Storepage.open();}

        @Step
        public void isCollectionLabelExist(){
            $(By.xpath("//*[contains(text(),'Collections')]"));
            System.out.println(getDriver().getTitle());
            Assert.assertTrue(getTitle().contains("Browse collections digital items for sale."));
        }

        @Step
        public void isBrokenImagesAvailable() throws IOException {

            List<WebElement> images = getDriver().findElements(By.tagName("img"));
            System.out.println("No of images are " + images);

            List<String> urlList = new ArrayList<>();
            for (WebElement e:images){
               String image= e.getAttribute("src");
               urlList.add(image);
                //checkBrokenImageLink(image);
            }

            urlList.parallelStream().forEach(e-> {
                try {
                    checkBrokenImageLink(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        }

        public static void checkBrokenImageLink(String imageUrl) throws IOException {

            URL url = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() >=400){
                System.out.println(imageUrl + "---> " + httpURLConnection.getResponseMessage()+" >>> is a broken link");
            } else {
                System.out.println(imageUrl + "---> " + httpURLConnection.getResponseMessage()+" >>> is not broken link");
            }

        }






        @Step
        public void isBrokenlinksAvailable() throws IOException {

            List<WebElement> linklist = getDriver().findElements(By.tagName("a"));
            linklist.addAll(getDriver().findElements(By.tagName("img")));

            System.out.println("size of full links and image : "+linklist.size());

            List<WebElement> activeLinks = new ArrayList<WebElement>();

            for (int i=0;i<linklist.size();i++){
                System.out.println(linklist.get(i).getAttribute("href"));
                if (linklist.get(i).getAttribute("href")!=null &&
                        (!linklist.get(i).getAttribute("href").contains("javascript"))){
                    activeLinks.add(linklist.get(i));
                }
            }

            System.out.println("size of active link : " + activeLinks.size());

            for (int k=0;k<activeLinks.size();k++){
               HttpURLConnection connection =
                       (HttpURLConnection) new URL(activeLinks.get(k).getAttribute("href")).openConnection();

               connection.connect();
               String response = connection.getResponseMessage();
               connection.disconnect();
               System.out.println(activeLinks.get(k).getAttribute("href")+"--->"+response);

            }


        }


        public void searchFor(String search){
            typeInto($(Selectors.xpathOrCssSelector("//input[@placeholder=\"Search for NFTs...\"]")),search);
        }

      public void visible(String search){
        waitForTextToAppear(search);
        Assert.assertTrue(search,true);
        }

        public void enterUsername(String username){

            clickOn($(Selectors.xpathOrCssSelector("//button[@type='button' and text()='Login']")));
            waitForTextToAppear("Welcome Back");
            typeInto($(Selectors.xpathOrCssSelector("//*[@name='username']")),username);
        }

    public void enterPassword(String password){
        typeInto($(Selectors.xpathOrCssSelector("//*[@name='password']")),password);
    }


    public void clickLogin(){
        clickOn($(Selectors.xpathOrCssSelector("//button[contains(text(),'Log In')]")));
        waitForTextToAppear("Connect Wallet",10000);
        Assert.assertTrue("Connect Wallet",true);
    }

}
