package soap.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ZhangPY on 2020/12/16
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain: 爬取网页信息工具
 */
public class HtmlParseUtils {

    public static void main(String[] args) throws IOException {
        /** 1.获取请求  https://search.jd.com/Search?keyword=java **/
        String url = "https://search.jd.com/Search?keyword=java";
        /** 2.解析网页 返回的Document就是一个js网页**/
        Document parse = Jsoup.parse(new URL(url), 30000);
        /** 获取商品区域 **/
        Element j_goodsList = parse.getElementById("J_goodsList");

        /** 获取商品li **/
        Elements li = j_goodsList.getElementsByTag("li");
        /** 便利每个li **/
        for (Element element : li) {

            /** 获取商品图片地址 **/
            String imgUrl = element.getElementsByTag("img").eq(0).attr("data-lazy-img");
            /** 获取商品价格 **/
            String price = element.getElementsByClass("p-price").eq(0).text();
            /** 获取商品标题 **/
            String title = element.getElementsByClass("p-name").eq(0).text();

            System.out.println("===========================");
            System.out.println(imgUrl);
            System.out.println(price);
            System.out.println(title);
        }
    }
}
