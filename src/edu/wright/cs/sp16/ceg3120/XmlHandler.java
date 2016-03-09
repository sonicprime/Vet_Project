/*
 * Copyright (C) 2016
 * 
 * 
 * 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.wright.cs.sp16.ceg3120;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/** Handles all XML for aliases.
 * @author Nick Madden
 * 
 */
public class XmlHandler {
	
	/** Populates a list of aliases saved in the aliases.xml file.
	 * @author Nick Madden
	 * @return list of aliases
	 */
	public static String[] populateAlias() {
		String[] listA = { "No Saved Aliases" };
		try {
			File xmlFile = new File("UserData/aliases.xml");
			if (xmlFile.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setValidating(true);
				DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				System.out.println("reading");
				NodeList aliasList = doc.getElementsByTagName("alias");
				int length = aliasList.getLength();
				listA = new String[length + 1];
				listA[0] = "Choose Alias";
				for (int i = 0; i < length; i++) {
					Node currentNode = aliasList.item(i);
					Element curElement = (Element) currentNode;
					listA[i + 1] = curElement.getAttribute("name");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return listA;
	}
	
	/**
	 * Writes Alias to File.
	 * 
	 * @param alias
	 *            alias name
	 * @param dbName
	 *            database name
	 * @param dbUrl
	 *            database url
	 * @param userName
	 *            User name
	 * @param password
	 *            password
	 * @param salt
	 *            password salt
	 * @param savePass
	 *            Whether to save password or not
	 * @param driver
	 *            Driver to connect
	 * @throws IOException
	 *             Throws Input Output exceptions
	 * @throws SAXException
	 *             Throws SAX exceptions
	 */
	public static void writeAlias(String alias, String dbName, String dbUrl, String userName, 
			String password, String salt, boolean savePass, int driver) 
					throws SAXException, IOException {
		System.out.println("writing");
		try {
			File file = new File("UserData/aliases.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Element root;
			Document doc;

			// root elements

			if (file.exists()) {
				doc = docBuilder.parse("UserData/aliases.xml");
				root = doc.getDocumentElement();
			} else {
				boolean fileStatus = false;
				File userDir = new File("UserData");

				// if the directory does not exist, create it
				if (!userDir.exists()) {
					try {
						fileStatus = userDir.mkdir();
						if (fileStatus == false) {
							System.out.println(fileStatus);
						}
					} catch (SecurityException se) {
						// handle it
						System.out.println(fileStatus);
					}
				}
				doc = docBuilder.newDocument();
				root = doc.createElement("aliases");
				doc.appendChild(root);
			}
			Element al = doc.createElement("alias");
			al.setAttribute("name", alias);
			root.appendChild(al);

			Element db = doc.createElement("dbName");
			db.appendChild(doc.createTextNode(dbName));
			al.appendChild(db);

			Element ur = doc.createElement("dbURL");
			ur.appendChild(doc.createTextNode(dbUrl));
			al.appendChild(ur);

			Element nm = doc.createElement("userName");
			nm.appendChild(doc.createTextNode(userName));
			al.appendChild(nm);

			Element dv = doc.createElement("driver");
			dv.appendChild(doc.createTextNode(Integer.toString(driver)));
			al.appendChild(dv);
			
			Element ps = doc.createElement("password");
			ps.setAttribute("saved", (savePass ? "true" : "false"));
			al.appendChild(ps);

			Element sl = doc.createElement("salt");
			Element sps = doc.createElement("pass");
			sl.appendChild(doc.createTextNode((savePass ? salt : "")));
			sps.appendChild(doc.createTextNode((savePass ? password : "" )));
			ps.appendChild(sps);
			ps.appendChild(sl);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"aliases.dtd");
			StreamResult result = new StreamResult(new File("UserData/aliases.xml"));
			transformer.transform(source, result);

			System.out.println("File saved!");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}
	/**
	 * Reads an alias based on name.
	 * @author Nick Madden
	 * @param alias
	 *            Alias name to read
	 */
	
	public static void readAlias(String alias, JCheckBox savePassword, JTextField name, 
			JTextField databaseUrl, JTextField username, 
			JTextField password, JComboBox<String> driver) {
		try {
			File xmlFile = new File("UserData/aliases.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(true);
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("reading");
			NodeList aliasList = doc.getElementsByTagName("alias");
			for (int i = 0; i < aliasList.getLength(); i++) {
				Node currentNode = aliasList.item(i);
				Element curElement = (Element) currentNode;
				if (currentNode.getNodeType() == Node.ELEMENT_NODE
						&& alias.equals(curElement.getAttribute("name"))) {
					System.out.println(curElement.getElementsByTagName("dbName").item(0)
							.getTextContent());
					name.setText(curElement.getElementsByTagName("dbName").item(0)
							.getTextContent());
					databaseUrl.setText(curElement.getElementsByTagName("dbURL").item(0)
							.getTextContent());
					username.setText(curElement.getElementsByTagName("userName").item(0)
							.getTextContent());
					driver.setSelectedIndex(Integer.valueOf(
							curElement.getElementsByTagName("driver").item(0).getTextContent()));
					curElement = (Element) curElement.getElementsByTagName("password").item(0);
					//String holdPass = curElement.getTextContent();
					//String holdSalt = curElement.getElementsByTagName("salt").item(0)
					//		.getTextContent();
					// final PasswordEncryptionService pes = new
					// PasswordEncryptionService();
					// password.setText(pes.deCrypt(holdPass, holdSalt));
					password.setText("this is broken right now");
					driver.setSelectedIndex(1);
					savePassword.setSelected(Boolean.valueOf(curElement.getAttribute("saved")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}
}