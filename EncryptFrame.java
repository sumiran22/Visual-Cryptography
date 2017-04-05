import java.awt.Component;
//A component is an object having a graphical representation that can be displayed on the screen and that can interact with the user.
import java.awt.Container;
//A generic Abstract Window Toolkit(AWT) container object is a component that can contain other AWT components.
import java.awt.Dimension;
//The attached Javadoc could not be retrieved as the specified Javadoc location is either wrong or currently not accessible.
import java.awt.FocusTraversalPolicy;
//A FocusTraversalPolicy defines the order in which Components with a particular focus cycle root are traversed. Instances can apply the policy to arbitrary focus cycle roots, allowing themselves to be shared across 
import java.awt.event.ActionEvent;
//The attached Javadoc could not be retrieved as the specified Javadoc location is either wrong or currently not accessible.
import java.awt.event.ActionListener;
//The listener interface for receiving action events. The class that is interested in processing an action event implements this interface, and the object created with that class is registered with a component, using the component's addActionListener method.
import java.awt.image.BufferedImage;//The BufferedImage subclass describes an Image with an accessible buffer of image data. 
import java.io.File;//User interfaces and operating systems use system-dependent pathname strings to name files and directories. This class presents an abstract, system-independent view of hierarchical pathnames.
import java.io.IOException;//Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.


import javax.imageio.ImageIO;//A class containing static convenience methods for locating ImageReaders and ImageWriters, and performing simple encoding and decoding.
import javax.swing.BorderFactory;//Factory class for vending standard Border objects. Wherever possible, this factory will hand out references to shared Border instances.
import javax.swing.Box;//The Box class can create several kinds of invisible components that affect layout: glue, struts, and rigid areas.
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;//An implementation of the Icon interface that paints Icons from Images.
import javax.swing.JButton;//JFileChooser provides a simple mechanism for the user to choose a file. For information about using JFileChooser
import javax.swing.JFileChooser;//JOptionPane makes it easy to pop up a standard dialog box that prompts users for a value or informs them of something.
import javax.swing.JFrame;//Provides a set of "lightweight" (all-Java language) components that, to the maximum degree possible, work the same on all platforms.
import javax.swing.JLabel;//A display area for a short text string or an image, or both. A label does not react to input events. As a result, it cannot get the keyboard focus.
import javax.swing.JOptionPane;//JOptionPane makes it easy to pop up a standard dialog box that prompts users for a value or informs them of something.
import javax.swing.JPanel;//JPanel is a generic lightweight container. For examples and task-oriented documentation for JPanel, see How to Use Panels, a section in The Java Tutorial. 
import javax.swing.JScrollPane;//Provides a scrollable view of a lightweight component. A JScrollPane manages a viewport, optional vertical and horizontal scroll bars, and optional row and column heading viewports.
import javax.swing.JTextField;//Contains classes and interfaces used by the JFileChooser component. 
import javax.swing.filechooser.FileFilter;//JPanel is a generic lightweight container. For examples and task-oriented documentation for JPanel


public class EncryptFrame extends JFrame implements ActionListener {//extends Jframe interface and implementing actionlisnter class
	private static final long serialVersionUID = 1L;//implenting Jpanel class
	private JPanel pnlAll = new JPanel();// declaring new panel named as pnlAll
	private JPanel pnlKeyFile = new JPanel();// declaring new panel named as pnlKeyFile
	private JPanel pnlImgFile = new JPanel();// declaring new panel named as pnlImgFile
	
	private JLabel lblDescr = new JLabel("<html>Add a valid key file and a valid source image (png, jpg or gif, will be converted to b/w, not larger than half the keyfile) below to encrypt the source image.</html>");
	//declaring new label named as lblDescr 
	private JLabel lblImg = new JLabel(new ImageIcon(), JLabel.CENTER);//declaring new label named as lblImg
	private JTextField tfKey = new JTextField();//declaring new textfield named as tfkey 
	private JTextField tfImage = new JTextField();//declaring new textfield named as tfImage
	private JButton btnSelectKey = new JButton("Select keyfile");//declaring new button named as tfkey 
	private JButton btnSelectImage = new JButton("Select image");//declaring new button named as btnSelectImage 
	private JButton btnEncrypt = new JButton("Encrypt");//declaring new button named as btnEncrypt
	private JButton btnSave = new JButton("Save encrypted image to file");//declaring new button named as btnSave
	private JScrollPane scrImage = new JScrollPane(lblImg);//declaring new scrollpane named as srcImage 
	
