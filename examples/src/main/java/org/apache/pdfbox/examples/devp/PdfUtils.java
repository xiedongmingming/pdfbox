package org.apache.pdfbox.examples.devp;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PdfUtils {

    // 参考文档：
    // 1. https://blog.csdn.net/suya2011/article/details/121368330

    public static List<File> pdf2png(String fileAddress, String filename, String type) {

        return pdf2png(fileAddress, filename, 0, -1, type);

    }

    /**
     * 自由确定起始页和终止页
     *
     * @param fileAddress  文件地址
     * @param filename     PDF文件名
     * @param indexOfStart 开始页（开始转换的页码，从0开始）
     * @param indexOfEnd   结束页（停止转换的页码，-1为全部）
     * @param type         图片类型
     */
    public static List<File> pdf2png(String fileAddress, String filename, int indexOfStart, int indexOfEnd, String type) {

        try {

            File file = new File(fileAddress + "/" + filename + ".pdf");

            PDDocument doc = Loader.loadPDF(file); // PDDocument doc = PDDocument.load(file);

            PDFRenderer renderer = new PDFRenderer(doc);

            int pageCount = doc.getNumberOfPages();

            if (indexOfEnd == -1) {
                indexOfEnd = pageCount;
            }

            List<File> list = new ArrayList<>();

            File dir = new File(fileAddress + "/" + filename);

            if (!dir.exists()) {
                dir.mkdir();
            }

            for (int i = indexOfStart; i < indexOfEnd; i++) {

                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI

                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图

                File png = new File(fileAddress + "/" + filename + "/" + (i + 1) + "." + type);

                if (!file.exists()) {
                    file.createNewFile();
                }

                ImageIO.write(image, type, png);

                list.add(png);

            }

            return list;

        } catch (IOException e) {

            e.printStackTrace();

        }

        return new ArrayList<>();

    }

    public static String pdf2txt(String fileAddress, String filename, int indexOfStart, int indexOfEnd, String type) {

        try {

            File file = new File(fileAddress + "\\" + filename + ".pdf");

            PDDocument doc = Loader.loadPDF(file); // PDDocument doc = PDDocument.load(file);

            int pageCount = doc.getNumberOfPages();

            if (indexOfEnd == -1) {
                indexOfEnd = pageCount;
            }

            PDFTextStripper textStripper = new PDFTextStripper();

            return textStripper.getText(doc);


        } catch (IOException e) {

            e.printStackTrace();

        }

        return "";

    }

    /**
     * 获取PDF文件的总页数
     *
     * @param path
     * @return
     */
    public static int getPdfTotalPage(String path) {

        if (path == null || path.isEmpty()) {
            return 0;
        }

        int pages = 0;

        try {// 读取PDF的页数

            pages = Loader.loadPDF(new File(path)).getNumberOfPages();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pages;

    }

    public static void main(String[] args) {

        //        String s = PdfUtils.pdf2txt("D:\\software\\feishu\\downloads", "2024年1月经销店重点实施事项手册（新车销售篇） 5分30秒.pdf", 0, -1, "");
        //
        //
        //        System.out.println(s);

        List<File> files = PdfUtils.pdf2png("C:\\Users\\98661\\Downloads", "ca3c28b1f7a3a4125a8d4d1f4f099e84", "png");

    }

    public static void main2(String[] args) {

        String path = "./temp/1712915617376_6-三级底盘-BEVeAxle系统和EV冷却系统（21p）.pdf";

        int page = getPdfTotalPage(path);

        System.out.println(page);

        //        String data = "{\"words_result\":[{\"words\":\"TOYOTA\"},{\"words\":\"回一汽丰田\"},{\"words\":\"商秘\"},{\"words\":\"打标签测试\"},{\"words\":\"24年1月经销店重点实施事项手册\"},{\"words\":\"1-3月营销节奏和规划\"},{\"words\":\"传播私域公域市场活动店头活动\"},{\"words\":\"服务入场促进\"},{\"words\":\"亚洲龙\"},{\"words\":\"24年试驾车管理与支援\"},{\"words\":\"全新普拉多改良版亚洲龙试驾车考核\"},{\"words\":\"店头活动及期间布置\"},{\"words\":\"sp展具大屏\"},{\"words\":\"5分30秒\"},{\"words\":\"2024年1月月度\"},{\"words\":\"经销店重点实施事项手册\"},{\"words\":\"<新车销售篇>\"},{\"words\":\"亚洲龙\"},{\"words\":\"皇冠陆放\"},{\"words\":\"格瑞维亚\"},{\"words\":\"亚洲龙\"},{\"words\":\"AVALON\"},{\"words\":\"IT'\"},{\"words\":\"●智能电混双擎\"},{\"words\":\"ANNIVERSARY\"},{\"words\":\"此刻智未\"},{\"words\":\".TiME\"},{\"words\":\"oo Coe\"},{\"words\":\"一消丰田20周年\"}],\"log_id\":1773624820229034021,\"words_result_num\":29}";
        //
        //        OcrResult ocrResult = JsonUtil.parseObject(data, OcrResult.class);
        //
        //        System.out.println(ocrResult);

    }

}
