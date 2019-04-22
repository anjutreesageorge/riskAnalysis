package com.reprisk.riskAnalysis.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderUtility {
	//get the XML file names present in a folder
	public static List<String> listXMLFilesForFolder(final File folder) {
		List<String> xmlfileList = new ArrayList<String>();
		String fileName;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listXMLFilesForFolder(fileEntry);
			} else {
				fileName = fileEntry.getName();
				if (fileName.endsWith(".xml"))
					xmlfileList.add(fileName);
			}
		}		return xmlfileList;
	}
}
