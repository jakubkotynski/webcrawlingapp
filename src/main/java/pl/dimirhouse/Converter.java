package pl.dimirhouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class Converter {

    public void convertUrl(String baseUrl){
        WebClient client = new WebClient();

        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = baseUrl;
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> elements = (List<HtmlElement>) page
                    .getByXPath("//div[@class='cat-prod-row js_category-list-item js_clickHashData js_man-track-event ']");
            if (elements.isEmpty()) {
                System.out.println("Not found");
            } else {
                for (HtmlElement htmlItem : elements) {
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//strong[@class='cat-prod-row-name']/a"));
                    HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//span[@class='price']"));

                    String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();

                    ItemInfo item = new ItemInfo();

                    item.setTitle(itemAnchor.asText());
                    item.setUrl(searchUrl + itemAnchor.getHrefAttribute());
                    //item.setPrice(new BigDecimal(itemPrice.replace("$", "")));
                    ObjectMapper mapper = new ObjectMapper();

                    String jsonString = mapper.writeValueAsString(item);
                    System.out.println(jsonString);

//                    try{
//                        PrintStream printStream = new PrintStream(new File("C:\\Users\\user\\Desktop\\java.xml"));
//                        System.setOut(printStream);
//                        printStream.println(jsonString);
//                    } catch (FileNotFoundException e){
//                        System.out.println(e);
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stringToXml(String xmlString)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File("xmlList.xml"));
        transformer.transform(domSource, streamResult);
    }
}
