package com.api.gatca.services;

import com.api.gatca.models.GenomicData;
import com.api.gatca.models.ReportData;
import com.api.gatca.models.entities.Analysis;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ReportService {

    ByteArrayOutputStream generateReport(List<GenomicData> genomicDataList, ReportData reportData) throws DocumentException, IOException;
    public ByteArrayOutputStream generateReport(Analysis analysis) throws DocumentException, IOException;
}
