package me.zeroxffff.mcipresolver.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;


import me.zeroxffff.mcipresolver.MCIPResolver;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Frame extends JFrame {
	
	private static String ip, porta;

	/**
	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>5.
	 */
	private static final long serialVersionUID = -1951478139626089328L;
	private JPanel contentPane;
	private JTextField fieldIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		setResizable(false);
		setTitle("Minecraft Server IP Resolver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Licenciado em GNU GPL.");
		lblNewLabel.setBounds(301, 236, 123, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIp.setBounds(10, 60, 46, 14);
		contentPane.add(lblIp);
		
		fieldIP = new JTextField();
		fieldIP.setToolTipText("Digite o ip do servidor aqui...");
		fieldIP.setBounds(31, 58, 393, 20);
		contentPane.add(fieldIP);
		fieldIP.setColumns(10);
		
		JButton btnOk = new JButton(" OK!");
		
		btnOk.setBounds(335, 89, 89, 23);
		contentPane.add(btnOk);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Resultado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 122, 414, 107);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(207, 21, 0, 0);
		panel.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("IP:");
		lblNewLabel_1.setBounds(10, 21, 22, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 46, 46, 14);
		panel.add(lblPorta);
		
		final JLabel resultadoIP = new JLabel("Nenhum.");
		resultadoIP.setFont(new Font("Tahoma", Font.BOLD, 11));
		resultadoIP.setForeground(Color.BLUE);
		resultadoIP.setBounds(26, 21, 156, 14);
		panel.add(resultadoIP);
		
		final JLabel resultadoPorta = new JLabel("Nenhuma.");
		resultadoPorta.setForeground(Color.BLUE);
		resultadoPorta.setFont(new Font("Tahoma", Font.BOLD, 11));
		resultadoPorta.setBounds(42, 46, 94, 14);
		panel.add(resultadoPorta);
		
		final JButton btnCopiar = new JButton("Copiar");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy(ip+":"+porta);
				JOptionPane.showMessageDialog(null,
					    "IP Copiado!!!",
					    "Minecraft IP Resolver",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnCopiar.setEnabled(false);
		btnCopiar.setBounds(175, 42, 89, 23);
		panel.add(btnCopiar);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (fieldIP.getText() == null || fieldIP.getText().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null,
						    "Por favor, adicione um ip!",
						    "Minecraft IP Resolver",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					resultadoIP.setText("Aguarde...");
					resultadoPorta.setText("Aguarde...");
						Thread t = new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								List<?> resultado = MCIPResolver.getAdress(fieldIP.getText());
								if (resultado == null) {
									resultadoIP.setForeground(Color.RED);
									resultadoIP.setText("Falha ao obter.");
									resultadoPorta.setForeground(Color.RED);
									resultadoPorta.setText("Falha ao obter.");
									
									btnCopiar.setEnabled(false);
									
								}else{
									ip = (String) resultado.get(0);
									porta = (String) resultado.get(1);
									
									resultadoIP.setForeground(new Color(0, 100, 0));
									resultadoIP.setText(ip);
									resultadoPorta.setForeground(new Color(0, 100, 0));
									resultadoPorta.setText(porta);
									
									btnCopiar.setEnabled(true);
									JOptionPane.showMessageDialog(null,
										    "Sucesso!\nO endereço e porta do servidor foram adicionados na descrição...",
										    "Minecraft IP Resolver",
										    JOptionPane.INFORMATION_MESSAGE);
								}
							}
						});
						t.setName("MCIPResolver");
						t.start();
					
				}
			}
		});
	}
	
	private void copy(String texto) {
	    StringSelection stringSelection = new StringSelection(texto);
	    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clpbrd.setContents(stringSelection, null);
	}
}
