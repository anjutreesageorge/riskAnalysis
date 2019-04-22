package com.reprisk.riskAnalysis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVRecord;

public class Company {
	String id;
	String name;
	List<String> altNameList;

	public Company(String id, String name, List<String> altNameList) {
		super();
		this.id = id;
		this.altNameList = altNameList;
		this.name = name;

	}

	public Company() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAltNameList() {
		return altNameList;
	}

	public void setAltNameList(List<String> altNameList) {
		this.altNameList = altNameList;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", altName=" + altNameList.toString() + "]";
	}

	public static Company getCompany(final CSVRecord csvRowInfo) {
		Company company = null;
		String id = null;
		String name;
		String altName = null;
		List<String> altNameList = new ArrayList<String>();
		// split the row data
		String[] companyString = csvRowInfo.get(0).split(";");
		
		id = companyString[0];
		if(csvRowInfo.size()>1&&csvRowInfo.get(1)!=null) {
			companyString[1] = companyString[1]+","+csvRowInfo.get(1).replace(";", "");
		}

		// get the altname within ()
		int index = companyString[1].indexOf("(");
		if (index != -1 && index != 0) {
		int	index2 = companyString[1].indexOf(")");
		if (index2 != -1 && index2 != 0) {
			altName = "  " + companyString[1].substring(index + 1, index2 - 1)+" ";
			name = "  " + companyString[1].substring(0,index - 1 )+" "+ companyString[1].substring(index2 + 1,companyString[1].length()).trim()+" ";
		}
		else {
			
			altName = "  " + companyString[1].substring(index + 1, companyString[1].length())+" ";
			name = " "+companyString[1].substring(0,index - 1 )+" ";
		}
			if (isnameignorecase(altName)) {
				name =" "+companyString[1]+" ";
				altName = null;
			}

		} else {
			name = " "+companyString[1]+" ";
		}
		// remove unwanted description from alternativename
		altName = removedescriptionfromName(altName);
		name = removedescriptionfromName(name);

		if (altName != null)
			altNameList.add(altName);
		
		// check if there is other alternative names
		for (int i = 2; i < companyString.length; i++) {

			
			// get the altname within ()
			int index3 = companyString[i].indexOf("(");
			int	index4 = companyString[i].indexOf(")");
			if (index3 != -1 && index3 != 0&&index4 != -1 && index4 != 0) {
	
				altNameList.add("  " + companyString[i].substring(index3 + 1, index4 - 1)+" ");
				altNameList.add("  " + companyString[i].substring(0,index3 - 1 )+" "+ companyString[i].substring(index4 + 1,companyString[i].length()).trim()+" ");
			}
				
			else if(index3 != -1 && index3 != 0&&(index4 == -1 || index4 == 0)){
				
				altNameList.add( "  " + companyString[i].substring(index3 + 1, companyString[i].length())+" ");
				altNameList.add(" "+companyString[i].substring(0,index3 - 1 )+" ");
			}
			else  if((index3 == -1 ||index3 == 0)&&(index4 != -1 && index4 != 0)){
				altNameList.add( "  " + companyString[i].substring(0, index4-1)+" ");
				altNameList.add(" "+companyString[i].substring(index4-1,  companyString[i].length())+" ");
			}
			
				
		}

		// add name along with The
		addNamewithThe(name, altNameList);

		addAbreviationsFullform(name, altNameList);
		company = new Company(id, name, altNameList);
		System.out.println(company.toString());

		return company;
	}

