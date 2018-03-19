package pl.dimirhouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConverterApp {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String baseUrl = "https://www.ceneo.pl/Komputery";

        WebScrap webScrap = new WebScrap();
        List<String> productList = webScrap.scrap(baseUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        String list = objectMapper.writeValueAsString(productList);

        Converter converter = new Converter();
        converter.createXmlFile(list, "product");
    }
}
