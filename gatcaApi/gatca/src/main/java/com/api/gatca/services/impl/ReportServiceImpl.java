package com.api.gatca.services.impl;

import com.api.gatca.models.GenomicData;
import com.api.gatca.models.ReportData;
import com.api.gatca.models.entities.Analysis;
import com.api.gatca.services.FetchFileDataService;
import com.api.gatca.services.ReportService;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    private final TemplateEngine templateEngine;

    public ReportServiceImpl(FetchFileDataService fileDataService, TemplateEngine templateEngine){
        FetchFileDataServiceImpl fetchFileDataServiceImpl = (FetchFileDataServiceImpl) fileDataService;
        this.templateEngine = templateEngine;
    }

    //TODO fix the fields its prepared for genomic data instead of analysis, it has to change, wait to get the first report
    public ByteArrayOutputStream generateReport(Analysis analysis) throws DocumentException, IOException {

        try {
            // Create a Thymeleaf context and add the genomicData to it
            Context context = new Context();

            //context.setVariable("genomicData", genomicDataList);
            context.setVariable("title","qcarrier test");
            context.setVariable("currentDate", ActualSpanishDateFormatted());
            context.setVariable("logo",convertImageToBase64("logo.png"));
            context.setVariable("genomicData",analysis);
            // Process the Thymeleaf template with the context to generate the HTML
            String htmlContent = templateEngine.process("report", context);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            renderer.createPDF(baos);
            return baos;

        }catch (Exception ex){
            throw  ex;
        }
    }



//TODO BORRAR
    public ByteArrayOutputStream generateReport(List<GenomicData> genomicDataList, ReportData reportData) throws DocumentException, IOException {

        try {
            // Create a Thymeleaf context and add the genomicData to it
            Context context = new Context();

            //context.setVariable("genomicData", genomicDataList);
            context.setVariable("title","qcarrier test");
            context.setVariable("currentDate", ActualSpanishDateFormatted());
            context.setVariable("logo",convertImageToBase64("logo.png"));
            context.setVariable("genomicData",genomicDataList);
            // Process the Thymeleaf template with the context to generate the HTML
            String htmlContent = templateEngine.process("report", context);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            renderer.createPDF(baos);
            return baos;

        }catch (Exception ex){
            throw  ex;
        }
    }


    public static String convertImageToBase64(String imagePath) throws IOException {
        // Read the image file from the specified path
        ClassPathResource resource = new ClassPathResource("static/" + imagePath);
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] imageBytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }

    public static String ActualSpanishDateFormatted(){
        // Specify the desired locale (Spanish)
        Locale spanishLocale = new Locale.Builder().build();

        // Create a SimpleDateFormat instance with the Spanish locale and desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", spanishLocale);

        // Get the current date
        Date currentDate = new Date();

        // Format the current date as a string in Spanish with only year, month, and day

        return sdf.format(currentDate);
    }

}
