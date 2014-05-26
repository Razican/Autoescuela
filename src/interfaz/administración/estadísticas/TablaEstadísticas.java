package interfaz.administración.estadísticas;

import interfaz.Menú;
import interfaz.Ventana;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JPanel;

import usuarios.Usuario;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import baseDeDatos.BD;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class TablaEstadísticas extends JPanel {

	private static final long serialVersionUID = -2104064942373788620L;
	private Usuario usuario;
	private JTable table;
	private Vector<Vector<String>> datos;

	/**
	 * Create the panel.
	 * @param usuario Usuario para el que mostrar las estadísticas
	 */
	public TablaEstadísticas(Usuario usuario)
	{
		this.usuario = usuario;

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
		gbl_panel.columnWidths = new int[] {0, 700, 0, 5};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 5};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblUsuario = new JLabel(this.usuario.toString());
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 0;
		panel.add(lblUsuario, gbc_lblUsuario);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		Vector<String> índices = new Vector<>(7);
		índices.add("Fecha");
		índices.add("Exámenes");
		índices.add("Aprobados");
		índices.add("Suspendidos");
		índices.add("Preguntas");
		índices.add("Acertadas");
		índices.add("Falladas");

		generarDatos();

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(new DefaultTableModel(datos, índices)
		{
			private static final long serialVersionUID = -7611354229994209778L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});
		scrollPane.setViewportView(table);
	}

	private void generarDatos()
	{
		datos = new Vector<>();
		Vector<String> fila = null;
		String fecha = null;
		Locale localización = new Locale("es", "ES");
		DateFormat formato = DateFormat.getDateInstance(DateFormat.SHORT, localización);
		Date últimaFecha = null, fechaActual;
		int exámenes, aprobados, suspendidos, preguntas, acertadas, falladas;
		exámenes = aprobados = suspendidos = preguntas = acertadas = falladas = 0;

		ResultSet consulta = BD.getInstance().consulta("SELECT * FROM estadísticas WHERE usuario = " + usuario.getId() + " ORDER BY fecha ASC;");

		try
		{
			while (consulta.next())
			{
				fechaActual = new Date(consulta.getInt("fecha")*1000L);
				if (últimaFecha == null || ! formato.format(fechaActual).equals(fecha))
				{
					if (fila != null)
					{
						fila.add(String.valueOf(exámenes));
						fila.add(String.valueOf(aprobados));
						fila.add(String.valueOf(suspendidos));
						fila.add(String.valueOf(preguntas));
						fila.add(String.valueOf(acertadas));
						fila.add(String.valueOf(falladas));
					}

					últimaFecha = fechaActual;
					fecha = formato.format(fechaActual);
					fila = new Vector<>(7);
					datos.add(fila);
					fila.add(formato.format(fechaActual));
					exámenes = aprobados = suspendidos = preguntas = acertadas = falladas = 0;
				}
				int p, a, máxFallos;
				exámenes++;
				preguntas += (p = consulta.getInt("totales"));
				acertadas += (a = consulta.getInt("acertadas"));
				falladas += consulta.getInt("falladas");

				máxFallos = (byte) Math.round(p/10D);
				boolean s = (p - a) > máxFallos;
				aprobados += s ? 0 : 1;
				suspendidos += s ? 1 : 0;
			}

			//Itroducimos la última fila
			if (fila != null)
			{
				fila.add(String.valueOf(exámenes));
				fila.add(String.valueOf(aprobados));
				fila.add(String.valueOf(suspendidos));
				fila.add(String.valueOf(preguntas));
				fila.add(String.valueOf(acertadas));
				fila.add(String.valueOf(falladas));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}
	}
}