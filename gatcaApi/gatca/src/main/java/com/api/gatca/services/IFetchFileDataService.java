package com.api.gatca.services;

import com.api.gatca.models.GenomicData;

import java.io.File;
import java.util.List;

public interface IFetchFileDataService {

    public List<GenomicData> getGenomicDataFromFile(File file);
}
