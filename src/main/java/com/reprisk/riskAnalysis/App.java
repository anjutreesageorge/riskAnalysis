package com.reprisk.riskAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.reprisk.riskAnalysis.model.Company;
import com.reprisk.riskAnalysis.utility.CsvFileReader;
import com.reprisk.riskAnalysis.utility.CsvFileWriter;
import com.reprisk.riskAnalysis.utility.FolderUtility;
import com.reprisk.riskAnalysis.utility.MonitorDirectory;
import com.reprisk.riskAnalysis.utility.XmlReader;

//Main class

public class App {

	public static void main(String[] args) {
		final String csvfolderName = "/Users/anjutreesageorge/Desktop/160502_data";
		final String csvfileName = "160408_company_list.csv";
		final String xmlFolderName = "/Users/anjutreesageorge/Desktop/160502_data/data";
		final String resultFilename = "result.csv";

		List<Company> companyList;

		Map<String, List<Company>> xmlCompanyMap = new ConcurrentHashMap<String, List<Company>>();

		try {
			// get the list of companies
			companyList = CsvFileReader.readCsvFile(csvfolderName + "/" + csvfileName);
			System.out.println("No.Of Companies: " + companyList.size());

			// read the XMl files
			final File folder = new File(xmlFolderName);

			ExecutorService service = Executors.newFixedThreadPool(300);

			// Check for the company names present in each file

			for (String xmlFileName : FolderUtility.listXMLFilesForFolder(folder)) {
				service.execute(() -> processFile(xmlFolderName, xmlFileName, companyList, xmlCompanyMap));
				//processFile(xmlFolderName, xmlFileName, companyList, xmlCompanyMap);

			}
			service.shutdown();
			try {
				service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			}
			System.out.println(xmlCompanyMap.size());
			CsvFileWriter.writeCSV(csvfolderName + "/" + resultFilename, xmlCompanyMap);

			// Once intital run completed check for the new files in the folder using
			// MonitorDirectory class and Thread and perofrm the check for company name

			// MonitorDirectory.monitor(xmlFolderName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void processFile(String xmlFolderName, String xmlFileName, List<Company> companyList,
		Map<String, List<Company>> xmlCompanyMap) {
		
		List<Company> xmlCompanyList = new ArrayList<Company>();

		System.out.println(
				"_____________________________________________________________________________________________");
		System.out.println("Company names present in : " + xmlFileName);
		String text = XmlReader.getxmlContent(xmlFolderName + "/" + xmlFileName);
		for (Company company : companyList) {
			if (text.contains(company.getName())) {
				System.out.println(company.getId()+":"+company.getName());
				xmlCompanyList.add(company);

			} else {
				// Checking for altnative names

				for (String altname : company.getAltNameList()) {
					if (text.contains(altname)) {
						System.out.println(company.getId()+":"+company.getName()+":"+altname);
						xmlCompanyList.add(company);
					}
				}
			}
		}
		xmlCompanyMap.put(xmlFileName, xmlCompanyList);
		
	}
	
	
	
	
	
	
}