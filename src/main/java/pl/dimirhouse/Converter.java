package pl.dimirhouse;

import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Converter {

    public void createXmlFile(String jsonString, String product) throws IOException {
        String xml = convertXml(jsonString, product);
        String outputPath = "./productList.xml";
        writeFile(outputPath, xml);

        System.out.println("Process is finnished!");
    }

    private String convertXml(String jsonString, String product) throws JSONException {
        {
            org.json.JSONObject jsonFileObject = new org.json.JSONObject(jsonString);

            return "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<" + product + ">"
                    + org.json.XML.toString(jsonFileObject) + "</" + product + ">";
        }
    }

    private static void writeFile(String filepath, String output) throws IOException {
        FileWriter fileWriter = new FileWriter(filepath);
        try (BufferedWriter out = new BufferedWriter(fileWriter)) {
            out.write(output);
        }
    }
}
