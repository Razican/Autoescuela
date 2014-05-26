package excepciones;

/**
 * Excepción en la configuración de un examen
 * @author Razican (Iban Eguia)
 *
 */
public class ExamConfigException extends Exception {

	private static final long serialVersionUID = -4427905941871030276L;

	/**
	 * Constructor por defecto. Usa como mensaje:
	 * 'Ha ocurrido un error en el examen'
	 */
	public ExamConfigException()
	{
		super("Ha ocurrido un error en el examen");
	}

	/**
	 * @param message Mensaje de la excepción
	 */
	public ExamConfigException(String message)
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
			throw new ExamConfigException("Ha ocurrido un error");
		}
		catch (ExamConfigException e)
		{
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}

		try
		{
			throw new ExamConfigException();
		}
		catch (ExamConfigException e)
		{
			System.err.println(e.getMessage());
			System.err.println();
			e.printStackTrace();
		}
	}
}