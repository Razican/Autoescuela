package usuarios;

import interfaz.Ventana;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import preguntas.Pregunta;

import baseDeDatos.BD;

import excepciones.UserNotSetException;

/**
 * @author Razican (Iban Eguia)
 *
 */
public class Usuario {

	/**
	 * Byte que representa al profesor
	 */
	public static final byte PROFESOR = 1;
	/**
	 * Byte que representa al alumno
	 */
	public static final byte ALUMNO = 0;

	/**
	 * Usuario actual
	 */
	public static Usuario actual;

	private int id;
	private String nombre;
	private String apellido;
	private int dni;
	private Date f_registro;
	private List<Pregunta> p_falladas;
	private boolean cambiado;
	private byte tipo;

	/**
	 * @param id ID del usuario
	 * @param dni DNI del usuario
	 * @param nombre Nombre del usuario
	 * @param apellido Apellido del usuario
	 * @param f_registro Fecha de registro del usuario
	 * @param tipo Tipo de usuario
	 */
	public Usuario(int id, int dni, String nombre, String apellido, Date f_registro, byte tipo)
	{
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.f_registro = f_registro;
		this.tipo = tipo;

		this.consultar_falladas();
	}

	/**
	 * @param dni DNI del usuario
	 * @throws UserNotSetException Si no existe ningún usuario con ese DNI
	 */
	public Usuario(int dni) throws UserNotSetException
	{
		this.id = 0;
		ResultSet resultado = BD.getInstance().consulta("SELECT * FROM usuarios WHERE dni="+dni);
		try
		{
			while (resultado.next())
			{
				this.id = resultado.getInt("id");
				this.nombre = resultado.getString("nombre");
				this.apellido = resultado.getString("apellido");
				this.dni = resultado.getInt("dni");
				this.f_registro = new Date(resultado.getInt("f_registro"));
				this.tipo = (byte) resultado.getInt("tipo");

				this.consultar_falladas();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}

		if (this.id == 0)
		{
			throw new UserNotSetException(dni);
		}
	}

	/**
	 * Consulta las preguntas falladas del usuario
	 */
	private void consultar_falladas()
	{
		ResultSet resultado = BD.getInstance().consulta(
			"SELECT p.id, p.pregunta, p.tema, r.respuesta, r.correcta " +
				"FROM preguntas AS p " +
					"JOIN respuestas AS r " +
						"ON p.id = r.pregunta " +
					"JOIN p_falladas AS f " +
						"ON p.id = f.pregunta " +
				"WHERE f.usuario = " + this.id + ";");

		int pregunta_id = 0;
		Pregunta pregunta = null;
		String respuesta;
		this.p_falladas = new ArrayList<>();

		try
		{
			while (resultado.next())
			{
				if (resultado.getInt("id") != pregunta_id)
				{
					pregunta_id = resultado.getInt("id");
					pregunta = new Pregunta(pregunta_id, resultado.getString("tema").equals("") ? null : resultado.getString("tema"), resultado.getString("pregunta"));
					this.p_falladas.add(pregunta);
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
	}

	/**
	 * Actualiza en la base de datos las preguntas falladas del usuario
	 */
	public void actualizar_falladas()
	{
		String in_sta = "";

		if (this.p_falladas.size() > 0)
		{
			in_sta = " AND pregunta NOT IN (";
			for (Pregunta p : this.p_falladas)
			{
			    in_sta += "'" + p.getId() + "', ";
			}
			in_sta = in_sta.substring(0, in_sta.length()-2) + ")";
		}

		BD.getInstance().actualizar("DELETE FROM p_falladas WHERE usuario = " + this.id + in_sta);
	}

	/**
	 * @return El nombre del usuario
	 */
	public String getNombre()
	{
		return nombre;
	}

	/**
	 * @param nombre El nuevo nombre del usuario
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 * @return El apellido del usuario
	 */
	public String getApellido()
	{
		return apellido;
	}

	/**
	 * @param apellido El nuevo apellido del usuario
	 */
	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	/**
	 * @return El DNI del usuario
	 */
	public int getDni()
	{
		return dni;
	}

	/**
	 * @param dni El nuevo DNI del usuario
	 */
	public void setDni(int dni)
	{
		this.dni = dni;
	}

	/**
	 * @return Fecha de registro del usuario
	 */
	public Date getF_registro()
	{
		return f_registro;
	}

	/**
	 * @return ¿Ha cambiado?
	 */
	public boolean isCambiado()
	{
		return cambiado;
	}

	/**
	 * @param cambiado Si el usuario ha cambiado o no
	 */
	public void setCambiado(boolean cambiado)
	{
		this.cambiado = cambiado;
	}

	/**
	 * @return Array con las preguntas falladas por el usuario
	 */
	public Pregunta[] getFalladas()
	{
		return (Pregunta[]) this.p_falladas.toArray();
	}

	/**
	 * @return Número de preguntas falladas
	 */
	public short getNumFalladas()
	{
		return (short) p_falladas.size();
	}

	/**
	 * @return ID del usuario
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param p Marca como acertada la pregunta p
	 */
	public void acertar(Pregunta p)
	{
		p_falladas.remove(p);
	}

	/**
	 * @return Si es profesor
	 */
	public boolean esProfesor()
	{
		return this.tipo == Usuario.PROFESOR;
	}

	/**
	 * @return Si es alumno
	 */
	public boolean esAlumno()
	{
		return this.tipo == ALUMNO;
	}

	/**
	 * @return Tipo de usuario
	 * Podrá ser o Usuario.PROFESOR o Usuario.ALUMNO
	 */
	public byte getTipo()
	{
		return this.tipo;
	}

	/**
	 * @param tipo Tipo de usuario
	 */
	public void setTipo(byte tipo)
	{
		if (tipo == PROFESOR || tipo == ALUMNO)
		{
			this.tipo = tipo;
		}
	}

	public String toString()
	{
		String tipo = this.tipo == PROFESOR ? "Profesor/a" : "Alumno/a";
		return tipo + " " + nombre + " " + apellido + " (" + dni + ")";
	}

	/**
	 * @return Vector con todos los usuarios de la base de datos
	 */
	public static Vector<Usuario> generarVector()
	{
		Vector<Usuario> vector = new Vector<>();
		ResultSet usuarios = BD.getInstance().consulta("SELECT * FROM usuarios");

		try
		{
			while (usuarios.next())
			{
				if (actual == null || usuarios.getInt("id") != actual.getId())
				{
					vector.add(new Usuario(usuarios.getInt("id"),
								usuarios.getInt("dni"),
								usuarios.getString("nombre"),
								usuarios.getString("apellido"),
								new Date(usuarios.getInt("f_registro")),
								(byte) usuarios.getInt("tipo")
							));
				}
				else
				{
					vector.add(actual);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}

		return vector;
	}

	/**
	 * @param args Argumentos
	 * @throws UserNotSetException Si el usuario no existe
	 */
	public static void main(String[] args) throws UserNotSetException
	{
		Usuario u = null;
		u = new Usuario(12345678);

		u.actualizar_falladas();
		System.out.println("Número de preguntas falladas: " + u.getNumFalladas());
	}
}