	private static void addNamewithThe(String name, List<String> altNameList) {
		int size = altNameList.size();
		for (int i = 0; i < size; i++) {
			if (altNameList.get(i) != null) {
				if (altNameList.get(i).trim().equals("The") || altNameList.get(i).trim().equals("the")
						|| altNameList.get(i).trim().equals("th") || altNameList.get(i).trim().equals("Th"))
					altNameList.set(i, "The " + name);
			}
			if (altNameList.get(i).trim().equals("Old") || altNameList.get(i).trim().equals("old")) {
				altNameList.set(i, "Old " + name);
				altNameList.set(i, name + "Old ");
				altNameList.set(i, name + "(Old)");

			}
			if (altNameList.get(i).trim().equals("New") || altNameList.get(i).trim().equals("new")) {
				altNameList.set(i, "New  " + name);
				altNameList.set(i, name + "(New)");

			}
			if (altNameList.get(i).trim().equals("Group") || altNameList.get(i).trim().equals("group"))
				altNameList.set(i, "Group  " + name);
			altNameList.set(i, "Group " + name);
			altNameList.set(i, name + "(Group)");

		}

	}

	// to add the full form of abbreviations like co,Inc,Ltd

	private static void addAbreviationsFullform(final String name, final List<String> altNameList) {
		List<String> tempList = new ArrayList<String>();
		if (name != null) {
			if (name.contains("Inc ")) {
				tempList.add(name.replace("Inc ", "Incorporation "));
			}
			if (name.contains("Corp ") && !name.contains("Corporation ")) {
				tempList.add(name.replace("Corp ", "Corporation"));
			} else if (name.contains("Co ") && !name.contains("Corporation ")) {
				tempList.add(name.replace("Co", "Corporation "));
			}
			if (name.contains("Ltd ")) {
				tempList.add(name.replace("Ltd ", "Limited "));
			}

		}
		for (String altName : altNameList) {
			if (altName != null) {
				if (altName.contains("Inc ") && !name.contains("Incorporation ")) {
					tempList.add(altName.replace("Inc ", "Incorporation "));
				}
				if (altName.contains("Corp ") && !name.contains("Corporation ")) {
					tempList.add(altName.replace("Corp ", "Corporation "));
				} else if (altName.contains("Co ") && !name.contains("Corporation ")) {
					tempList.add(altName.replace("Co ", "Corporation "));
				}
				if (altName.contains("Ltd ")) {
					tempList.add(altName.replace("Ltd ", "Limited "));
				}
				if (altName.contains("Ltd ") && name.contains("Co ") && !name.contains("Corporation ")) {
					tempList.add(altName.replace("Ltd ", "Limited ").replace("Co ", "Corporation "));
				}
			}
		}
		altNameList.addAll(tempList);

	}

	private static String removedescriptionfromName(String name) {

		String result = null;
		if (name != null) {
			// "formerly known as"
			result = name.replace("formerly known as", "");
			// remove "please refer to "
			result = result.replace("please refer to ", "");
			// remove "please refer to "
			result = result.replace("Formerly", "");
			result = result.replace("formerly", "");
			// remove "refer to "
			result = result.replace("refer to", "");
			// remove "
			result = result.replaceAll("^\"|\"$", "");
			result = result.replace("also known as", "");
			
		}
		return result;

	}

	private static List<String> getListOfCountries() {
		List<String> countryList = new ArrayList<String>();

		// Get ISO countries, create Country object and
		// store in the collection.
		String[] isoCountries = Locale.getISOCountries();
		for (String country : isoCountries) {
			Locale locale = new Locale("en", country);
			String iso = locale.getISO3Country();
			String code = locale.getCountry();
			String name = locale.getDisplayCountry();

			if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
				countryList.add(name);
			}
		}
		return countryList;
	}

	public static boolean isnameignorecase(String name) {
		List<String> countryList = getListOfCountries();

		for (String countryName : countryList) {
			if (name.equals(countryName)) {
				return true;

			}
		}
		if (name.equals("M") || name.equals("Rus") || name.equals("UK")
				|| name.equals("US") || name.equals("Europe")
				|| name.equals("New York")||name.equals("London")||name.equals("Texas")||name.equals("HK")||name.equals("Shanghai")) {
			return true;
		}
		if(name.equals("No. 4")||name.equals("No. 1")||name.equals("No. 2")||name.equals("No. 3")||name.equals("Proprietary"))
			return true;
		return false;
	}
}