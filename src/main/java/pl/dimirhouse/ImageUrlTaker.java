package pl.dimirhouse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageUrlTaker {


    public static void imagesUrls(String url) throws IOException {
        List<String> imgUrls = new ArrayList<>();
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        Elements image2 = doc.select("a.js_clickHash > img[src]");
        Elements image = doc.select("img.js_lazy");

        for (int i = 0; i <= 3; i++) {
            String imgUrl = image2.get(i).attr("src");
            String httpUrl = "http:" + imgUrl;
            imgUrls.add(httpUrl);
            System.out.println("http:" + imgUrl);
        }

        for (Element e : image) {
            String imgUrl = e.attr("data-original");
            String httpUrl = "http:" + imgUrl;
            imgUrls.add(httpUrl);
            System.out.println("http:" + imgUrl);
        }

        for (int i = 0; i < imgUrls.size(); i++) {
            URL pictureUrl = new URL(imgUrls.get(i));
            BufferedImage bufferedImage = ImageIO.read(pictureUrl);
            File file = new File("picture" + i + ".jpg");
            ImageIO.write(bufferedImage, "jpg", file);
        }
    }
}
