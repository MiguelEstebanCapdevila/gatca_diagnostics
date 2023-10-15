package com.api.gatca.services.impl;

import com.api.gatca.models.GenomicData;
import com.api.gatca.services.FetchFileDataService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FetchFileDataServiceImpl implements FetchFileDataService {

    /**
     * Parses the VCF (Variant Call Format) file and returns a list of genomic data when
     * they contain a single letter in both the reference and alternate fields. and is not dot
     *
     * @param file The VCF file to be parsed.
     * @return A list of GenomicData objects representing valid genomic data.
     */
    @Override
    public List<GenomicData> getGenomicDataFromFile(File file) {
        List<GenomicData> genomicDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) { // Exclude lines starting with '#'
                    String[] parts = line.split("\t");
                    if (parts.length >= 5 && parts[3].length() == 1 && parts[4].length() == 1 && !parts[3].equals(parts[4])
                    && !parts[3].equals(".") && !parts[4].equals(".")) {
                        GenomicData genomicData = new GenomicData();
                        /*genomicData.setChromosoma(Integer.parseInt(parts[0]));
                        genomicData.setPosition(Integer.parseInt(parts[1]));
                        genomicData.setReference(parts[3]);
                        genomicData.setAlternance(parts[4]);
                        genomicData.setQuality(Integer.parseInt(parts[5]));

                         */
                        genomicData.chromosoma = Integer.parseInt(parts[0]);
                        genomicData.position = (Integer.parseInt(parts[1]));
                        genomicData.reference = (parts[3]);
                        genomicData.alternance = (parts[4]);
                        genomicData.quality = (Integer.parseInt(parts[5]));
                        genomicDataList.add(genomicData);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return genomicDataList;
    }
}
