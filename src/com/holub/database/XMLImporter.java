/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 *	Pass this importer to a {@link Table} constructor (such
 *	as
 *	{link com.holub.database.ConcreteTable#ConcreteTable(Table.Importer)}
 *	to initialize
 *	a <code>Table</code> from
 *	a comma-sparated-value repressentation. For example:
 *	<PRE>
 *	Reader in = new FileReader( "people.csv" );
 *	people = new ConcreteTable( new CSVImporter(in) );
 *	in.close();
 *	</PRE>
 *	The input file for a table called "name" with
 *	columns "first," "last," and "addrId" would look
 *	like this:
 *	<PRE>
 *	name
 *	first,	last,	addrId
 *	Fred,	Flintstone,	1
 *	Wilma,	Flintstone,	1
 *	Allen,	Holub,	0
 *	</PRE>
 *	The first line is the table name, the second line
 *	identifies the columns, and the subsequent lines define
 *	the rows.
 *
 * @include /etc/license.txt
 *
 * @see Table
 * @see Table.Importer
 * @see CSVExporter
 */

public class XMLImporter implements Table.Importer
{	private BufferedReader  in;			// null once end-of-file reached
	private String[]        columnNames;
	private String          tableName;
	private final String XMLTagRegex = "<([^>]+)>(.*?)</\\1>";

	public XMLImporter(Reader in )
	{	this.in = in instanceof BufferedReader
						? (BufferedReader)in
                        : new BufferedReader(in)
	                    ;
	}
	public void startTable()			throws IOException
	{
		String firstLine = in.readLine().trim();
		tableName   = extractCaption(firstLine);
		String parsedColumnNames = parseColumnNames();
		columnNames = parsedColumnNames.split("\\s*,\\s*");
	}
	public String loadTableName()		throws IOException
	{	return tableName;
	}
	public int loadWidth()			    throws IOException
	{	return columnNames.length;
	}
	public Iterator loadColumnNames()	throws IOException
	{	return new ArrayIterator(columnNames);  //{=CSVImporter.ArrayIteratorCall}
	}

	public Iterator loadRow()			throws IOException
	{	Iterator row = null;
		if( in != null )
		{	String line = in.readLine().trim();
			if (extractCaption(line).equals("/"+tableName)) return row;

			if( !extractCaption(line).equals("item"))
				in = null;
			else
				line = parseXMLRowFormat();
				row = new ArrayIterator( line.split("\\s*,\\s*"));
		}
		return row;
	}

	public void endTable() throws IOException {}

	private String parseColumnNames() throws IOException {
		String input = in.readLine().trim();
		String metaRow = input.substring(6,input.length()-7);
		StringTokenizer st = new StringTokenizer(metaRow,"/");
		StringBuilder parsingFormat = new StringBuilder();
		while (st.hasMoreTokens()){
			parsingFormat.append(st.nextToken()+",\t");
		}
		return parsingFormat.toString();
	}

	private String extractCaption(String raw){
		return raw.substring(1,raw.length()-1);
	}

	private String parseXMLRowFormat() throws IOException {
		StringBuilder parsingFormat = new StringBuilder();
		String nextLine = in.readLine().trim();
		Pattern pattern = Pattern.compile(XMLTagRegex);
		Matcher matcher;
		while(!nextLine.startsWith("</")){
			matcher = pattern.matcher(nextLine);
			if(matcher.find()){
				parsingFormat.append(matcher.group(2)+",\t");
			}
			nextLine=in.readLine().trim();
		}
		return parsingFormat.toString();
	}

}

