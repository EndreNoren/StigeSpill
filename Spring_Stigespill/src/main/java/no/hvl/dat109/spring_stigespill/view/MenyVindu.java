package no.hvl.dat109.spring_stigespill.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MenyVindu extends JFrame{
	
	public MenyVindu(ActionListener nyttSpillAction, 
			ActionListener hentSpillAction, 
			ActionListener avsluttSpillAction) {
		super("Hovedmeny");
		setSize(300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(4,1,10,10));
		
		add(new JLabel("Stigespill", SwingConstants.CENTER));
		
		JButton nyttSpillButton = new JButton("Nytt spill");
		nyttSpillButton.addActionListener();
		
		JButton replayButton = new JButton("Replay");
		replayButton.addActionListener();
		
		
		JButton avsluttButton = new JButton("Avslutt");
		avsluttButton.addActionListener();
		
	}

}
