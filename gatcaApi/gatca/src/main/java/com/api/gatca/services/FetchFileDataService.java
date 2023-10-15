package com.api.gatca.services;

import com.api.gatca.models.GenomicData;

import java.io.File;
import java.util.List;

public interface FetchFileDataService {

    public List<GenomicData> getGenomicDataFromFile(File file);
}
