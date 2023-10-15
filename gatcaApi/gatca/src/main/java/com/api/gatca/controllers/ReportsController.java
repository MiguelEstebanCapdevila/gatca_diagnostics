package com.api.gatca.controllers;

import com.api.gatca.models.ReportData;
import com.api.gatca.services.impl.FetchFileDataServiceImpl;
import com.api.gatca.services.FetchFileDataService;
import com.api.gatca.services.ReportService;
import com.api.gatca.services.impl.ReportServiceImpl;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportServiceImpl reportServiceImpl;
    private final FetchFileDataServiceImpl fetchFileDataServiceImpl;
    @Autowired
    public ReportsController(ReportService reportService, FetchFileDataService fetchFileDataService){
        this.reportServiceImpl = (ReportServiceImpl) reportService;
        this.fetchFileDataServiceImpl = (FetchFileDataServiceImpl) fetchFileDataService;
    }

    //TODO
    /*
    recuperar data llamando al fetchFileDataService
    invocar a Report Data y generar el archivo de reporte
    mirar como usar jasper reports para generar el archivo
     */
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateReport(@RequestParam("file") MultipartFile file){
        // Check if the uploaded file is not empty
        if (!file.isEmpty()) {
            try {
                // Get the original file name
                String originalFilename = file.getOriginalFilename();

                // Specify the directory where you want to save the uploaded file
                //TODO change to relative path/real directory
                //        String userHome = System.getProperty("user.home");
                String uploadDirectory = "C:\\GATCA\\reports";

                // Create the directory if it doesn't exist
                File directory = new File(uploadDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the uploaded file to the specified directory
                File uploadedFile = new File(uploadDirectory, originalFilename != null ? originalFilename : "REPORT");
                file.transferTo(uploadedFile);

                //prepare data
                var genomicData = fetchFileDataServiceImpl.getGenomicDataFromFile(uploadedFile);
                //TODO no hardcodearlo
                var report = reportServiceImpl.generateReport(genomicData,new ReportData("SNV","C:\\GATCA\\logo-report.png",
                        "soy el parágrafo legal \n bla bla bla \n bla bla bla"));



                // Set response headers for PDF
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "genomic-report.pdf");

                return new ResponseEntity<>(report.toByteArray(), headers, HttpStatus.OK);


            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
