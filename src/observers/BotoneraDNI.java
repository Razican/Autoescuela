package observers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * Observer para la botonera del DNI del acceso
 * @author Razican (Iban Eguia)
 *
 */
public class BotoneraDNI implements ActionListener {

	private JTextField f;

	/**
	 * Crear el observer
	 * @param f TextField donde se escribirá con la botonera
	 */
	public BotoneraDNI(JTextField f)
	{
		this.f = f;
	}

	public void actionPerformed(ActionEvent e)
	{
		this.f.setBackground(Color.WHITE);
		if (this.f.getText().length() < 8)
		{
			this.f.setText(this.f.getText() + ((JButton) e.getSource()).getText());
		}
	}
}