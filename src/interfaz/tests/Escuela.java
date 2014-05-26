package interfaz.tests;


import interfaz.Menú;
import interfaz.Ventana;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JList;

import preguntas.Examen;
import preguntas.Pregunta;
import usuarios.Usuario;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import baseDeDatos.BD;

/**
 * Panel de tests
 * @author Razican (Iban Eguia)
 *
 */
public class Escuela extends JPanel {

	private static final long serialVersionUID = 1563645571745016611L;
	private JList<String> list;
	private JLabel lblSeleccionaElTema;
	private JScrollPane scrollPane;
	private JButton btnComenzarExamen;

	/**
	 * Crea un nuevo panel de tests
	 */
	public Escuela()
	{
		Font fuenteLbl = new Font("Gran_label", Font.PLAIN|Font.BOLD, 25);

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

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 121, 0, 0, 5};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 0, 5};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblPreguntasAleatrias = new JLabel("Preguntas aleatórias");
		lblPreguntasAleatrias.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Vector<String> v = Pregunta.vectorTemas();
				if (v.size() > 1)
				{
					v.add("Todos los temas");
				}
				list.setListData(v);
				lblSeleccionaElTema.setVisible(true);
				list.setVisible(true);
				scrollPane.setVisible(true);

				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_lblPreguntasAleatrias = new GridBagConstraints();
		gbc_lblPreguntasAleatrias.gridwidth = 4;
		gbc_lblPreguntasAleatrias.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreguntasAleatrias.gridx = 1;
		gbc_lblPreguntasAleatrias.gridy = 1;
		lblPreguntasAleatrias.setFont(fuenteLbl);
		lblPreguntasAleatrias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(lblPreguntasAleatrias, gbc_lblPreguntasAleatrias);

		lblSeleccionaElTema = new JLabel("Selecciona el tema:");
		lblSeleccionaElTema.setVisible(false);
		GridBagConstraints gbc_lblSeleccionaElTema = new GridBagConstraints();
		gbc_lblSeleccionaElTema.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionaElTema.gridx = 6;
		gbc_lblSeleccionaElTema.gridy = 1;
		panel.add(lblSeleccionaElTema, gbc_lblSeleccionaElTema);

		JLabel lblPreguntasFalladas = new JLabel("Preguntas falladas");
		if (hayFalladas())
		{
			lblPreguntasFalladas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblPreguntasFalladas.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					ResultSet resultado = BD.getInstance().consulta("SELECT preguntas.id, tema, preguntas.pregunta, respuesta, correcta " +
									"FROM respuestas " +
										"JOIN " +
											"(SELECT id, tema, pregunta " +
												"FROM preguntas WHERE id IN (" +
													"SELECT pregunta FROM p_falladas WHERE usuario = " + Usuario.actual.getId() + ")" +
												"LIMIT 30) " +
											"AS preguntas " +
											"ON respuestas.pregunta = preguntas.id;");

					int pregunta_id = 0;
					Pregunta pregunta = null;
					String respuesta;
					ArrayList<Pregunta> preguntas = new ArrayList<>();

					try
					{
						while (resultado.next())
						{
							if (resultado.getInt("id") != pregunta_id)
							{
								pregunta_id = resultado.getInt("id");
								pregunta = new Pregunta(pregunta_id, resultado.getString("tema").equals("") ? null : resultado.getString("tema"), resultado.getString("pregunta"));
								preguntas.add(pregunta);
							}

							respuesta = resultado.getString("respuesta");
							pregunta.addRespuesta(respuesta);
							if (resultado.getInt("correcta") == 1)
							{
								pregunta.setCorrecta(respuesta);
							}
						}
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
						Ventana.getInstance().dispose();
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e1.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
					}

					Ventana.getInstance().setContentPane(new Test(new Examen("Preguntas Falladas", preguntas)));
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
			});
		}
		else
		{
			lblPreguntasFalladas.setForeground(new Color(80, 80, 80));
		}

		GridBagConstraints gbc_lblPreguntasFalladas = new GridBagConstraints();
		gbc_lblPreguntasFalladas.insets = new Insets(0, 0, 0, 5);
		gbc_lblPreguntasFalladas.gridwidth = 4;
		gbc_lblPreguntasFalladas.gridx = 1;
		gbc_lblPreguntasFalladas.gridy = 3;
		lblPreguntasFalladas.setFont(fuenteLbl);
		panel.add(lblPreguntasFalladas, gbc_lblPreguntasFalladas);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 6;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);

		list = new JList<>();
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				btnComenzarExamen.setVisible(true);
			}
		});
		list.setVisible(false);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setColumnHeaderView(list);

		btnComenzarExamen = new JButton("Comenzar examen");
		btnComenzarExamen.setVisible(false);
		btnComenzarExamen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (list.getSelectedValue() != null)
				{
					Ventana.getInstance().setContentPane(new Test(list.getSelectedValue()));
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
			}
		});
		GridBagConstraints gbc_btnComenzarExamen = new GridBagConstraints();
		gbc_btnComenzarExamen.insets = new Insets(0, 0, 0, 5);
		gbc_btnComenzarExamen.gridx = 6;
		gbc_btnComenzarExamen.gridy = 3;
		panel.add(btnComenzarExamen, gbc_btnComenzarExamen);
	}

	private boolean hayFalladas()
	{
		return BD.getInstance().contar("p_falladas", "usuario = " + Usuario.actual.getId()) > 0;
	}
}