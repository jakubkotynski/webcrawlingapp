package pl.dimirhouse;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Scanner;

import static pl.dimirhouse.Converter.stringToXml;

public class ConverterApp {
    public static void main(String[] args)
            throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Scanner scanner = new Scanner(System.in);
        String baseUrl = "https://www.ceneo.pl/;szukaj-filmy+sensacyjne";

        Converter converter = new Converter();
        converter.convertUrl(baseUrl);

        //stringToXml(baseUrl);
    }
}
