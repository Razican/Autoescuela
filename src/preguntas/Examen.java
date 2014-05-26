package preguntas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import excepciones.ExamConfigException;

/**
 * Clase examen
 * @author Razican (Iban Eguia)
 *
 */
public class Examen {

	private List<Pregunta> preguntas;
	private String tema;
	private String nombre;

	/**
	 * Crea un examen por defecto
	 */
	public Examen()
	{
		setPreguntas(new Pregunta[30]);
	}

	/**
	 * @param nombre Nombre del examen
	 * @param preguntas Array de preguntas del examen
	 */
	public Examen(String nombre, Pregunta...preguntas)
	{
		this(nombre, Arrays.asList(preguntas));
	}

	/**
	 * @param nombre Nombre del examen
	 * @param preguntas Array de preguntas del examen
	 */
	public Examen(String nombre, List<Pregunta> preguntas)
	{
		this.preguntas = preguntas;
		this.nombre = nombre;
	}

	/**
	 * @param nombre Nombre del examen
	 * @param tema Tema del examen
	 * @param preguntas Array de preguntas
	 * @throws ExamConfigException Si las preguntas del examen no son del tema especificado
	 */
	public Examen(String nombre, String tema, Pregunta...preguntas) throws ExamConfigException
	{
		this(nombre, tema, Arrays.asList(preguntas));
	}

	/**
	 * @param nombre Nombre del examen
	 * @param tema Tema del examen
	 * @param preguntas Array de preguntas
	 * @throws ExamConfigException Si las preguntas del examen no son del tema especificado
	 */
	public Examen(String nombre, String tema, List<Pregunta> preguntas) throws ExamConfigException
	{
		for (Pregunta p : preguntas)
		{
			if ( ! p.getTema().equals(tema))
			{
				throw new ExamConfigException("Las preguntas del examen no son todas del tema especificado");
			}
		}

		this.preguntas = preguntas;
		this.tema	= tema;
		this.nombre = nombre;
	}

	/**
	 * @return Tema del examen
	 */
	public String getTema()
	{
		return tema == null ? "Todos los temas" : tema;
	}

	/**
	 * @param tema Nuevo tema del examen
	 * @throws ExamConfigException Si el tema no coincide con el de las preguntas del examen
	 */
	public void setTema(String tema) throws ExamConfigException
	{
		for (Pregunta p : preguntas)
		{
			if ( ! p.getTema().equals(tema))
			{
				throw new ExamConfigException("Las preguntas del examen no son todas del tema especificado");
			}
		}

		this.tema = tema;
	}

	/**
	 * @return Array de preguntas
	 */
	public Pregunta[] getPreguntas()
	{
		return (Pregunta[]) preguntas.toArray();
	}

	/**
	 * @param preguntas El array de preguntas nuevo
	 */
	public void setPreguntas(Pregunta[] preguntas)
	{
		this.preguntas = Arrays.asList(preguntas);
	}

	/**
	 * Obtiene una pregunta por su índice
	 * @param num Número de la pregunta
	 * @return La pregunta con ese índice
	 */
	public Pregunta getPregunta(int num)
	{
		return preguntas.get(num);
	}

	/**
	 * Introduce una nueva pregunta
	 * @param p Pregunta
	 * @param num Índice de la nueva pregunta
	 */
	public void setPregunta(Pregunta p, int num)
	{
		preguntas.set(num, p);
	}

	/**
	 * Reordena las preguntas del examen aleatoriamente
	 */
	public void reordenarPreguntas()
	{
		Collections.shuffle(this.preguntas);
	}

	/**
	 * Reordena las respuestas de todas las preguntas del examen aleatoriamente
	 */
	public void reordenarRespuestas()
	{
		Iterator<Pregunta> iterador = this.preguntas.iterator();
		while (iterador.hasNext())
		{
			iterador.next().reordenarRespuestas();
		}
	}

	/**
	 * @return El nombre del examen
	 */
	public String getNombre()
	{
		return nombre;
	}

	/**
	 * @param nombre Nuevo nombre para el examen
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 * @return Cantidad de preguntas
	 */
	public int getNumPreguntas()
	{
		return preguntas.size();
	}
}