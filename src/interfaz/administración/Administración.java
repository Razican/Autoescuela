package interfaz.administración;


import interfaz.Menú;
import interfaz.Ventana;
import interfaz.administración.estadísticas.MostrarEstadísticas;
import interfaz.administración.preguntas.EditarPregunta;
import interfaz.administración.preguntas.NuevaPregunta;
import interfaz.administración.usuarios.EditarUsuario;
import interfaz.administración.usuarios.NuevoUsuario;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel de administración
 * @author Razican (Iban Eguia)
 *
 */
public class Administración extends JPanel {

	private static final long serialVersionUID = 2150862764825794955L;

	/**
	 * Crea el panel de administración
	 */
	public Administración()
	{
		Font fuenteTítulo = new Font("Gran_label", Font.PLAIN|Font.BOLD, 25);

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
		gbc_panel.insets = new Insets(0, 0, 0, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 30, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblPanelDeAdministracin = new JLabel("Panel de Administración");
		GridBagConstraints gbc_lblPanelDeAdministracin = new GridBagConstraints();
		gbc_lblPanelDeAdministracin.gridwidth = 3;
		gbc_lblPanelDeAdministracin.insets = new Insets(0, 0, 5, 5);
		gbc_lblPanelDeAdministracin.gridx = 1;
		gbc_lblPanelDeAdministracin.gridy = 0;
		lblPanelDeAdministracin.setFont(fuenteTítulo);
		panel.add(lblPanelDeAdministracin, gbc_lblPanelDeAdministracin);

		JLabel lblNuevoUsuario = new JLabel("Nuevo Usuario");
		lblNuevoUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNuevoUsuario.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Ventana.getInstance().setContentPane(new NuevoUsuario());
				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_btnNuevoUsuario = new GridBagConstraints();
		gbc_btnNuevoUsuario.insets = new Insets(15, 15, 15, 15);
		gbc_btnNuevoUsuario.gridx = 1;
		gbc_btnNuevoUsuario.gridy = 2;
		panel.add(lblNuevoUsuario, gbc_btnNuevoUsuario);

		JLabel lblEditarUsuario = new JLabel("Editar Usuario");
		lblEditarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblEditarUsuario.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Ventana.getInstance().setContentPane(new EditarUsuario());
				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_btnEditarUsuario = new GridBagConstraints();
		gbc_btnEditarUsuario.insets = new Insets(15, 15, 15, 15);
		gbc_btnEditarUsuario.gridx = 3;
		gbc_btnEditarUsuario.gridy = 2;
		panel.add(lblEditarUsuario, gbc_btnEditarUsuario);

		JLabel lblNuevaPregunta = new JLabel("Nueva Pregunta");
		lblNuevaPregunta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNuevaPregunta.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Ventana.getInstance().setContentPane(new NuevaPregunta());
				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_btnNuevaPregunta = new GridBagConstraints();
		gbc_btnNuevaPregunta.insets = new Insets(15, 15, 15, 15);
		gbc_btnNuevaPregunta.gridx = 1;
		gbc_btnNuevaPregunta.gridy = 3;
		panel.add(lblNuevaPregunta, gbc_btnNuevaPregunta);

		JLabel lblEditarPregunta = new JLabel("Editar Pregunta");
		lblEditarPregunta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblEditarPregunta.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Ventana.getInstance().setContentPane(new EditarPregunta());
				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_btnEditarPregunta = new GridBagConstraints();
		gbc_btnEditarPregunta.insets = new Insets(15, 15, 15, 15);
		gbc_btnEditarPregunta.gridx = 3;
		gbc_btnEditarPregunta.gridy = 3;
		panel.add(lblEditarPregunta, gbc_btnEditarPregunta);

		JLabel lblEstadísticas = new JLabel("Estadísticas de los usuarios");
		lblEstadísticas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblEstadísticas.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Ventana.getInstance().setContentPane(new MostrarEstadísticas());
				Ventana.getInstance().validate();
				Ventana.getInstance().repaint();
			}
		});
		GridBagConstraints gbc_lblEstadsticasDeLos = new GridBagConstraints();
		gbc_lblEstadsticasDeLos.gridwidth = 3;
		gbc_lblEstadsticasDeLos.insets = new Insets(15, 15, 15, 15);
		gbc_lblEstadsticasDeLos.gridx = 1;
		gbc_lblEstadsticasDeLos.gridy = 4;
		panel.add(lblEstadísticas, gbc_lblEstadsticasDeLos);
	}
}