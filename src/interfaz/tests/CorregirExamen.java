package interfaz.tests;

import interfaz.Menú;

import javax.swing.JLabel;
import javax.swing.JPanel;

import baseDeDatos.BD;

import preguntas.Examen;
import preguntas.Pregunta;
import usuarios.Usuario;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class CorregirExamen extends JPanel {

	private static final long serialVersionUID = -7019753657697799373L;
	private Examen examen;
	private byte[] respuestas;
	private JPanel panel;
	private int no_respondidas;
	private int falladas;
	private int correctas;

	/**
	 * @param examen Examen que corregir
	 * @param respuestas Respuestas obtenidas
	 */
	public CorregirExamen(Examen examen, byte[] respuestas)
	{
		this.examen = examen;
		this.respuestas = respuestas;

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
		gbl_panel.columnWidths = new int[] {0, 0, 0, 5};
		gbl_panel.rowHeights = new int[] {0, 33, 0, 0, 0, 25, 5};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 100.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		mostrarResultados();
		guardarResultados();
	}

	private void mostrarResultados()
	{
		no_respondidas = falladas = correctas = 0;
		for (int i = 0; i < respuestas.length; i++)
		{
			Pregunta p = this.examen.getPregunta(i);
			if (respuestas[i] == 0)
			{
				no_respondidas++;
			}
			else if (respuestas[i]-1 != p.getCorrecta())
			{
				falladas++;
			}
			else if (respuestas[i]-1 == p.getCorrecta())
			{
				correctas++;
			}
		}

		byte máxFallos = (byte) Math.round(respuestas.length/10D);

		JPanel información = new JPanel();
		información.setBackground(new Color(230, 230, 100));
		GridBagConstraints gbc_información = new GridBagConstraints();
		gbc_información.insets = new Insets(0, 0, 5, 5);
		gbc_información.fill = GridBagConstraints.VERTICAL;
		gbc_información.gridx = 1;
		gbc_información.gridy = 0;
		panel.add(información, gbc_información);
		información.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

		JLabel lblExamen = new JLabel("Examen: " + examen.getNombre());
		información.add(lblExamen);

		JLabel lblTema = new JLabel("Tema:" + examen.getTema());
		información.add(lblTema);

		JLabel lblAprobado = new JLabel();
		if (falladas + no_respondidas > máxFallos)
		{
			lblAprobado.setText("Has suspendido el examen");
			lblAprobado.setForeground(new Color(165, 0, 0));
		}
		else
		{
			lblAprobado.setText("Has aprobado el examen");
			lblAprobado.setForeground(new Color(0, 165, 0));
		}
		GridBagConstraints gbc_lblAprobado = new GridBagConstraints();
		gbc_lblAprobado.insets = new Insets(0, 0, 5, 5);
		gbc_lblAprobado.gridx = 1;
		gbc_lblAprobado.gridy = 1;
		panel.add(lblAprobado, gbc_lblAprobado);

		JLabel lblFallos = new JLabel("Fallos: " + falladas);
		GridBagConstraints gbc_lblFallos = new GridBagConstraints();
		gbc_lblFallos.insets = new Insets(0, 0, 5, 5);
		gbc_lblFallos.gridx = 1;
		gbc_lblFallos.gridy = 2;
		panel.add(lblFallos, gbc_lblFallos);

		JLabel lblAciertos = new JLabel("Aciertos: " + correctas);
		GridBagConstraints gbc_lblAciertos = new GridBagConstraints();
		gbc_lblAciertos.insets = new Insets(0, 0, 5, 5);
		gbc_lblAciertos.gridx = 1;
		gbc_lblAciertos.gridy = 3;
		panel.add(lblAciertos, gbc_lblAciertos);

		JLabel lblNoContestadas = new JLabel("No contestadas: " + no_respondidas);
		GridBagConstraints gbc_lblNoContestadas = new GridBagConstraints();
		gbc_lblNoContestadas.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoContestadas.gridx = 1;
		gbc_lblNoContestadas.gridy = 4;
		panel.add(lblNoContestadas, gbc_lblNoContestadas);
	}

	private void guardarResultados()
	{
		BD.getInstance().actualizar("INSERT INTO estadísticas (usuario, totales, acertadas, falladas) VALUES (" +
			Usuario.actual.getId() + ", " + respuestas.length + ", " + correctas + ", " +
			falladas + ")");
	}
}