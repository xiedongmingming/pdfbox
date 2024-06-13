package org.apache.pdfbox.examples.devp;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.tools.PDFText2HTML;
import org.apache.pdfbox.tools.PDFText2Markdown;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PdfBoxTest {

    // 参考文档：https://blog.csdn.net/qq_39225639/article/details/119344997

    public static void main(String[] args) {

//        demo1();
//        demo2();
//        demo3();
//        demo4();
        demo5();

//        File filePath = new File("C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf");
//        File filePath = new File("C:\\Users\\98661\\Desktop\\理想汽车小红书kos专项服务方案-5.29终版.pdf");
//
//        getbase64Photos(filePath, 1, 3);

    }

    public static void demo1() { // 读取所有页所有文本

        try {

            File filePath = new File("C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf");

            PDDocument document = Loader.loadPDF(filePath); // 加载PDF文件

            PDFTextStripper stripper = new PDFTextStripper(); // 文本剥离器

            stripper.setSortByPosition(true); // 按位置进行排序

            String text = stripper.getText(document); //获取文本

            document.close(); // 关闭文档对象

            System.out.println(text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void demo2() { // 按页读取文本

        try {

            File filePath = new File("C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf");

            PDDocument document = Loader.loadPDF(filePath); // 加载PDF文件

            PDFTextStripper stripper = new PDFTextStripper(); // 文本剥离器

            stripper.setStartPage(1); // 按页进行读取，页码从1开始
            stripper.setEndPage(1);

            stripper.setSortByPosition(true); // 按位置进行排序

            String text = stripper.getText(document); // 获取文本

            document.close(); // 关闭文档对象

            System.out.println(text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void demo3() {  // 按坐标读取

        try {

            File filePath = new File("C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf");

            PDDocument document = Loader.loadPDF(filePath); // 加载PDF文件

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();  // 按区域读取文本剥离器

            Rectangle rectangle = new Rectangle(39, 39, 95, 38); // 新建区域，坐标：X,Y；宽高：WIDTH，HEIGHT

            stripper.addRegion("regionName", rectangle); // 设置区域

            stripper.setSortByPosition(true); // 按位置进行排序

            PDDocumentCatalog catalog = document.getDocumentCatalog(); // 获取目录

            PDPageTree tree = catalog.getPages(); // 获取页码树

            PDPage page = tree.get(0); // 获取指定页，从0开始

            stripper.extractRegions(page); // 提取页面信息

            String regionText = stripper.getTextForRegion("regionName"); // 获取指定区域名称对应区域的文本

            System.out.println(regionText);

            document.close(); // 关闭文档对象

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void demo4() {  // 按坐标读取

        try {

            File filePath = new File("C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf");

            PDDocument document = Loader.loadPDF(filePath); // 加载PDF文件

            PDFText2HTML stripper = new PDFText2HTML(); // 文本剥离器

            stripper.setSortByPosition(true); // 按位置进行排序

            String text = stripper.getText(document); //获取文本

            document.close(); // 关闭文档对象

            System.out.println(text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void demo5() {  // 按坐标读取

        try {

            File filePath = new File("C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf");

            PDDocument document = Loader.loadPDF(filePath); // 加载PDF文件

            PDFText2Markdown stripper = new PDFText2Markdown(); // 文本剥离器

//            stripper.setSortByPosition(true); // 按位置进行排序

            String text = stripper.getText(document); //获取文本

            document.close(); // 关闭文档对象

            System.out.println(text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //pdf模板处理
//    private static void fillTemplete() {
//
//        String templetePath = "C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf";
//
//        String data = "";
//
//        try {
//
//            PDDocument document = PDDocument.load(new File(templetePath));
//
//            if (document.isEncrypted()) {
//                try {
////                    document.("");
//                } catch (Exception e) {
//
//                }
//            }
//
//            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//
//            stripper.setSortByPosition(true);
//            stripper.setWordSeparator("|");
////	      stripper.setLineSeparator("#");
//            //划定区域
//            Rectangle rect = new Rectangle(0, 0, 10000, 10000);
//
//            stripper.addRegion("area", rect);
//
//            List<PDPage> allPages = document.getDocumentCatalog().getAllPages();
//
//            int i = 0;
//
//            for (PDPage page : allPages) {
//
//                stripper.extractRegions(page);
//
//                i++;
//
//                //获取区域的text
//
//                data = stripper.getTextForRegion("area");
//                //	         data = data.trim();
//                String[] datas = data.split("\r\n");
//                //对文本进行分行处理
//                for (i = 0; i < datas.length; ++i) {
//                    String[] str = datas[i].split(" ");
//                    System.out.println(JsonUtils.objToString(str));
//                }
//            }
//            document.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 提取指定PDF页面的图片转换为BASE64的LIST
     * 注意：图片提取的顺序是PDF创建时图片插入的顺序
     * @param file PDF文件
     * @param startIndex
     * @param endIndex
     * @throws Exception
     */
    public static List<String> getbase64Photos(File file, int startIndex, int endIndex) {

        List<String> photos = new ArrayList<>();

        try (PDDocument document = Loader.loadPDF(file)) {

            for (int i = startIndex - 1; i < endIndex; i++) { // TODO 下标从0开始，所以-1

                PDPage pdfpage = document.getPage(i);

                PDResources pdResources = pdfpage.getResources();

                Iterable<COSName> xObjectNames = pdResources.getXObjectNames();

                Iterator<COSName> iterator = xObjectNames.iterator();

                while (iterator.hasNext()) {

                    COSName cosName = iterator.next();

                    PDXObject o = pdResources.getXObject(cosName);

                    System.out.println(cosName);

                    if (o instanceof PDImageXObject) {

                        BufferedImage image = ((PDImageXObject) o).getImage(); // 得到BUFFEREDIMAGE对象

                        String base64img = Base64Util.convertimgtoBase64(image);

                        // 可以打印到本地，查看输出顺序
                        String imglocation = "C:\\Users\\98661\\Desktop\\";

                        File imgfile = new File(imglocation + UUID.randomUUID() + ".png");

                        ImageIO.write(image, "png", imgfile);

                        photos.add("data:image/jpg;base64," + base64img);

                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return photos;

    }

}
