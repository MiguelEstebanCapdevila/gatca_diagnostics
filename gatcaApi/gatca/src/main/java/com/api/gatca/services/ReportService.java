package com.api.gatca.services;

import com.api.gatca.models.GenomicData;
import com.api.gatca.models.ReportData;
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
public class ReportService implements  IReportService {
    private final TemplateEngine templateEngine;

    private FetchFileDataService fetchFileDataService;
    public ReportService(IFetchFileDataService fileDataService, TemplateEngine templateEngine){
        fetchFileDataService = (FetchFileDataService) fileDataService;
        this.templateEngine = templateEngine;
    }

   /* public byte[] generateReport(List<GenomicData> genomicDataList, ReportData reportData) {
        try {


            // Load the JasperReport template (JRXML or compiled Jasper file)
            //TODO change by relative path
            //InputStream reportTemplateStream = getClass().getResourceAsStream("C:\\Users\\Nitropc\\Downloads\\gatca\\gatca\\src\\main\\resources\\templates\\Blank_A4.jrxml");
            InputStream reportTemplateStream = new FileInputStream(new File("C:\\Users\\Nitropc\\Downloads\\gatca\\gatca\\src\\main\\resources\\templates\\Blank_A4.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(reportTemplateStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportTemplateStream);

            // Create parameters if needed
            Map<String, Object> parameters = new HashMap<>();
            // parameters.put("parameterName", parameterValue);

            //add data to report
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(genomicDataList);
            parameters.put("GenomicCollectionParam",dataSource);

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());


            // Export the report to a byte array (PDF in this case)

            return exportReportToByteArray(jasperPrint, "pdf");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    */



    public ByteArrayOutputStream generateReport(List<GenomicData> genomicDataList, ReportData reportData) throws DocumentException, IOException {

        try {
            // Create a Thymeleaf context and add the genomicData to it
            Context context = new Context();

//            String image = "data:image/png;base64, " + convertImageToBase64("src/main/resources/static/images/logo-gatca-sin-fondo.png");
         //   context.setVariable("image",  image);
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
        Locale spanishLocale = new Locale("es", "ES");

        // Create a SimpleDateFormat instance with the Spanish locale and desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", spanishLocale);

        // Get the current date
        Date currentDate = new Date();

        // Format the current date as a string in Spanish with only year, month, and day
        String formattedDate = sdf.format(currentDate);

        return formattedDate;
    }

}
