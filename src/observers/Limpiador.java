package observers;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class Limpiador extends FocusAdapter {

	@Override
	public void focusGained(FocusEvent e)
	{
		((JTextField) e.getSource()).setBackground(Color.WHITE);
	}
}