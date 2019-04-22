package com.reprisk.riskAnalysis.utility;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.reprisk.riskAnalysis.model.Company;

public class CsvFileWriter {
	public static void writeCSV(final String fileName, final Map<String, List<Company>> newsCompanyMap) {
		String companyIds = "";
		try {
			// We have to create the CSVPrinter class object
			Writer writer = Files.newBufferedWriter(Paths.get(fileName));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("XmlFileName", "CompanyIds"));
			for (String xmlfileName : newsCompanyMap.keySet()) {
				companyIds = "";
				csvPrinter.print(xmlfileName);
				for (Company company : newsCompanyMap.get(xmlfileName)) {
					companyIds = companyIds + "|" + company.getId();
				}
				csvPrinter.print(companyIds);
				csvPrinter.println();
			}
			csvPrinter.flush();

			csvPrinter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
