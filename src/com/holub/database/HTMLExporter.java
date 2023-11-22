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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 *	Pass this exporter to a {@link Table#export} implementation to
 *	create a comma-sparated-value version of a {@link Table}.
 *	For example:
 *	<PRE>
 *	Table people  = TableFactory.create( ... );
 *	//...
 *	Writer out = new FileWriter( "people.csv" );
 *	people.export( new CSVExporter(out) );
 *	out.close();
 *	</PRE>
 *	The output file for a table called "name" with
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
 * @see Table
 * @see Table.Exporter
 * @see CSVImporter
 */

public class HTMLExporter implements Table.Exporter
{	private final Writer out;
	private 	  int	 width;
	private String tableName;
	private List<String> columns = new ArrayList<>();
	private int indentLevel = 1;

	public HTMLExporter(Writer out )
	{	this.out = out;
	}

	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException

	{
		this.width = width;
		this.tableName = tableName == null ? "<anonymous>" : tableName;
		out.write(writeHTMLStandardFormat("title",null,null,tableName,this.indentLevel,false));
		out.write("   </head>\n");
		indentLevel++;
		out.write("   <body>\n");
		saveColumns(columnNames);
	}

	public void storeRow( Iterator data ) throws IOException
	{
		out.write("	<div>\n");
		indentLevel++;
		int i = 0;
		while( data.hasNext() )
		{
			String targetColumn = columns.get(i);
			//out.write("<"+targetColumn+">");
			Object datum = data.next();

			out.write(writeHTMLStandardFormat("p","name",targetColumn,datum.toString(),indentLevel,false));
			i++;
			//out.write("</"+targetColumn+">");
		}
		out.write("	</div>\n");
		indentLevel--;
	}

	public void startTable() throws IOException {
		out.write("<!DOCTYPE html>\n");
		out.write(" <html lang=\"ko\">\n");
		out.write("   <head>\n");
		out.write("     <meta charset=\"utf-8\">\n");
		indentLevel++;
		indentLevel++;
	}
	public void endTable()   throws IOException {
		out.write("  </body>\n");
		out.write(" </html>\n");
	}

	private String writeHTMLStandardFormat(String tagName,String attrName, String attrValue, String content,int indent,boolean isEnter){
		//<caption>content</caption>
		StringBuilder prefix = new StringBuilder();
		StringBuilder postfix = new StringBuilder();
		for(int i = 0;i<indent;i++){
			prefix.append("  ");
			if(isEnter){
				postfix.append("  ");
			}
		}
		prefix.append("<").append(tagName);
		if(attrName!=null){
			prefix.append(" ").append(attrName).append("=").append("\"").append(attrValue).append("\"");
		}
		prefix.append(">");
		if(isEnter) {
			prefix.append("\n");
			postfix.append("\n");
		}
		postfix.append("</").append(tagName).append(">");
		return prefix + content + postfix+"\n";
	}

	private void saveColumns(Iterator data) throws IOException {
		StringBuilder columnNames = new StringBuilder();
		while(data.hasNext()){
			String columnName = data.next().toString();
			columns.add(columnName);
			columnNames.append(columnName);
			if(data.hasNext()) columnNames.append("/");
		}
	}
}
