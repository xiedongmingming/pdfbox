package org.apache.pdfbox.examples.devp;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.text.PDFTextStripper.WordWithTextPositions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPDFTextStripper extends PDFTextStripper {

    public CustomerPDFTextStripper() throws IOException {
    }

    @Override
    protected void writePageStart() throws IOException {

    }

    @Override
    protected void writePageEnd() throws IOException {

    }
    @Override
    public void beginText() throws IOException {
        System.out.println("文本开始>>>>>>>>>>>");
    }
    @Override
    public void endText() throws IOException
    {
        System.out.println("文本结束<<<<<<<<<<");
    }
    @Override
    protected void unsupportedOperator(Operator operator, List<COSBase> operands) throws IOException {
        System.out.println("未识别的标识：" + operator.getName());
    }

//    @Override
//    protected void writeAPageEnd() throws IOException {
//
//    }

    @Override
    protected void writeLine(List<WordWithTextPositions> line) throws IOException {

        int numberOfStrings = line.size();

        StringBuilder text = new StringBuilder();

        List<TextPosition> textPositions = new ArrayList<>();

        for (int i = 0; i < numberOfStrings; i++) {

            WordWithTextPositions word = line.get(i);

            text.append(word.getText());

            if (i < numberOfStrings - 1) {
                text.append(getWordSeparator());
            }

            textPositions.addAll(word.getTextPositions());

        }

        writeString(text.toString(), textPositions);

    }

    /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {

        if (StringUtils.isEmpty(string) || StringUtils.isAllBlank(string)) {
            return;
        }

        float xMin = Float.MAX_VALUE;
        float xMax = Float.MIN_VALUE;
        float yMin = Float.MAX_VALUE;
        float yMax = Float.MIN_VALUE;

        float maxHeight = 0f;

        float width = 0f;

        for (TextPosition text : textPositions) {

            if (text.getX() < xMin) {
                xMin = text.getX();
            }
            if (text.getX() > xMax) {
                xMax = text.getX();
            }

            if (text.getY() < yMin) {
                yMin = text.getY();
            }
            if (text.getY() > yMax) {
                yMax = text.getY();
            }

            width += text.getWidth();

            if (text.getHeight() > maxHeight) {
                maxHeight = text.getTextMatrix().getScaleX();
            }

        }

        System.out.println(string + "[(X=" + xMin + ",Y=" + yMin + ") height=" + maxHeight + " width=" + width + "]");

    }


    /**
     * @throws IOException If there is an error parsing the document.
     */
    public static void main(String[] args) throws IOException {

        PDDocument document = null;

        String fileName = "C:\\Users\\98661\\Desktop\\20170501-天风证券-北京银行-601169.SH-年报点评：定增支撑业务发展，负债结构仍待优化.pdf";
        // String fileName = "D:\\workspace\\pdf\\articls.pdf";

        try {

            document = Loader.loadPDF(new File(fileName));

            PDFTextStripper stripper = new CustomerPDFTextStripper();
            stripper.setAddMoreFormatting(true);
//            stripper.setStartBookmark();
//            stripper.setEndBookmark();
//            stripper.setSortByPosition(true);
            stripper.setStartPage(2);
            stripper.setEndPage(2); // document.getNumberOfPages()

            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());

            stripper.writeText(document, dummy);

        } finally {

            if (document != null) {
                document.close();
            }

        }

    }

}