	private JFileChooser fileChooser = new JFileChooser();//used to choose the file
	private BufferedImage imgEncr = null;//The BufferedImage subclass describes an Image with an accessible buffer of image data.
	File fKeyFile = null;
	File fSrcFile = null;
	
	public EncryptFrame(JFrame parent) {
		// size
		//Sets the preferred size of this component. If preferredSize is null, the UI will be asked for the preferred size.
		tfKey.setMaximumSize(new Dimension(tfKey.getMaximumSize().width, tfKey.getPreferredSize().height));
		//If the maximum size has been set to a non-null value just returns it. If the UI delegate's getMaximumSize method returns a non-null value then return that; otherwise defer to the component's layout manager.
		tfImage.setMaximumSize(new Dimension(tfImage.getMaximumSize().width, tfImage.getPreferredSize().height));
		//If the preferredSize has been set to a non-null value just returns it. If the UI delegate's getPreferredSize method returns a non null value
		//then return that; otherwise defer to the component's layout manager.
		int iButMaxWidth = (btnSelectKey.getPreferredSize().width > btnSelectImage.getPreferredSize().width) ?
							btnSelectKey.getPreferredSize().width : btnSelectImage.getPreferredSize().width;
		btnSelectKey.setPreferredSize(new Dimension(iButMaxWidth, btnSelectKey.getPreferredSize().height));
		btnSelectImage.setPreferredSize(new Dimension(iButMaxWidth, btnSelectImage.getPreferredSize().height));
		
		
		// orientation
		lblDescr.setAlignmentX(LEFT_ALIGNMENT);//align lblDescr button to left
		pnlKeyFile.setAlignmentX(LEFT_ALIGNMENT);//align pnlKeyFile button to left
		pnlImgFile.setAlignmentX(LEFT_ALIGNMENT);//align pnlImgFile button to left
		scrImage.setAlignmentX(LEFT_ALIGNMENT);//align lblDescr button to left
		btnSave.setAlignmentX(LEFT_ALIGNMENT);//align lblDescr button to left
		
		// action listener
		btnSelectKey.addActionListener(this);//implement action listener to btnSelectkey
		btnSelectImage.addActionListener(this);//implement action listener to btnSelectImage
		btnEncrypt.addActionListener(this);//implement action listener to btnEncrypt
		btnSave.addActionListener(this);//implement action listener to btnSave
		
		tfKey.setEditable(false);
		tfImage.setEditable(false);
		btnSave.setEnabled(false);
		
		fileChooser.setFileFilter(new FileFilter() {// used to choose the file
			public boolean accept(File arg0) {//accept the file
				if (arg0.isDirectory()) return true;
				if (arg0.getName().toLowerCase().endsWith(".png")) return true;//used to get png file
				if (arg0.getName().toLowerCase().endsWith(".jpg")) return true;//used to get jpg file
				if (arg0.getName().toLowerCase().endsWith(".gif")) return true;//used to get gif file
				return false;
			}

			public String getDescription() {//used to get description
				return "Image";//returns image
			}
		});
		
		pnlKeyFile.setLayout(new BoxLayout(pnlKeyFile, BoxLayout.X_AXIS));//Creates an invisible component that's always the specified 
		pnlKeyFile.add(tfKey);//add tfkey to the panel pnlkeyfile
		pnlKeyFile.add(Box.createRigidArea(new Dimension(10, 0)));//public static Component createRigidArea(Dimension d)
        pnlKeyFile.add(btnSelectKey);//add btnselectkey to the panel btnselectkey
		
		pnlImgFile.setLayout(new BoxLayout(pnlImgFile, BoxLayout.X_AXIS));//Creates an invisible component that's always the specified 
		pnlImgFile.add(tfImage);//add tfImage to the panel pnlImagefile
		pnlImgFile.add(Box.createRigidArea(new Dimension(10, 0)));//public static Component createRigidArea(Dimension d)
		pnlImgFile.add(btnSelectImage);//add btnselectkey to the panel btnselectkey
		
		//Creates an empty border that takes up space but which does no drawing, 
		//specifying the width of the top, left, bottom, and right sides.
		pnlAll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));//Creates an invisible component that's always the specified 
		pnlAll.add(lblDescr);//add lblDescr button to pnlAll panel
		pnlAll.add(pnlKeyFile);//add pnlKeyFile button to pnlAll panel
		pnlAll.add(pnlImgFile);//add pnlImgFile button to pnlAll panel
		pnlAll.add(btnEncrypt);//add btnEncrypt button to pnlAll panel
		pnlAll.add(Box.createVerticalStrut(10));//add verticalStrut to pnlAll panel
		pnlAll.add(scrImage);//add scrImage button to pnlAll panel
		pnlAll.add(btnSave);//add btnSave button to pnlAll panel
		
		setFocusTraversalPolicy(new MyFocusTraversalPolicy());//This Java Code Snippet Describes setFocusTraversalPolicy(FocusTraversalPolicy policy) In Container.
		add(pnlAll);
		setSize(500, 500);//Sets the location of the window relative to the specified component according to the following scenarios. 
		setMinimumSize(new Dimension(384, 253));//Sets the minimum size of this window to a constant value. Subsequent calls to getMinimumSize will always return this value.
		setLocationRelativeTo(parent);//Sets the operation that will happen by default when the user initiates a "close" on this frame
		setTitle("Visual Cryptography - Encrypt Image");//set the title of the frame
		setVisible(true);//Shows or hides this Window depending on the value of parameter b. 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// used to make default close option
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {// declaring new method
		if (e.getActionCommand().equals(btnEncrypt.getText())) {//applying if condition to btnEncrypt that is used for encyption purpose
			//applying validation on fKeyfile 
			if (fKeyFile == null || !fKeyFile.exists() || fSrcFile == null || !fSrcFile.exists()) {
				//displying error message if validation is not correct that file not found
				JOptionPane.showMessageDialog(this, "File not found", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;//return the method
			}
			BufferedImage imgKey = Crypting.loadAndCheckEncrFile(fKeyFile);//The BufferedImage subclass describes an Image with an accessible buffer of image data.
			//JOptionPane makes it easy to pop up a standard dialog box that prompts users for a value or informs them of something.
			if (imgKey == null) {//validate the imgkey
				JOptionPane.showMessageDialog(this, fKeyFile.getName() + " is not a valid key file", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;//return the value
			}
			//check the validation of buffered image
			//Loads the image to be encrypted. If the image is smaller than the maximum possible size and resize is true, it is resized.
			BufferedImage imgSrc = Crypting.loadAndCheckSource(fSrcFile, imgKey.getWidth() / 2, imgKey.getHeight() / 2, true);
			if (imgSrc == null) {//validate the imgSrc
				JOptionPane.showMessageDialog(this, fSrcFile.getName() + " is not fit for encryption", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;//return the value
			}
			//Encrypts an image. 
			//It is assumed that the source image is the maximum possible size (width and height half of that of the key).
			//Validity of source and key image are not checked, see loadAndCheckKey and loadAndCheckSource for that.
			imgEncr = Crypting.encryptImage(imgKey, imgSrc);
			if (imgSrc == null) {
				JOptionPane.showMessageDialog(this, "Could not encrypt file. You should never see this :(", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;//return the value
			}
			//Defines the icon this component will display. If the value of icon is null, nothing is displayed. 
			//The default value of this property is null. 
            lblImg.setIcon(new ImageIcon(imgEncr));
			btnSave.setEnabled(true);
			
			
		}
		//Returns the command string associated with this action. 
		//This string allows a "modal" component to specify one of several commands, depending on its state.
		else if (e.getActionCommand().equals(btnSave.getText())) {
			if (imgEncr == null) return;
			//Sets the selected file. If the file's parent directory is not the current directory, changes the current directory to be the file's parent directory.
			fileChooser.setSelectedFile(new File(""));
			//Sets the string that goes in the JFileChooser window's title bar.
		    fileChooser.setDialogTitle("Save as..");
		    //Pops up a "Save File" file chooser dialog. Note that the text that appears in the approve button is determined by the L&F.
		    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
		    	//Returns the selected file. This can be set either by the programmer via setSelectedFile or by a user action, such as either typing the filename into the UI or selecting the file from a list in the UI.
		    	File f = fileChooser.getSelectedFile();
		    	//Tests if this string ends with the specified suffix.
		    	if (!f.toString().endsWith(".png")) {
		    		//Returns the pathname string of this abstract pathname. This is just the string returned by the getPath() method.
		    		f = new File(f.toString() + ".png");
		    	}
		    	//A class containing static convenience methods for locating ImageReaders and ImageWriters, and performing simple encoding and decoding.
		    	try {
					ImageIO.write(imgEncr, "png", f);
				} catch (IOException e1) {
					//JOptionPane makes it easy to pop up a standard dialog box that prompts users for a value or informs them of something.
					JOptionPane.showMessageDialog(this, "Could not Save file because: " + e1.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
		    }
		}
		//Returns the command string associated with this action. This string allows a "modal" component to specify one of several commands, depending on its state.
		else if (e.getActionCommand().equals(btnSelectKey.getText())) {
			//Sets the string that goes in the JFileChooser window's title bar.
			fileChooser.setDialogTitle("Open keyfile..");
			//Pops up an "Open File" file chooser dialog. Note that the text that appears in the approve button is determined by the L&F.
		    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
		    	//Returns the selected file. This can be set either by the programmer via setSelectedFile or by a user action, such as either typing the filename into the UI or selecting the file from a list in the UI.
		    	if (!fileChooser.getSelectedFile().exists()) return;
		    	if (!fileChooser.getSelectedFile().getName().endsWith(".png")) return;
		    	//Returns the selected file. This can be set either by the programmer via setSelectedFile or by a user action, such as either typing the filename into the UI or selecting the file from a list in the UI.
		    	fKeyFile = fileChooser.getSelectedFile();
		    	tfKey.setText(fKeyFile.toString());
		    }
		}
		//Returns the command string associated with this action. This string allows a "modal" component to specify one of several commands, depending on its state. 
		else if (e.getActionCommand().equals(btnSelectImage.getText())) {
			//choose the given file 
			fileChooser.setDialogTitle("Open source image..");
			//Pops up an "Open File" file chooser dialog. Note that the text that appears in the approve button is determined by the L&F.
		    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
		    	if (!fileChooser.getSelectedFile().exists()) return;
		    	//if (!fileChooser.getSelectedFile().getName().endsWith(".png")) return;
		    	fSrcFile = fileChooser.getSelectedFile();
		    	tfImage.setText(fSrcFile.toString());
		    }
		}
	}
//declare new class and extend the focustraversal policy
	class MyFocusTraversalPolicy extends FocusTraversalPolicy {
		//Returns the Component that should receive the focus after aComponent.
		//aContainer must be a focus cycle root of aComponent or a focus traversal policy provider.
public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
	        if(aComponent.equals(btnSelectKey))//Indicates whether component object is "equal to" btnSelectImage. 
	        	return btnSelectImage;
	        else if(aComponent.equals(btnSelectImage))//Indicates whether component object is "equal to" btnEncrypt. 
	        	return btnEncrypt;
	        else if(aComponent.equals(btnEncrypt) && btnSave.isEnabled())//Indicates whether component object is "equal to" btnEncrypt.  
	        	return btnSave;
	        return btnSelectKey;//return the value
	    }
	   //Returns the Component that should receive the focus before aComponent. aContainer must be a focus cycle root of aComponent or a focus traversal policy provider.
	    public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
	        if(aComponent.equals(btnSelectKey) && btnSave.isEnabled())//Indicates whether component object is "equal to" btnSelectImage. 
	        	return btnSave;
	        else if(aComponent.equals(btnSelectImage))//Indicates whether component object is "equal to" btnSelectImage. 
	        	return btnSelectKey;
	        else if(aComponent.equals(btnEncrypt))//Indicates whether component object is "equal to" btnEncrypt. 
	        	return btnSelectImage;
	        return btnEncrypt;//return the method
	    }
	    
	    public Component getDefaultComponent(Container focusCycleRoot) {
	        return btnSelectKey;//Returns the default Component to focus
	    }
	   
	    public Component getFirstComponent(Container focusCycleRoot) {
	        return btnSelectKey;//Returns the first Component in the traversal cycle.
	    }
	   
	    public Component getLastComponent(Container focusCycleRoot) {
	        return btnSave;//Returns the last Component in the traversal cycle.
	    }
	}
	
}