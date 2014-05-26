package interfaz.administración.preguntas;

import interfaz.Menú;
import interfaz.Ventana;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import observers.Limpiador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JRadioButton;

import baseDeDatos.BD;
import javax.swing.JComboBox;

import preguntas.Pregunta;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class NuevaPregunta extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6218773651633068491L;
	private JPanel panel;
	private JTextField preguntaField;
	private JTextField r1Field;
	private JTextField r2Field;
	private JTextField r3Field;
	private JButton btnGuardar;
	private JLabel lblCorrecta;
	private JRadioButton r1Correcta;
	private JRadioButton r2Correcta;
	private JRadioButton r3Correcta;
	private ButtonGroup correcta;
	private JRadioButton correctaElegida;
	private JLabel lblTema;
	private JLabel lblConfirmacin;
	private JPanel panelTema;
	private JComboBox<String> temaBox;
	private JLabel lblNuevotema;
	private JTextField temaField;
	private JCheckBox chckbxSinTema;

	/**
	 * Create the panel.
	 */
	public NuevaPregunta()
	{
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
		gbl_panel.rowHeights = new int[]{0, 0, 25, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_panelTema.gridx = 2;
		gbc_panelTema.gridy = 0;
		panel.add(panelTema, gbc_panelTema);

		temaBox = new JComboBox<>(Pregunta.vectorTemas());
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
		temaField.setColumns(10);
		chckbxSinTema = new JCheckBox("Sin tema");
		chckbxSinTema.setBackground(new Color(230, 230, 100));

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

		preguntaField = new JTextField();
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

		r1Field = new JTextField();
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

		r2Field = new JTextField();
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

		r3Field = new JTextField();
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

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean correcto = true;
				Color rojo = new Color(255, 100, 100);

				if (preguntaField.getText().length() < 1 || existePregunta())
				{
					correcto = false;
					preguntaField.setBackground(rojo);
				}

				if (correctaElegida == null)
				{
					correcto = false;
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

				if (correctaElegida == r3Correcta && r3Field.getText().length() < 1)
				{
					correcto = false;
					r3Field.setBackground(rojo);
				}
				else if (correctaElegida != r3Correcta)
				{
					r3Field.setBackground(Color.WHITE);
				}

				if (correcto)
				{
					ResultSet pregunta;
					if (temaField.getText().length() > 0)
					{
						BD.getInstance().actualizar("INSERT INTO preguntas (tema, pregunta) VALUES ('" + temaField.getText() + "', '" + preguntaField.getText() + "');");
						pregunta = BD.getInstance().consulta("SELECT id FROM preguntas WHERE tema = '" + temaField.getText() + "' AND pregunta = '" + preguntaField.getText() + "';");
					}
					else
					{
						if (chckbxSinTema.isSelected() || temaBox.getSelectedItem().equals("Sin tema"))
						{
							BD.getInstance().actualizar("INSERT INTO preguntas (pregunta) VALUES ('" + preguntaField.getText() + "');");
							pregunta = BD.getInstance().consulta("SELECT id FROM preguntas WHERE pregunta = '" + preguntaField.getText() + "';");
						}
						else
						{
							BD.getInstance().actualizar("INSERT INTO preguntas (tema, pregunta) VALUES ('" + temaBox.getSelectedItem() + "', '" + preguntaField.getText() + "');");
							pregunta = BD.getInstance().consulta("SELECT id FROM preguntas WHERE tema = '" + temaBox.getSelectedItem() + "' AND pregunta = '" + preguntaField.getText() + "';");
						}
					}

					int pId = 0;
					try
					{
						while (pregunta.next())
						{
							pId = pregunta.getInt("id");
						}
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						Ventana.getInstance().dispose();
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e1.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
					}

					if (pId == 0)
					{
						Ventana.getInstance().dispose();
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error al crear la pregunta", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
					}

					BD.getInstance().actualizar("INSERT INTO respuestas (respuesta, correcta, pregunta) VALUES ('" + r1Field.getText() + "', " + (correctaElegida == r1Correcta ? 1 : 0) + ", " + pId + ");");
					BD.getInstance().actualizar("INSERT INTO respuestas (respuesta, correcta, pregunta) VALUES ('" + r2Field.getText() + "', " + (correctaElegida == r2Correcta ? 1 : 0) + ", " + pId + ");");

					if (r3Field.getText().length() > 0)
					{
						BD.getInstance().actualizar("INSERT INTO respuestas (respuesta, correcta, pregunta) VALUES ('" + r3Field.getText() + "', " + (correctaElegida == r3Correcta ? 1 : 0) + ", " + pId + ");");
					}

					lblConfirmacin.setVisible(true);
					(new Thread()
					{
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
							lblConfirmacin.setVisible(false);
						}
					}).start();
					preguntaField.setText("");
					correcta.clearSelection();
					correctaElegida = null;
					r1Field.setText("");
					r2Field.setText("");
					r3Field.setText("");

					if (chckbxSinTema.isSelected() || temaField.getText().length() > 0)
					{
						Vector<String> v = Pregunta.vectorTemas();
						temaBox = new JComboBox<>(v);
					}
					panelTema.remove(chckbxSinTema);
					panelTema.remove(temaField);
					panelTema.updateUI();
					chckbxSinTema.setSelected(false);
					temaField.setText("");
				}
			}

			private boolean existePregunta()
			{
				String tema;
				if (temaField.getText().length() > 0)
				{
					tema = " AND tema = '" + temaField.getText() + "'";
				}
				else
				{
					if (chckbxSinTema.isSelected() || temaBox.getSelectedItem().equals("Sin tema"))
					{
						tema = " AND tema IS NULL";
					}
					else
					{
						tema = " AND tema = '" + temaBox.getSelectedItem() + "'";
					}
				}
				return BD.getInstance().contar("preguntas", "pregunta = '" + preguntaField.getText() + "'" + tema) > 0;
			}
		});
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 5;
		panel.add(btnGuardar, gbc_btnGuardar);

		lblConfirmacin = new JLabel(new ImageIcon("img/tick.png"));
		lblConfirmacin.setVisible(false);
		GridBagConstraints gbc_lblConfirmacin = new GridBagConstraints();
		gbc_lblConfirmacin.insets = new Insets(0, 0, 0, 5);
		gbc_lblConfirmacin.gridx = 2;
		gbc_lblConfirmacin.gridy = 6;
		panel.add(lblConfirmacin, gbc_lblConfirmacin);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		correctaElegida = (JRadioButton) e.getSource();
	}
}