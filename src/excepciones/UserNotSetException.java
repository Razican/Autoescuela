package excepciones;

/**
 * Excepción de usuario no configurado
 * @author Razican (Iban Eguia)
 *
 */
public class UserNotSetException extends Exception {

	private static final long serialVersionUID = -5710286959979495718L;

	/**
	 * Constructor por defecto. Usa como mensaje:
	 * 'No se han suministrado los parámetros del usuario'
	 */
	public UserNotSetException()
	{
		super("No se han suministrado los parámetros del usuario");
	}

	/**
	 * @param message Mensaje de la excepción
	 */
	public UserNotSetException(String message)
	{
		super(message);
	}

	/**
	 * @param dni DNI del usuario
	 */
	public UserNotSetException(int dni)
	{
		super("No existe el usuario con DNI " + dni);
	}

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args)
	{
		try
		{
			throw new UserNotSetException("Ha ocurrido un error");
		}
		catch (UserNotSetException e)
		{
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}

		try
		{
			throw new UserNotSetException();
		}
		catch (UserNotSetException e)
		{
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
}