/** * Copyright (c) 2010 Source Auditor Inc. * *   Licensed under the Apache License, Version 2.0 (the "License"); *   you may not use this file except in compliance with the License. *   You may obtain a copy of the License at * *       http://www.apache.org/licenses/LICENSE-2.0 * *   Unless required by applicable law or agreed to in writing, software *   distributed under the License is distributed on an "AS IS" BASIS, *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. *   See the License for the specific language governing permissions and *   limitations under the License. * */package org.spdx.tools;import java.io.DataInputStream;import java.io.File;import java.io.FileInputStream;import java.io.FileNotFoundException;import java.io.FileOutputStream;import java.io.IOException;import java.io.InputStream;import java.io.OutputStream;import java.util.Properties;import org.spdx.rdfparser.SPDXDocument;import org.spdx.tag.BuildDocument;import org.spdx.tag.CommonCode;import org.spdx.tag.HandBuiltParser;import org.spdx.tag.NoCommentInputStream;import antlr.RecognitionException;import antlr.TokenStreamException;import com.hp.hpl.jena.rdf.model.Model;import com.hp.hpl.jena.rdf.model.ModelFactory;/** * Translates a tag-value file to an RDF XML format Usage: TagToRDF * spdxfile.spdx rdfxmlfile.rdf where spdxfile.spdx is a valid SPDX tag-value * file and rdfxmlfile.rdf is the output SPDX RDF file. *  * @author Rana Rahal, Protecode Inc. */public class TagToRDF {	static final int MIN_ARGS = 2;	static final int MAX_ARGS = 2;	public static void main(String[] args) {				if (args.length < MIN_ARGS) {			usage();			return;		}		if (args.length > MAX_ARGS) {			usage();			return;		}		FileInputStream spdxTagStream;		try {			spdxTagStream = new FileInputStream(args[0]);		} catch (FileNotFoundException ex) {			System.out					.printf("Tag-Value file %1$s does not exists.\n", args[0]);			return;		}		File spdxRDFFile = new File(args[1]);		if (spdxRDFFile.exists()) {			System.out					.printf("Error: File %1$s already exists - please specify a new file.\n",							args[1]);			return;		}		try {			if (!spdxRDFFile.createNewFile()) {				System.out.println("Could not create the new SPDX RDF file "						+ args[1]);				usage();				return;			}		} catch (IOException e1) {			System.out.println("Could not create the new SPDX Tag-Value file "					+ args[1]);			System.out.println("due to error " + e1.getMessage());			usage();			return;		}		FileOutputStream out;		try {			out = new FileOutputStream(spdxRDFFile);		} catch (FileNotFoundException e1) {			System.out.println("Could not write to the new SPDX RDF file "					+ args[1]);			System.out.println("due to error " + e1.getMessage());			usage();			return;		}		try {			convertTagFileToRdf(spdxTagStream, out);		} catch (Exception e) {			System.err.println("Error creating SPDX Analysis: " + e);		} finally {			if (out != null) {				try {					out.close();				} catch (IOException e) {					System.out.println("Error closing RDF file: " + e.getMessage());				}			}			if (spdxTagStream != null) {				try {					spdxTagStream.close();				} catch (IOException e) {					System.out.println("Error closing Tag/Value file: " + e.getMessage());				}			}		}	}	/**	 * Convert a Tag File to an RDF output stream	 * @param spdxTagFile File containing a tag/value formatted SPDX file	 * @param out Stream where the RDF/XML data is written	 * @throws Exception 	 * @throws TokenStreamException 	 * @throws RecognitionException 	 */	public static void convertTagFileToRdf(InputStream spdxTagFile,			OutputStream out) throws RecognitionException, TokenStreamException, Exception {			// read the tag-value constants from a file			Properties constants = CommonCode.getTextFromProperties("org/spdx/tag/SpdxTagValueConstants.properties");			NoCommentInputStream nci = new NoCommentInputStream(spdxTagFile);//			TagValueLexer lexer = new TagValueLexer(new DataInputStream(nci));//			TagValueParser parser = new TagValueParser(lexer);			HandBuiltParser parser = new HandBuiltParser(new DataInputStream(nci));			Model model = ModelFactory.createDefaultModel();			SPDXDocument analysis = new SPDXDocument(model);			parser.setBehavior(new BuildDocument(model, analysis, constants));			parser.data();			model.write(out, "RDF/XML-ABBREV");	}	private static void usage() {		System.out.println("Usage: TagToRDF spdxfile.spdx rdfxmlfile.rdf \n"				+ "where spdxfile.spdx is a valid SPDX tag-value file and \n"				+ "rdfxmlfile.rdf is the output SPDX RDF analysis file.");	}}