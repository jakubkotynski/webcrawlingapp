package pl.dimirhouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WebScrap {

    private String listXPath = "//div[@class='cat-prod-row js_category-list-item js_clickHashData js_man-track-event ']";
    private String nameXPath = ".//strong[@class='cat-prod-row-name']/a";
    private String priceXPath = ".//span[@class='price']";
    private String pictureXPath = ".//a[@class='js_clickHash ']";

    public List<String> scrap(String baseUrl){

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        List<String> jsonList = new ArrayList<>();

        try {
            String searchUrl = baseUrl;
            HtmlPage page = client.getPage(searchUrl);
            List<HtmlElement> elements = (List<HtmlElement>) page.getByXPath(listXPath);

            if (elements.isEmpty()) {
                System.out.println("Not found");
            } else {
                for (HtmlElement htmlItem : elements) {
                    HtmlAnchor itemAnchor = htmlItem.getFirstByXPath(nameXPath);
                    HtmlElement itemPrice = htmlItem.getFirstByXPath(priceXPath);
                    HtmlElement itemPicture = htmlItem.getFirstByXPath(pictureXPath);

                    ItemInfo item = new ItemInfo();
                    item.setTitle(itemAnchor.asText());
                    item.setUrl(searchUrl + itemAnchor.getHrefAttribute());
                    item.setPrice(itemPrice.asText());
                    item.setPicture(itemPicture.asText());

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item);

                    jsonList.add(jsonString);

//                    try{
//                        PrintStream printStream = new PrintStream(new FileOutputStream("./list.xml", true));
//                        printStream.println(jsonString);
//                    } catch (FileNotFoundException e){
//                        System.out.println(e);
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }
}
