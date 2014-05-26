package interfaz.tests;

import interfaz.Menú;
import interfaz.Ventana;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import excepciones.ExamConfigException;

import baseDeDatos.BD;

import preguntas.Examen;
import preguntas.Pregunta;
import usuarios.Usuario;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Generar panel de examen
 * @author Razican (Iban Eguia)
 *
 */
public class Test extends JPanel {

	private static final long serialVersionUID = 3879081923158757134L;
	private Examen examen;
	private int índicePreActual;
	private Pregunta preguntaActual;
	private JPanel panel;
	private byte[] respuestas;

	/**
	 * Create the panel.
	 * @param tema Tema del examen
	 * @wbp.parser.constructor
	 */
	public Test(String tema)
	{
		try
		{
			if (tema.equals("Todos los temas") || tema.equals("Sin tema"))
			{
				examen = new Examen("Examen aleatorio", Pregunta.getPreguntasPorTema(tema));
			}
			else
			{
				examen = new Examen("Examen aleatorio", tema, Pregunta.getPreguntasPorTema(tema));
			}
		}
		catch (ExamConfigException e){}

		comenzar_examen();
	}

	/**
	 * @param examen Examen con el que comenzar el test
	 */
	public Test(Examen examen)
	{
		this.examen = examen;

		comenzar_examen();
	}

	private void comenzar_examen()
	{
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
		gbl_panel.columnWidths = new int[] {0, 0, 10, 150, 0, 5};
		gbl_panel.rowHeights = new int[] {0, 0, 30, 30, 25, 5};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 100.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 100.0, 1.0, 0.0, 100.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		examen.reordenarPreguntas();
		examen.reordenarRespuestas();

		preguntaActual = examen.getPregunta(0);
		índicePreActual = 0;
		respuestas = new byte[examen.getNumPreguntas()];
		mostrarPregunta();
	}

	private void crearMenú()
	{
		JPanel información = new JPanel();
		información.setBackground(new Color(230, 230, 100));
		GridBagConstraints gbc_información = new GridBagConstraints();
		gbc_información.gridwidth = 5;
		gbc_información.insets = new Insets(0, 0, 5, 5);
		gbc_información.fill = GridBagConstraints.VERTICAL;
		gbc_información.gridx = 0;
		gbc_información.gridy = 0;
		panel.add(información, gbc_información);
		información.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

		JLabel lblExamen = new JLabel("Examen: " + examen.getNombre());
		información.add(lblExamen);

		JLabel lblTema = new JLabel("Tema:" + examen.getTema());
		información.add(lblTema);

		JButton btnCorregirExamen = new JButton("Corregir examen");
		btnCorregirExamen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Ventana.getInstance().setContentPane(new CorregirExamen(examen, respuestas));
				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		btnCorregirExamen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		información.add(btnCorregirExamen);
	}

