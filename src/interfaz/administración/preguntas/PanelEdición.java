package interfaz.administración.preguntas;

import interfaz.Menú;
import interfaz.Ventana;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import baseDeDatos.BD;

import observers.Limpiador;

import preguntas.Pregunta;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class PanelEdición extends JPanel implements ActionListener {

	private static final long serialVersionUID = -169258303205019767L;
	private Pregunta pregunta;
	private JPanel panel;
	private ButtonGroup correcta;
	private JRadioButton r3Correcta;
	private JTextField r3Field;
	private JRadioButton r2Correcta;
	private JTextField r2Field;
	private JLabel lblCorrecta;
	private JLabel lblTema;
	private JPanel panelTema;
	private JComboBox<String> temaBox;
	private JLabel lblNuevotema;
	private JTextField temaField;
	private JCheckBox chckbxSinTema;
	private JTextField preguntaField;
	private JTextField r1Field;
	private JRadioButton r1Correcta;
	private JButton btnGuardar;
	private JLabel lblConfirmacin;
	private boolean cambiada;
	private JButton btnBorrar;
	private JPanel confirmación;
	private JLabel lblestsSeguro;
	private JButton btnSi;
	private JButton btnNo;

	/**
	 * Create the panel.
	 * @param p Pregunta a editar
	 */
	public PanelEdición(Pregunta p)
	{
		pregunta = p;
		Limpiador l = new Limpiador();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 400, 10, 3};
		gridBagLayout.rowHeights = new int[] {10, 200, 10, 3};
		gridBagLayout.columnWeights = new double[]{100.0, 0.0, 100.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{100.0, 0.0, 100.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel menú = new Menú(this);
		GridBagConstraints gbc_menú = new GridBagConstraints();
		gbc_menú.anchor = GridBagConstraints.NORTHEAST;
		gbc_menú.gridwidth = 3;
		gbc_menú.insets = new Insets(0, 0, 5, 0);
		gbc_menú.gridx = 0;
		gbc_menú.gridy = 0;
		add(menú, gbc_menú);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 75, 500, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 25, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		lblTema = new JLabel("Tema:");
		GridBagConstraints gbc_lblTema = new GridBagConstraints();
		gbc_lblTema.insets = new Insets(0, 0, 5, 5);
		gbc_lblTema.gridx = 1;
		gbc_lblTema.gridy = 0;
		panel.add(lblTema, gbc_lblTema);

		panelTema = new JPanel();
		GridBagConstraints gbc_panelTema = new GridBagConstraints();
		gbc_panelTema.anchor = GridBagConstraints.WEST;
		gbc_panelTema.insets = new Insets(0, 0, 5, 5);
		gbc_panelTema.fill = GridBagConstraints.VERTICAL;
		gbc_panelTema.gridx = 2;
		gbc_panelTema.gridy = 0;
		panel.add(panelTema, gbc_panelTema);

		temaBox = new JComboBox<>(Pregunta.vectorTemas());
		temaBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cambiada = true;
			}
		});
		for (int i = 0; i < temaBox.getItemCount(); i++)
		{
			if (temaBox.getItemAt(i).equals(pregunta.getTema()))
			{
				temaBox.setSelectedIndex(i);
				break;
			}
		}
		panelTema.add(temaBox);

		lblNuevotema = new JLabel(new ImageIcon("img/añadir.png"));
		lblNuevotema.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNuevotema.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				panelTema.add(temaField);
				panelTema.add(chckbxSinTema);
				panelTema.updateUI();
			}
		});
		panelTema.add(lblNuevotema);

		temaField = new JTextField();
		temaField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				cambiada = true;
			}
		});
		temaField.setColumns(10);
		chckbxSinTema = new JCheckBox("Sin tema");
		chckbxSinTema.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				cambiada = true;
			}
		});
		chckbxSinTema.setBackground(new Color(230, 230, 100));
		if (pregunta.getTema() == null)
		{
			chckbxSinTema.setSelected(true);
			panelTema.add(temaField);
			panelTema.add(chckbxSinTema);
		}

		lblCorrecta = new JLabel("Correcta");
		GridBagConstraints gbc_lblCorrecta = new GridBagConstraints();
		gbc_lblCorrecta.anchor = GridBagConstraints.SOUTH;
		gbc_lblCorrecta.insets = new Insets(0, 0, 5, 0);
		gbc_lblCorrecta.gridx = 3;
		gbc_lblCorrecta.gridy = 0;
		panel.add(lblCorrecta, gbc_lblCorrecta);

		JLabel lblPregunta = new JLabel("Pregunta:");
		GridBagConstraints gbc_lblPregunta = new GridBagConstraints();
		gbc_lblPregunta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPregunta.gridx = 1;
		gbc_lblPregunta.gridy = 1;
		panel.add(lblPregunta, gbc_lblPregunta);

		preguntaField = new JTextField(pregunta.getPregunta());
		preguntaField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e)
			{
				cambiada = true;
			}
		});
		preguntaField.addFocusListener(l);
		GridBagConstraints gbc_preguntaField = new GridBagConstraints();
		gbc_preguntaField.insets = new Insets(0, 0, 5, 5);
		gbc_preguntaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_preguntaField.gridx = 2;
		gbc_preguntaField.gridy = 1;
		panel.add(preguntaField, gbc_preguntaField);
		preguntaField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Respuesta 1:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		r1Field = new JTextField(pregunta.getRespuesta(0));
		r1Field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e)
			{
				cambiada = true;
			}
		});
		r1Field.addFocusListener(l);
		GridBagConstraints gbc_r1Field = new GridBagConstraints();
		gbc_r1Field.insets = new Insets(0, 0, 5, 5);
		gbc_r1Field.fill = GridBagConstraints.HORIZONTAL;
		gbc_r1Field.gridx = 2;
		gbc_r1Field.gridy = 2;
		panel.add(r1Field, gbc_r1Field);
		r1Field.setColumns(10);

		r1Correcta = new JRadioButton();
		r1Correcta.addActionListener(this);
		GridBagConstraints gbc_r1correcta = new GridBagConstraints();
		gbc_r1correcta.insets = new Insets(0, 0, 5, 0);
		gbc_r1correcta.gridx = 3;
		gbc_r1correcta.gridy = 2;
		panel.add(r1Correcta, gbc_r1correcta);

		JLabel lblRespuesta = new JLabel("Respuesta 2:");
		GridBagConstraints gbc_lblRespuesta = new GridBagConstraints();
		gbc_lblRespuesta.insets = new Insets(0, 0, 5, 5);
		gbc_lblRespuesta.gridx = 1;
		gbc_lblRespuesta.gridy = 3;
		panel.add(lblRespuesta, gbc_lblRespuesta);

		r2Field = new JTextField(pregunta.getRespuesta(1));
		r2Field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e)
			{
				cambiada = true;
			}
		});
		r2Field.addFocusListener(l);
		GridBagConstraints gbc_r2Field = new GridBagConstraints();
		gbc_r2Field.insets = new Insets(0, 0, 5, 5);
		gbc_r2Field.fill = GridBagConstraints.HORIZONTAL;
		gbc_r2Field.gridx = 2;
		gbc_r2Field.gridy = 3;
		panel.add(r2Field, gbc_r2Field);
		r2Field.setColumns(10);

		r2Correcta = new JRadioButton();
		r2Correcta.addActionListener(this);
		GridBagConstraints gbc_r2correcta = new GridBagConstraints();
		gbc_r2correcta.insets = new Insets(0, 0, 5, 0);
		gbc_r2correcta.gridx = 3;
		gbc_r2correcta.gridy = 3;
		panel.add(r2Correcta, gbc_r2correcta);

		JLabel lblRespuesta_1 = new JLabel("Respuesta 3:");
		GridBagConstraints gbc_lblRespuesta_1 = new GridBagConstraints();
		gbc_lblRespuesta_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRespuesta_1.gridx = 1;
		gbc_lblRespuesta_1.gridy = 4;
		panel.add(lblRespuesta_1, gbc_lblRespuesta_1);

		r3Field = new JTextField(pregunta.getNumRespuestas() == 3 ? pregunta.getRespuesta(2) : null);
		r3Field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e)
			{
				cambiada = true;
			}
		});
		r3Field.addFocusListener(l);
		GridBagConstraints gbc_r3Field = new GridBagConstraints();
		gbc_r3Field.insets = new Insets(0, 0, 5, 5);
		gbc_r3Field.fill = GridBagConstraints.HORIZONTAL;
		gbc_r3Field.gridx = 2;
		gbc_r3Field.gridy = 4;
		panel.add(r3Field, gbc_r3Field);
		r3Field.setColumns(10);

		r3Correcta = new JRadioButton();
		r3Correcta.addActionListener(this);
		GridBagConstraints gbc_r3Correcta = new GridBagConstraints();
		gbc_r3Correcta.insets = new Insets(0, 0, 5, 0);
		gbc_r3Correcta.gridx = 3;
		gbc_r3Correcta.gridy = 4;
		panel.add(r3Correcta, gbc_r3Correcta);

		correcta = new ButtonGroup();
		correcta.add(r1Correcta);
		correcta.add(r2Correcta);
		correcta.add(r3Correcta);

		switch (pregunta.getCorrecta())
		{
			case 0:
				r1Correcta.setSelected(true);
				break;
			case 1:
				r2Correcta.setSelected(true);
				break;
			case 2:
				r3Correcta.setSelected(true);
		}

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (cambiada)
				{
					boolean correcto = true;
					Color rojo = new Color(255, 100, 100);

					if (preguntaField.getText().length() < 1)
					{
						correcto = false;
						preguntaField.setBackground(rojo);
					}

					if (r1Field.getText().length() < 1)
					{
						correcto = false;
						r1Field.setBackground(rojo);
					}

					if (r2Field.getText().length() < 1)
					{
						correcto = false;
						r2Field.setBackground(rojo);
					}

					if (r3Correcta.isSelected() && r3Field.getText().length() < 1)
					{
						correcto = false;
						r3Field.setBackground(rojo);
					}
					else if ( ! r3Correcta.isSelected())
					{
						r3Field.setBackground(Color.WHITE);
					}

					if (correcto)
					{
						String tema = chckbxSinTema.isSelected() ? null : temaField.getText().equals("") ? (String) temaBox.getSelectedItem() : temaField.getText();
						byte nCorrecta;
						if (r1Correcta.isSelected())
						{
							nCorrecta = 0;
						}
						else if (r2Correcta.isSelected())
						{
							nCorrecta = 1;
						}
						else
						{
							nCorrecta = 2;
						}

						final Pregunta p = new Pregunta(pregunta.getId(), tema, preguntaField.getText(), nCorrecta, r1Field.getText(), r2Field.getText(), r3Field.getText());

						BD.getInstance().actualizar("UPDATE preguntas SET tema = " + (tema == null ? "NULL" : "'" + tema + "'") + ", pregunta = '" + preguntaField.getText() + "' WHERE id = " + pregunta.getId() + ";");
						BD.getInstance().actualizar("UPDATE respuestas SET respuesta = '" + r1Field.getText() + "', correcta = " + (nCorrecta == 0 ? 1 : 0) + " WHERE respuesta = '" + pregunta.getRespuesta(0) + "' AND pregunta = " + pregunta.getId() + ";");
						BD.getInstance().actualizar("UPDATE respuestas SET respuesta = '" + r2Field.getText() + "', correcta = " + (nCorrecta == 1 ? 1 : 0) + " WHERE respuesta = '" + pregunta.getRespuesta(1) + "' AND pregunta = " + pregunta.getId() + ";");
						BD.getInstance().actualizar("UPDATE respuestas SET respuesta = '" + r3Field.getText() + "', correcta = " + (nCorrecta == 2 ? 1 : 0) + " WHERE respuesta = '" + pregunta.getRespuesta(2) + "' AND pregunta = " + pregunta.getId() + ";");

						panel.removeAll();
						panel.setLayout(new FlowLayout());
						panel.add(new JLabel(new ImageIcon("img/tick.png")));
						panel.updateUI();
						(new Thread() {

							public void run()
							{
								try
								{
									Thread.sleep(1000);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								Ventana.getInstance().setContentPane(new PanelEdición(p));
								Ventana.getInstance().validate();
								Ventana.getInstance().repaint();
							}
						}).start();
					}
				}
			}
		});
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 5;
		panel.add(btnGuardar, gbc_btnGuardar);

		lblConfirmacin = new JLabel(new ImageIcon("img/tick.png"));
		lblConfirmacin.setVisible(false);

		btnBorrar = new JButton("Borrar Pregunta");
		btnBorrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmación.setVisible(true);
				panel.updateUI();
			}
		});
		GridBagConstraints gbc_btnBorrar = new GridBagConstraints();
		gbc_btnBorrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBorrar.gridx = 2;
		gbc_btnBorrar.gridy = 6;
		panel.add(btnBorrar, gbc_btnBorrar);

		confirmación = new JPanel();
		confirmación.setVisible(false);
		FlowLayout flowLayout = (FlowLayout) confirmación.getLayout();
		flowLayout.setHgap(20);
		GridBagConstraints gbc_confirmación = new GridBagConstraints();
		gbc_confirmación.insets = new Insets(0, 0, 5, 5);
		gbc_confirmación.fill = GridBagConstraints.BOTH;
		gbc_confirmación.gridx = 2;
		gbc_confirmación.gridy = 7;
		panel.add(confirmación, gbc_confirmación);

		lblestsSeguro = new JLabel("¿Estás seguro?");
		confirmación.add(lblestsSeguro);

		btnSi = new JButton("Si");
		btnSi.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				BD.getInstance().actualizar("DELETE FROM preguntas WHERE id = " + pregunta.getId());
				panel.removeAll();
				panel.setLayout(new FlowLayout());
				panel.add(new JLabel(new ImageIcon("img/tick.png")));
				panel.updateUI();
				(new Thread() {

					public void run()
					{
						try
						{
							Thread.sleep(1000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						Ventana.getInstance().setContentPane(new EditarPregunta());
						Ventana.getInstance().validate();
						Ventana.getInstance().repaint();
					}
				}).start();
			}
		});
		confirmación.add(btnSi);

		btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmación.setVisible(false);
				panel.updateUI();
			}
		});
		confirmación.add(btnNo);
		GridBagConstraints gbc_lblConfirmacin = new GridBagConstraints();
		gbc_lblConfirmacin.insets = new Insets(0, 0, 0, 5);
		gbc_lblConfirmacin.gridx = 2;
		gbc_lblConfirmacin.gridy = 8;
		panel.add(lblConfirmacin, gbc_lblConfirmacin);

		cambiada = false;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		cambiada = true;
	}
}
