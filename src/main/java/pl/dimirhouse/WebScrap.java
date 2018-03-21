package pl.dimirhouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.ArrayList;
import java.util.List;

import static pl.dimirhouse.Base64Converter.pictureInBase64;
import static pl.dimirhouse.ImageUrlTaker.imagesUrls;

public class WebScrap {

    private String listXPath = "//div[@class='cat-prod-row js_category-list-item js_clickHashData js_man-track-event ']";
    private String nameXPath = ".//strong[@class='cat-prod-row-name']/a";
    private String priceXPath = ".//span[@class='price']";

    public List<String> scrap(String baseUrl){

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        List<String> jsonList = new ArrayList<>();

        try {
            String searchUrl = baseUrl;
            HtmlPage page = client.getPage(searchUrl);
            List<HtmlElement> elements = (List<HtmlElement>) page.getByXPath(listXPath);
            imagesUrls(baseUrl); //downloads pictures

            if (elements.isEmpty()) {
                System.out.println("Not found");
            } else {

                for (int i = 0; i < elements.size(); i++) {
                    HtmlAnchor itemAnchor = elements.get(i).getFirstByXPath(nameXPath);
                    HtmlElement itemPrice = elements.get(i).getFirstByXPath(priceXPath);

                    String picture = pictureInBase64(i);

                    ItemInfo item = new ItemInfo();
                    item.setTitle(itemAnchor.asText());
                    item.setUrl(searchUrl + itemAnchor.getHrefAttribute() + "\n");
                    item.setPrice(itemPrice.asText());
                    item.setPicture(picture+"\n");

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item);

                    jsonList.add(jsonString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }
}
