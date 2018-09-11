package com.stackroute.datamunger;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.regex.Pattern;

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {
		// Implementation

		queryString = queryString.toLowerCase();

		String[] resultString = queryString.split(" ");

		return resultString;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {
		//Implementation

		queryString.toLowerCase();
		int resultLength;

		String[] resultString = queryString.split(" ");
		resultLength = resultString.length;

		for (int i = 0; i < resultLength; i++){
			if (resultString[i].equals("from")) {
				System.out.println(resultString[i+1]);
				return resultString[i+1];
			}
		}

		return null;
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {

		queryString = queryString.toLowerCase();

		String[] resultString = queryString.split(" where ");
		resultString = resultString[0].split(" group ");
		resultString = resultString[0].split(" order ");

		return resultString[0];
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {

		int resultLength;
		queryString = queryString.toLowerCase();

		String[] resultString = queryString.split(" ");
		resultLength = resultString.length;

		for (int i = 0; i < resultLength; i++)
			if (resultString[i].equals("select")) {
				String[] fields = resultString[i+1].split(",");
				return fields;
			}


		return null;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {

		String conditions = "";
		int queryArrLength;
		boolean flag = false;

		queryString = queryString.toLowerCase();

		String[] querryArr = queryString.split(" ");
		queryArrLength = querryArr.length;

		for (int i = 0; i < queryArrLength; i++) {
			if (flag) {
				if (querryArr[i].equals("group") || querryArr[i].equals("order"))
					return conditions;
				else {

					if (conditions.equals(""))
						conditions = conditions + querryArr[i];
					else
						conditions = conditions + " " + querryArr[i];
				}
			}else if (querryArr[i].equals("where")) flag = true;
		}
		if (conditions.equals("")) return null;

		return conditions;
	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {

		String conditions = "";
		int queryArrLength;
		boolean flag = false;
		boolean multiCondition = false;

		queryString = queryString.toLowerCase();

		String[] querryArr = queryString.split(" ");
		queryArrLength = querryArr.length;

		for (int i = 0; i < queryArrLength; i++) {
			if (flag) {
				if (querryArr[i].equals("group") || querryArr[i].equals("order"))
					break;
				else {

					if (conditions.equals("") || multiCondition) {
						conditions = conditions + querryArr[i];
						multiCondition = false;
					} else {
						if (querryArr[i].equals("and") || querryArr[i].equals("or")) {
							conditions = conditions + ",";
							multiCondition = true;
						} else
							conditions = conditions + " " + querryArr[i];
					}
				}
			}else if (querryArr[i].equals("where")) flag = true;
		}

		String[] conditionsArr;
		if (conditions.equals(""))
			conditionsArr = null;
		else
			conditionsArr = conditions.split(",");


		return conditionsArr;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {

		queryString = queryString.toLowerCase();
		String[] conditions = queryString.split(" where ");
		if (conditions.length == 1) return null;
		conditions = conditions[1].split("group");
		conditions = conditions[0].split("order");
		queryString = conditions[0];
		String[] arrWords = queryString.split(" ");
		int flag = 0;
		for (int i = 0; i < arrWords.length; i++){
			if (flag == 0){
				if (arrWords[i].equals("and"))
					flag = 1;
				else if (arrWords[i].equals("or"))
					flag = 2;
				else;
			}else if ((flag == 1) && arrWords[i].equals("or")){
				flag = 3;
				break;
			}else if ((flag == 2) && arrWords[i].equals("and")){
				flag = 3;
				break;
			}
		}
		String[] str1 = {"and"};
		String[] str2 = {"or"};
		String[] str3 = {"and","or"};


		if (flag == 1) return str1;
		else if (flag == 2) return str2;
		else if (flag == 3) return str3;

		return null;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {

		queryString = queryString.toLowerCase();
		String[] orderBy = queryString.split(" order by ");
		if (orderBy.length == 1) return null;
		orderBy = orderBy[1].split(",");

		return orderBy;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {

		queryString = queryString.toLowerCase();
		String[] groupBy = queryString.split(" group by ");
		if (groupBy.length == 1) return null;
		groupBy = groupBy[1].split(" order by ");
		groupBy = groupBy[0].split(",");

		return groupBy;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {

		queryString = queryString.toLowerCase();

		//split using 'from'
		String[] splitFrom = queryString.split("from");
		//separate aggregate block from 'select'
		String[] aggregate = splitFrom[0].split("select");
		//clean the preceding and trailing spaces
		String aggregates = aggregate[1].trim();

		if (aggregates.equals("*")) return null;

		//making array of aggregate values
		String[] arrAggregate = aggregates.split(",");
		aggregates = "";
		// removing undesired content
		for (String a : arrAggregate){
			if (a.endsWith(")"))
				aggregates = aggregates + a + " ";
		}
		//forming array of strings in order to return;
		arrAggregate = aggregates.split(" ");
		return arrAggregate;
	}

}