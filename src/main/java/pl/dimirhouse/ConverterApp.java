package pl.dimirhouse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConverterApp {
    public static void main(String[] args) throws IOException {

        System.out.println("Paste your url below");
        Scanner scanner = new Scanner(System.in);
        String baseUrl = "https://www.ceneo.pl/Komputery";

        WebScrap webScrap = new WebScrap();
        List<String> productList = webScrap.scrap(baseUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        String listAsJson = objectMapper.writeValueAsString(productList);

        XmlConverter converter = new XmlConverter();
        converter.createXmlFile(listAsJson, "product");
    }
}
