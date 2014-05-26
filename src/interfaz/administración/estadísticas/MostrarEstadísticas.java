package interfaz.administración.estadísticas;

import interfaz.Menú;
import interfaz.Ventana;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import usuarios.Usuario;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class MostrarEstadísticas extends JPanel {

	private static final long serialVersionUID = 484685166120710875L;
	private Vector<Usuario> usuarios;
	private JList<Usuario> list;

	/**
	 * Create the panel.
	 */
	public MostrarEstadísticas()
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

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 100, 0, 5};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 5};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblSeleccionaUnUsuario = new JLabel("Selecciona un usuario:");
		GridBagConstraints gbc_lblSeleccionaUnUsuario = new GridBagConstraints();
		gbc_lblSeleccionaUnUsuario.gridwidth = 3;
		gbc_lblSeleccionaUnUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_lblSeleccionaUnUsuario.gridx = 0;
		gbc_lblSeleccionaUnUsuario.gridy = 0;
		panel.add(lblSeleccionaUnUsuario, gbc_lblSeleccionaUnUsuario);

		usuarios = Usuario.generarVector();
		list = new JList<>(usuarios);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		JButton btnMostrarEstadsticas = new JButton("Mostrar estadísticas");
		btnMostrarEstadsticas.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (list.getSelectedValue() != null)
				{
					Ventana.getInstance().setContentPane(new TablaEstadísticas(list.getSelectedValue()));
					Ventana.getInstance().validate();
					Ventana.getInstance().repaint();
				}
			}
		});
		GridBagConstraints gbc_btnMostrarEstadsticas = new GridBagConstraints();
		gbc_btnMostrarEstadsticas.insets = new Insets(0, 0, 0, 5);
		gbc_btnMostrarEstadsticas.gridx = 1;
		gbc_btnMostrarEstadsticas.gridy = 2;
		panel.add(btnMostrarEstadsticas, gbc_btnMostrarEstadsticas);
	}
}