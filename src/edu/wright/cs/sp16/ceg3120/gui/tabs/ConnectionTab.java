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

package edu.wright.cs.sp16.ceg3120.gui.tabs;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 * The tab that contains all the components for the connection.
 * 
 * @author sam
 *
 */
public class ConnectionTab extends JPanel {

	private static final long serialVersionUID = -9056641573923593935L;

	/**
	 * Default constructor, initializes components.
	 */
	public ConnectionTab() {
		super();

		initComponents();
	}

	/**
	 * TODO: create all components for this window and initialize them here.
	 */
	private void initComponents() {

		JToggleButton contentButton = new JToggleButton();

		contentButton.setSelected(true);
		contentButton.setUI(new MetalToggleButtonUI());

		try {
			URL url = new URL("https://cdn2.iconfinder.com/data/icons/flat-and-simple-part-4/128/table_check-128.png");
			BufferedImage img = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(img);
			contentButton.setIcon(icon);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
/*		
<<<<<<< HEAD
		String text = "This is a fake text box where we can add fake "
				+ "SQL queries. This is a temporary text box til we develop "
				+ "actual SQL query builder. This text area is added so we can add "
				+ "functionality for cut, copy, paste, find, and replace JMenuItems. ";
		JTextArea textArea = new JTextArea(25,60);
		textArea.setText(text);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);
=======
*/
		try {
			URL url = new URL("https://cdn2.iconfinder.com/data/icons/flat-and-simple-part-4/128/table_alert-128.png");
			BufferedImage img = ImageIO.read(url);
			ImageIcon icon = new ImageIcon(img);
			contentButton.setSelectedIcon(icon);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		add(contentButton);

		JToggleButton structureButton = new JToggleButton("Structure");
		JToggleButton relationshipButton = new JToggleButton("Relationship");
		JToggleButton queryBuilderButton = new JToggleButton("Query Builder");
		add(structureButton);
		add(relationshipButton);
		add(queryBuilderButton);
//>>>>>>> fc4ff12e2a0f8d6a84f7f5a572205343703e800a
	}
}
