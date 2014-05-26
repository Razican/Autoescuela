package interfaz.administración.preguntas;

import interfaz.Menú;
import interfaz.Ventana;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import preguntas.Pregunta;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class EditarPregunta extends JPanel {

	private static final long serialVersionUID = 1948209010132569501L;
	private JPanel panel;
	private JList<Pregunta> listaPreguntas;
	private JList<String> listaTemas;
	private JLabel lblImagen;

	/**
	 * Create the panel.
	 */
	public EditarPregunta()
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
		gbl_panel.columnWidths = new int[]{125, 450, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 200, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblSeleccionarTema = new JLabel("Seleccionar tema:");
		GridBagConstraints gbc_lblSeleccionarTema = new GridBagConstraints();
		gbc_lblSeleccionarTema.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarTema.gridx = 0;
		gbc_lblSeleccionarTema.gridy = 0;
		panel.add(lblSeleccionarTema, gbc_lblSeleccionarTema);

		JLabel lblSeleccionarPregunta = new JLabel("Seleccionar pregunta:");
		GridBagConstraints gbc_lblSeleccionarPregunta = new GridBagConstraints();
		gbc_lblSeleccionarPregunta.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarPregunta.gridx = 1;
		gbc_lblSeleccionarPregunta.gridy = 0;
		panel.add(lblSeleccionarPregunta, gbc_lblSeleccionarPregunta);

		JScrollPane scrollTema = new JScrollPane();
		GridBagConstraints gbc_scrollTema = new GridBagConstraints();
		gbc_scrollTema.gridheight = 2;
		gbc_scrollTema.insets = new Insets(0, 0, 5, 5);
		gbc_scrollTema.fill = GridBagConstraints.BOTH;
		gbc_scrollTema.gridx = 0;
		gbc_scrollTema.gridy = 1;
		panel.add(scrollTema, gbc_scrollTema);

		Vector<String> v = Pregunta.vectorTemas();
		if (v.size() > 1)
		{
			v.add("Todos los temas");
		}
		listaTemas = new JList<>(v);
		listaTemas.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				listaPreguntas.setListData(Pregunta.getPreguntasPorTema(listaTemas.getSelectedValue()));
			}
		});
		scrollTema.setViewportView(listaTemas);

		lblImagen = new JLabel();
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 1;
		gbc_lblImagen.gridy = 1;
		panel.add(lblImagen, gbc_lblImagen);

		JScrollPane scrollPregunta = new JScrollPane();
		scrollPregunta.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPregunta = new GridBagConstraints();
		gbc_scrollPregunta.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPregunta.fill = GridBagConstraints.BOTH;
		gbc_scrollPregunta.gridx = 1;
		gbc_scrollPregunta.gridy = 2;
		panel.add(scrollPregunta, gbc_scrollPregunta);

		listaPreguntas = new JList<>();
		listaPreguntas.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (listaPreguntas.getSelectedValue() != null)
				{
					if (listaPreguntas.getSelectedValue().tieneImagen())
					{
						lblImagen.setIcon(listaPreguntas.getSelectedValue().getImagen());
					}
				}
				else
				{
					lblImagen.setIcon(null);
				}
			}
		});
		scrollPregunta.setViewportView(listaPreguntas);

		JButton btnEditarPregunta = new JButton("Editar pregunta");
		btnEditarPregunta.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (listaPreguntas.getSelectedValue() != null)
				{
					Ventana.getInstance().setContentPane(new PanelEdición(listaPreguntas.getSelectedValue()));
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
			}
		});
		GridBagConstraints gbc_btnEditarPregunta = new GridBagConstraints();
		gbc_btnEditarPregunta.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditarPregunta.gridx = 1;
		gbc_btnEditarPregunta.gridy = 3;
		panel.add(btnEditarPregunta, gbc_btnEditarPregunta);
	}
}