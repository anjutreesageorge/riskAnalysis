package com.reprisk.riskAnalysis.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.reprisk.riskAnalysis.model.Company;

/**
 * @author anjutreesageogre
 *
 */
public class CsvFileReader {

	private static final String[] FILE_HEADER_MAPPING = { "RepRisk Company ID", "Company Name" };
	private static final String COMPANY_ID = "RepRisk Company ID";
	private static final String COMPANY_NAME = "Company Name";

	public static List<Company> readCsvFile(final String fileName) {

		CSVParser csvFileParser = null;

		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		try {

			// Create a new List of Company to be filled by CSV file data
			List<Company> companyList = new ArrayList<Company>();
			csvFileParser = CSVParser.parse(new File(fileName), Charset.forName("utf-8"), csvFileFormat);

			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			System.out.println(csvRecords.size());

			// Read the CSV file records starting from the second record to skip the header
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = csvRecords.get(i);
				Company company = Company.getCompany(record);
				companyList.add(company);

			}
			return companyList;
		}

		catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			
		}
		return null;

	}

}