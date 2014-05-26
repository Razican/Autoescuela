package interfaz.externo;

import interfaz.Ventana;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JProgressBar;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class Creador extends JPanel {

	private static final long serialVersionUID = 4777816720271504410L;
	private JProgressBar progressBar;

	/**
	 * Create the panel.
	 * @param númeroConsultas Número de consultas totales
	 * @param v Ventana actual
	 */
	public Creador(int númeroConsultas, Ventana v)
	{
		v.setBounds(0, 0, 500, 100);
		v.setMinimumSize(new Dimension(500, 100));
		v.setSize(500, 100);
		v.setUndecorated(true);
		v.setResizable(false);
		v.setLocationRelativeTo(null);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500, 0};
		gridBagLayout.rowHeights = new int[]{0, 75, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblInstalando = new JLabel("Instalando");
		GridBagConstraints gbc_lblInstalando = new GridBagConstraints();
		gbc_lblInstalando.insets = new Insets(0, 0, 5, 0);
		gbc_lblInstalando.gridx = 0;
		gbc_lblInstalando.gridy = 0;
		add(lblInstalando, gbc_lblInstalando);

		progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, númeroConsultas);
		progressBar.setPreferredSize(new Dimension(500, 75));
		progressBar.setMinimumSize(new Dimension(500, 75));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		add(progressBar, gbc_progressBar);
	}

	/**
	 * @return Barra de progreso
	 */
	public JProgressBar getProgressBar()
	{
		return progressBar;
	}

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args)
	{
		Ventana v = new Ventana(false);
		v.setContentPane(new Creador(100, v));
		v.setVisible(true);
		JProgressBar pb = ((Creador) v.getContentPane()).getProgressBar();

		int a = 0;
		while (a <= 100)
		{
			pb.setValue(a);
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			a++;
		}

		v.dispose();
	}
}