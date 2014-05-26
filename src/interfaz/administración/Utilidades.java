package interfaz.administración;

import baseDeDatos.BD;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class Utilidades {

	/**
	 * @param dni DNI del nuevo usuario
	 * @return Si ya existe el usuario o no
	 */
	public static boolean existeUsuario(String dni)
	{
		return BD.getInstance().contar("usuarios", "dni='"+dni+"'") == 1;
	}
}