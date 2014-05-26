package excepciones;

/**
 * Excepción para cuando no haya base de datos
 * @author Razican (Iban Eguia)
 *
 */
public class NoDatabaseException extends Exception {

	private static final long serialVersionUID = 1265142422211981500L;

	/**
	 * Constructor por defecto. Usa como mensaje:
	 * 'No existe la base de datos'
	 */
	public NoDatabaseException()
	{
		super("No existe la base de datos");
	}

	/**
	 * @param message Mensaje de la excepción
	 */
	public NoDatabaseException(String message)
	{
		super(message);
	}

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args)
	{
		try
		{
			throw new NoDatabaseException("Ha ocurrido un error");
		}
		catch (NoDatabaseException e)
		{
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}

		try
		{
			throw new NoDatabaseException();
		}
		catch (NoDatabaseException e)
		{
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
}