	private void mostrarPregunta()
	{
		crearMenú();

		Color c;
		if (respuestas[índicePreActual] != 0 && preguntaActual.getCorrecta()+1 == respuestas[índicePreActual])
		{
			c = new Color(0, 165, 0);
		}
		else if (respuestas[índicePreActual] != 0 && preguntaActual.getCorrecta()+1 != respuestas[índicePreActual])
		{
			c = new Color(165, 0, 0);
		}
		else
		{
			c = new Color(0, 0, 0);
		}

		if (índicePreActual != 0)
		{
			JLabel lblAtras = new JLabel(new ImageIcon("img/anterior.png"));
			lblAtras.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					panel.removeAll();
					índicePreActual -= 1;
					preguntaActual = examen.getPregunta(índicePreActual);
					mostrarPregunta();
					panel.updateUI();
				}
			});
			lblAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			GridBagConstraints gbc_lblAtras = new GridBagConstraints();
			gbc_lblAtras.gridheight = 4;
			gbc_lblAtras.insets = new Insets(0, 0, 0, 5);
			gbc_lblAtras.gridx = 0;
			gbc_lblAtras.gridy = 1;
			panel.add(lblAtras, gbc_lblAtras);
		}

		if (preguntaActual.tieneImagen())
		{
			JLabel lblImagen = new JLabel(preguntaActual.getImagen());
			GridBagConstraints gbc_lblImagen = new GridBagConstraints();
			gbc_lblImagen.gridheight = 4;
			gbc_lblImagen.insets = new Insets(0, 0, 0, 5);
			gbc_lblImagen.gridx = 1;
			gbc_lblImagen.gridy = 1;
			panel.add(lblImagen, gbc_lblImagen);
		}

		JLabel lblPregunta = new JLabel("<html>" + preguntaActual.getPregunta() + "</html>");
		lblPregunta.setMinimumSize(new Dimension(350, 75));
		lblPregunta.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		GridBagConstraints gbc_lblPregunta = new GridBagConstraints();
		gbc_lblPregunta.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPregunta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPregunta.gridx = 3;
		gbc_lblPregunta.gridy = 1;
		panel.add(lblPregunta, gbc_lblPregunta);

		if (índicePreActual != examen.getNumPreguntas()-1)
		{
			JLabel lblAdelante = new JLabel(new ImageIcon("img/siguiente.png"));
			lblAdelante.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					panel.removeAll();
					índicePreActual += 1;
					preguntaActual = examen.getPregunta(índicePreActual);
					mostrarPregunta();
					panel.updateUI();
				}
			});
			lblAdelante.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			GridBagConstraints gbc_lblAdelante = new GridBagConstraints();
			gbc_lblAdelante.gridheight = 4;
			gbc_lblAdelante.insets = new Insets(0, 0, 5, 0);
			gbc_lblAdelante.gridx = 4;
			gbc_lblAdelante.gridy = 1;
			panel.add(lblAdelante, gbc_lblAdelante);
		}

		JLabel lblRespuesta = new JLabel("<html>a) " + preguntaActual.getRespuesta(0) + "</html>");
		lblRespuesta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (respuestas[índicePreActual] == 0)
				{
					respuestas[índicePreActual] = 1;
					if (preguntaActual.getCorrecta() == 0)
					{
						((JLabel) e.getSource()).setForeground(new Color(0, 165, 0));
						BD.getInstance().actualizar("DELETE FROM p_falladas WHERE pregunta = " + preguntaActual.getId() + " AND usuario = " + Usuario.actual.getId() + ";");
					}
					else
					{
						((JLabel) e.getSource()).setForeground(new Color(165, 0, 0));
						BD.getInstance().actualizar("INSERT INTO p_falladas VALUES (" + preguntaActual.getId() + ", " + Usuario.actual.getId() + ");");
					}
				}
			}
		});
		if (respuestas[índicePreActual] == 1)
		{
			lblRespuesta.setForeground(c);
		}
		lblRespuesta.setMinimumSize(new Dimension(350, 50));
		lblRespuesta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblRespuesta = new GridBagConstraints();
		gbc_lblRespuesta.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRespuesta.anchor = GridBagConstraints.SOUTH;
		gbc_lblRespuesta.insets = new Insets(0, 0, 5, 5);
		gbc_lblRespuesta.gridx = 3;
		gbc_lblRespuesta.gridy = 2;
		panel.add(lblRespuesta, gbc_lblRespuesta);

		JLabel lblRespuesta_1 = new JLabel("<html>b) " + preguntaActual.getRespuesta(1) + "</html>");
		lblRespuesta_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (respuestas[índicePreActual] == 0)
				{
					respuestas[índicePreActual] = 2;
					if (preguntaActual.getCorrecta() == 1)
					{
						((JLabel) e.getSource()).setForeground(new Color(0, 165, 0));
						BD.getInstance().actualizar("DELETE FROM p_falladas WHERE pregunta = " + preguntaActual.getId() + " AND usuario = " + Usuario.actual.getId() + ";");
					}
					else
					{
						((JLabel) e.getSource()).setForeground(new Color(165, 0, 0));
						BD.getInstance().actualizar("INSERT INTO p_falladas VALUES (" + preguntaActual.getId() + ", " + Usuario.actual.getId() + ");");
					}
				}
			}
		});
		if (respuestas[índicePreActual] == 2)
		{
			lblRespuesta_1.setForeground(c);
		}
		lblRespuesta_1.setMinimumSize(new Dimension(350, 50));
		lblRespuesta_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblRespuesta_1 = new GridBagConstraints();
		gbc_lblRespuesta_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRespuesta_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRespuesta_1.gridx = 3;
		gbc_lblRespuesta_1.gridy = 3;
		panel.add(lblRespuesta_1, gbc_lblRespuesta_1);

		if (preguntaActual.getNumRespuestas() == 3)
		{
			JLabel lblRespuesta_2 = new JLabel("<html>c) " + preguntaActual.getRespuesta(2) + "</html>");
			lblRespuesta_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (respuestas[índicePreActual] == 0)
					{
						respuestas[índicePreActual] = 3;
						if (preguntaActual.getCorrecta() == 2)
						{
							((JLabel) e.getSource()).setForeground(new Color(0, 165, 0));
							BD.getInstance().actualizar("DELETE FROM p_falladas WHERE pregunta = " + preguntaActual.getId() + " AND usuario = " + Usuario.actual.getId() + ";");
						}
						else
						{
							((JLabel) e.getSource()).setForeground(new Color(165, 0, 0));
							BD.getInstance().actualizar("INSERT INTO p_falladas VALUES (" + preguntaActual.getId() + ", " + Usuario.actual.getId() + ");");
						}
					}
				}
			});
			if (respuestas[índicePreActual] == 3)
			{
				lblRespuesta_2.setForeground(c);
			}
			lblRespuesta_2.setMinimumSize(new Dimension(350, 50));
			lblRespuesta_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			GridBagConstraints gbc_lblRespuesta_2 = new GridBagConstraints();
			gbc_lblRespuesta_2.insets = new Insets(0, 0, 0, 5);
			gbc_lblRespuesta_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblRespuesta_2.anchor = GridBagConstraints.NORTH;
			gbc_lblRespuesta_2.gridx = 3;
			gbc_lblRespuesta_2.gridy = 4;
			panel.add(lblRespuesta_2, gbc_lblRespuesta_2);
		}
	}
}