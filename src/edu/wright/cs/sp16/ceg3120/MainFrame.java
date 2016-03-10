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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Main Frame for application.
 *
 * 
 * @author Bonnie Shields
 */

public class MainFrame {

	static JFrame frame;

	/**
	 * Main Frame for application.
	 * 
	 * 
	 * @author Bonnie Shields
	 */
	public static void createFrame() {
		frame = new JFrame("SQLizard");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// display the menu bar on the top of the page
		displayMenu();
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int answer = JOptionPane.showConfirmDialog(frame,
						"Do you really want to quit?", 
						"Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			} // end of widowClosing

		}); // end of WindowListener
	}

	/**
	 * Display menu function creates the menu bar and adds the "File option to
	 * it. Also sets up the connection option to launch the connect window.
	 */
	public static void displayMenu() {
		JMenuBar menuBar = new JMenuBar();

		// File Menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// File > Connect
		JMenuItem connMenuItem = new JMenuItem("Connect");
		fileMenu.add(connMenuItem);

		ActionListener connect = new ConnectWindow();
		connMenuItem.addActionListener(connect);
		
		frame.setJMenuBar(menuBar);
	
	}

	/**
	 * The main method creates a listener thread to manage the GUI and launches
	 * the window.
	 * 
	 * @param args
	 *            Default input requirement
	 */

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createFrame();
			}
		});
	}

	/**
	 * Add listener to Connect button from File menu.
	 * 
	 * @author Bonnie
	 */
	private static class ConnectWindow implements ActionListener {
		/**
		 * Need to implement this with the CreateWindow class.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			final CreateWindow popup = new CreateWindow();
			popup.setVisible(true);
			popup.pack();
			popup.setLocationRelativeTo(null);
		}
	}
}
