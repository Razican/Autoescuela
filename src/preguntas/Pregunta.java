package preguntas;

import interfaz.Ventana;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import baseDeDatos.BD;

/**
 * Clase pregunta
 * @author Razican (Iban Eguia)
 *
 */
public class Pregunta {

	private int id;
	private String pregunta;
	private ImageIcon imagen;
	private List<String> respuestas;
	private String correcta;
	private String tema;

	/**
	 * @param id ID de la pregunta en la base de datos
	 * @param tema El tema de la pregunta
	 * @param pregunta Pregunta
	 * @param correcta El índice de la respuesta correcta
	 * @param respuesta El array con las respuestas posibles
	 */
	public Pregunta(int id, String tema, String pregunta, byte correcta, String... respuesta)
	{
		this.id			= id;
		this.pregunta	= pregunta;
		this.imagen		= new ImageIcon("img/preguntas/" + (tema == null ? "Sin tema" : tema).toLowerCase() + "/" + id + ".png");
		this.respuestas	= Arrays.asList(respuesta);
		this.correcta	= this.respuestas.get(correcta);
		this.tema		= tema;
	}

	/**
	 * @param id ID de la pregunta en la base de datos
	 * @param tema El tema de la pregunta
	 * @param pregunta Pregunta
	 */
	public Pregunta(int id, String tema, String pregunta)
	{
		this.id			= id;
		this.pregunta	= pregunta;
		this.imagen		= new ImageIcon("img/preguntas/" + (tema == null ? "Sin tema" : tema).toLowerCase() + "/" + id + ".png");
		this.respuestas	= new ArrayList<>();
		this.tema		= tema;
	}

	/**
	 * @return La pregunta
	 */
	public String getPregunta()
	{
		return pregunta;
	}

	/**
	 * @param pregunta La pregunta
	 */
	public void setPregunta(String pregunta)
	{
		this.pregunta = pregunta;
	}

	/**
	 * @return Un array de respuestas
	 */
	public String[] getRespuestas()
	{
		return (String[]) respuestas.toArray();
	}

	/**
	 * @param respuestas El nuevo array de respuestas
	 */
	public void setRespuestas(String[] respuestas)
	{
		this.respuestas = Arrays.asList(respuestas);
	}

	/**
	 * @param num Índice de la respuesta
	 * @return La respuesta
	 */
	public String getRespuesta(int num)
	{
		return respuestas.get(num);
	}

	/**
	 * @param respuesta La nueva respuesta
	 * @param num Índice de la respuesta
	 */
	public void setRespuesta(String respuesta, int num)
	{
		this.respuestas.set(num, respuesta);
	}

	/**
	 * @param respuesta La nueva respuesta
	 * @param num Índice de la respuesta
	 */
	public void addRespuesta(String respuesta, int num)
	{
		this.respuestas.add(num,respuesta);
	}

	/**
	 * @param respuesta La nueva respuesta
	 */
	public void addRespuesta(String respuesta)
	{
		this.respuestas.add(respuesta);
	}

	/**
	 * @return El índice de la respuesta correcta
	 */
	public byte getCorrecta()
	{
		return (byte) this.respuestas.indexOf(correcta);
	}

	/**
	 * @param correcta El nuevo índice de la respuesta correcta
	 */
	public void setCorrecta(byte correcta)
	{
		this.correcta = this.respuestas.get(correcta);
	}

	/**
	 * @param correcta La nueva respuesta correcta
	 */
	public void setCorrecta(String correcta)
	{
		this.correcta = correcta;
	}

	/**
	 * @return El tema de la pregunta
	 */
	public String getTema()
	{
		return tema;
	}

	/**
	 * @param tema El nuevo tema de la pregunta
	 */
	public void setTema(String tema)
	{
		this.tema = tema;
	}

	/**
	 * @return La ID de la pregunta
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * @return La imagen de la pregunta
	 */
	public ImageIcon getImagen()
	{
		return imagen;
	}

	/**
	 * @param imagen La nueva imagen de la pregunta
	 */
	public void setImagen(ImageIcon imagen)
	{
		this.imagen = imagen;
	}

	/**
	 * @return Número de respuestas
	 */
	public int getNumRespuestas()
	{
		return respuestas.size();
	}

	/**
	 * Reordena todas las respuestas de la pregunta
	 */
	public void reordenarRespuestas()
	{
		Collections.shuffle(this.respuestas);
	}

	/**
	 * @return ¿Tiene la pregunta imagen?
	 */
	public boolean tieneImagen()
	{
		File archivo = new File("img/preguntas/" + (tema == null ? "Sin tema" : tema).toLowerCase() + "/" + id + ".png");
		return archivo.exists();
	}

	@Override
	public String toString()
	{
		int máx = pregunta.length() > 50 ? 50 : pregunta.length();
		String more = pregunta.length() > 50 ? "..." : "";
		return pregunta.substring(0, máx) + more;
	}

	/**
	 * @return Vector con todos los temas de las preguntas.
	 */
	public static Vector<String> vectorTemas()
	{
		ResultSet resultado = BD.getInstance().consulta(
					"SELECT DISTINCT ifnull(tema, 'Sin tema') as tema " +
						"FROM preguntas " +
						"ORDER BY CASE " +
							"WHEN tema='Sin tema' THEN 1 " +
							"ELSE 0 " +
						"END, tema;");
		Vector<String> v = new Vector<>();

		try
		{
			while (resultado.next())
			{
				v.add(resultado.getString("tema"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}

		return v;
	}

	/**
	 * @param tema Tema de las preguntas
	 * @return Vector de las preguntas
	 */
	public static Vector<Pregunta> getPreguntasPorTema(String tema)
	{
		String where = tema.equals("Todos los temas") ? "" : tema.equals("Sin tema") ? "WHERE tema IS NULL " : "WHERE tema='" + tema +"' ";
		String consulta = "SELECT preguntas.id, tema, preguntas.pregunta, respuesta, correcta " +
								"FROM respuestas " +
									"JOIN " +
										"(SELECT id, tema, pregunta " +
											"FROM preguntas " + where +
											"LIMIT 30) " +
										"AS preguntas " +
										"ON respuestas.pregunta = preguntas.id;";

		ResultSet resultado = BD.getInstance().consulta(consulta);

		int pregunta_id = 0;
		Pregunta pregunta = null;
		String respuesta;
		Vector<Pregunta> preguntas = new Vector<>();

		try
		{
			while (resultado.next())
			{
				if (resultado.getInt("id") != pregunta_id)
				{
					pregunta_id = resultado.getInt("id");
					pregunta = new Pregunta(pregunta_id, tema.equals("Sin tema") ? null : resultado.getString("tema"), resultado.getString("pregunta"));
					preguntas.add(pregunta);
				}

				respuesta = resultado.getString("respuesta");
				pregunta.addRespuesta(respuesta);
				if (resultado.getInt("correcta") == 1)
				{
					pregunta.setCorrecta(respuesta);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}

		return preguntas;
	}
}