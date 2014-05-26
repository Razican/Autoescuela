package interfaz;

import interfaz.externo.Acceso;
import interfaz.externo.Instalación;

import baseDeDatos.BD;

/**
 * Clase principal del proyecto, el programa se ejecutará desde aquí.
 * La versión de esta entrega es una mera prueba de concepto, y todavía
 * no se ha incidido mucho en su lógica y funcionalidad. Se comprueba
 * la creación de exámenes, preguntas, y se comprueba el buen
 * funcionamiento de la lógica pregunta-respuesta. Se debe responder
 * el número de respuesta correcta.
 *
 * @author Razican (Iban Eguia)
 */
public class Principal {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args)
	{
		if (hay_profesores())
		{
			acceder();
		}
		else
		{
			instalar();
		}
	}

	/**
	 * @return ¿Hay profesores?
	 */
	private static boolean hay_profesores()
	{
		return BD.getInstance().contar("usuarios", "tipo=1") > 0;
	}

	private static void acceder()
	{
		Ventana.getInstance().setContentPane(new Acceso());
		Ventana.getInstance().setVisible(true);
	}

	private static void instalar()
	{
		Ventana.getInstance().setContentPane(new Instalación());
		Ventana.getInstance().setVisible(true);
	}
}