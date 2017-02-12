import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class VisualCryptography extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnlAll = new JPanel();
	
	private JButton btnAbout = new JButton("About");
	private JButton btnGenerateKey = new JButton("Generate Key");
	private JButton btnEncrypt = new JButton("Encrypt Image");
	private JButton btnGenerateKeyAndEncrypt = new JButton("<html><center>Generate Key & Encrypt Image at the same time</center></html>");
	private JButton btnDecrypt = new JButton("Decrypt Image");
        private JLabel l=new JLabel();
	
	public static void main(String[] args) {
		new VisualCryptography();
	}
	
	public VisualCryptography() {
		
		// alignment
		btnAbout.setAlignmentX(CENTER_ALIGNMENT);
		btnGenerateKey.setAlignmentX(CENTER_ALIGNMENT);
		btnEncrypt.setAlignmentX(CENTER_ALIGNMENT);
		btnGenerateKeyAndEncrypt.setAlignmentX(CENTER_ALIGNMENT);
		btnDecrypt.setAlignmentX(CENTER_ALIGNMENT);
		
		// maximize the width
		btnAbout.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnAbout.getMaximumSize().height));
		btnGenerateKey.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnGenerateKey.getMaximumSize().height));
		btnEncrypt.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnEncrypt.getMaximumSize().height));
		btnGenerateKeyAndEncrypt.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnGenerateKeyAndEncrypt.getMaximumSize().height));
		btnDecrypt.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnDecrypt.getMaximumSize().height));
		
		// action listener
		btnAbout.addActionListener(this);
		btnGenerateKey.addActionListener(this);
		btnEncrypt.addActionListener(this);
		btnGenerateKeyAndEncrypt.addActionListener(this);
		btnDecrypt.addActionListener(this);
		
		//btnAbout.setFont(new Font( "",Font.BOLD, 20));
		
		pnlAll.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));
		pnlAll.add(btnAbout);
		pnlAll.add(Box.createVerticalStrut(btnAbout.getPreferredSize().height));
		pnlAll.add(btnGenerateKey);
		pnlAll.add(Box.createVerticalStrut(btnGenerateKey.getPreferredSize().height));
		pnlAll.add(btnEncrypt);
		pnlAll.add(Box.createVerticalStrut(btnEncrypt.getPreferredSize().height));
		pnlAll.add(btnGenerateKeyAndEncrypt);
		pnlAll.add(Box.createVerticalStrut(btnGenerateKeyAndEncrypt.getPreferredSize().height));
		pnlAll.add(btnDecrypt);
		pnlAll.add(Box.createVerticalStrut(btnDecrypt.getPreferredSize().height));
               
                pnlAll.add(l);
		
		add(pnlAll);
		setSize(350, 320);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Visual Cryptography");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(btnAbout.getText())) {
			new AboutDialog(this);
		} else if (e.getActionCommand().equals(btnGenerateKey.getText())) {
			new KeyGenFrame(this);
		
		}
	}
}