package ui;

import general.WindowsRegistry;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/***
 * Author Bota Catalin
 * Email  bota.catalin@gmail.com
 * Desc This software can read all the USB devices that were connected to a computer. 
 */
public class UsbHistoryApp {

	private JFrame frame;
	private final JRadioButton rdbtnWindows = new JRadioButton("Windows");
	private final JButton btnReadHistory = new JButton("Read USB History");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea txtRegOutput = new JTextArea();
	Set<String> USBKeysSet = new HashSet<String>() ;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnNewMenu = new JMenu("USBHistory");
	private final JMenuItem mntmAbout = new JMenuItem("About");
	private final JMenuItem mntmExit = new JMenuItem("Exit");
	
	private String about_info="Version v1. 06072014, Author: bota.catalin@gmail.com \n" +
			"GitHub https://github.com/botacatalin/USBHistory, \nLicence: GPLv2 https://www.gnu.org/licenses/gpl-2.0.txt\nThis software can read all the USB devices that were connected to a computer.\n";
	private final JLabel lblSelectOs = new JLabel("Select OS");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsbHistoryApp window = new UsbHistoryApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UsbHistoryApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 475, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_btnReadHistory = new GridBagConstraints();
		gbc_btnReadHistory.anchor = GridBagConstraints.WEST;
		gbc_btnReadHistory.insets = new Insets(0, 0, 5, 5);
		gbc_btnReadHistory.gridx = 1;
		gbc_btnReadHistory.gridy = 3;
		btnReadHistory.setToolTipText("Reads ALL USB Devices connected to this PC");
		btnReadHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				txtRegOutput.setText("");
				USBKeysSet = WindowsRegistry.readRegistry("HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Enum\\USBSTOR", "USBSTOR");
				Iterator<String> itr = USBKeysSet.iterator();
				while(itr.hasNext()) {
					txtRegOutput.append(itr.next().replace("FriendlyName    REG_SZ", "") + "\n");
				}
			}
		});
		
		GridBagConstraints gbc_lblSelectOs = new GridBagConstraints();
		gbc_lblSelectOs.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectOs.gridx = 1;
		gbc_lblSelectOs.gridy = 1;
		frame.getContentPane().add(lblSelectOs, gbc_lblSelectOs);
		
		GridBagConstraints gbc_rdbtnWindows = new GridBagConstraints();
		gbc_rdbtnWindows.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnWindows.gridx = 1;
		gbc_rdbtnWindows.gridy = 2;
		rdbtnWindows.setSelected(true);
		frame.getContentPane().add(rdbtnWindows, gbc_rdbtnWindows);
		frame.getContentPane().add(btnReadHistory, gbc_btnReadHistory);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		scrollPane.setViewportView(txtRegOutput);
		
		frame.setJMenuBar(menuBar);
		
		menuBar.add(mnNewMenu);
		mntmAbout.addMouseListener(new MouseAdapter() {
		
		

			@Override
			public void mouseReleased(MouseEvent e) {
				txtRegOutput.setText("");
				txtRegOutput.setText(about_info);
			}
		});
		mntmAbout.setIcon(new ImageIcon(UsbHistoryApp.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		
		mnNewMenu.add(mntmAbout);
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			System.exit(0);
			}
		});
		mntmExit.setIcon(new ImageIcon(UsbHistoryApp.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		
		mnNewMenu.add(mntmExit);
	}

}
