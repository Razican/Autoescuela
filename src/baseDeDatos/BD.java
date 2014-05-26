package baseDeDatos;

import interfaz.Ventana;
import interfaz.externo.Creador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import utilities.FileUtils;
import utilities.LineIterator;

import excepciones.NoDatabaseException;

/**
 * Clase para el manejo de la base de datos
 * @author Razican (Iban Eguia)
 *
 */
public class BD {

	private static BD instancia;
	private boolean cambiada;
	private Connection conexión;
	private File base;
	private boolean error;
	private String últimoError;

	/**
	 * @throws NoDatabaseException Si no existe la base de datos
	 */
	private BD() throws NoDatabaseException
	{
		this("db.sqlite3");
	}

	/**
	 * @param archivo Base de datos a usar
	 * @throws NoDatabaseException Si no existe la base de datos
	 */
	private BD(String archivo) throws NoDatabaseException
	{
		this.base = new File(archivo);
		this.cargar();
	}

	/**
	 * Carga la base de datos
	 * @throws NoDatabaseException Si no existe la base de datos
	 */
	private void cargar() throws NoDatabaseException
	{
		if (this.base.exists())
		{
			try
			{
				/**
				 * Usaré el driver de SQLite desarrollado por Xerial
				 * en http://www.xerial.org/trac/Xerial/wiki/SQLiteJDBC
				 **/

				Class.forName("org.sqlite.JDBC");
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				Ventana.getInstance().dispose();
				JOptionPane.showMessageDialog(null, "No se ha encontrado el driver de la base de datos", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
			}

			this.error = false;
			this.últimoError = null;

			this.conectar();
		}
		else
		{
			throw new NoDatabaseException();
		}
	}

	/**
	 * Crea la conexión con la base de datos
	 */
	private void conectar()
	{
		try
		{
			this.conexión = DriverManager.getConnection("jdbc:sqlite:"+this.base.getCanonicalPath());
		}
		catch (SQLException | IOException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexión con la base de datos. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}

		this.actualizar("PRAGMA encoding = \"UTF-8\";");
		this.actualizar("PRAGMA foreign_keys = ON;");

		this.cambiada = false;

		if (num_tablas() == 0)
		{
			crear_tablas();
		}
	}

	/**
	 * @return Número de tablas en la base de datos
	 */
	private int num_tablas()
	{
		return contar("sqlite_master", "type='table'");
	}

	/**
	 * Crear las tablas de la base de datos
	 */
	private void crear_tablas()
	{
		try
		{
			this.actualizar(FileUtils.toString("creación_db.sql"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int númeroConsultas = FileUtils.getLines("datos_ejemplo.sql");

		Ventana v = new Ventana(false);
		v.setContentPane(new Creador(númeroConsultas, v));
		v.setVisible(true);
		JProgressBar pb = ((Creador) v.getContentPane()).getProgressBar();
		LineIterator li = null;

		try
		{
			li = FileUtils.getLineIterator("datos_ejemplo.sql");
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}

		while (li.hasNext())
		{
			this.actualizar(li.next());
			pb.setValue(pb.getValue()+1);
		}

		this.cambiada = false;
		(new File("creación_db.sql")).delete();
		(new File("datos_ejemplo.sql")).delete();

		v.dispose();
	}

	private Statement crear_sentencia()
	{
		Statement sentencia = null;
		try
		{
			sentencia = this.conexión.createStatement();
			sentencia.setQueryTimeout(10);
		}
		catch (SQLException e)
		{
			System.err.println("Ha ocurrido un error al crear la sentencia:");
			System.err.println(e.getMessage());
			this.error = true;
			this.últimoError = e.getMessage();
		}

		return sentencia;
	}

	/**
	 * Solo se debe usar este método para realizar consultas de tipo SELECT.
	 * Para consultas que puedan cambiar algo en la base de datos, se debe
	 * usar el método actualizar().
	 *
	 * @param consulta Sentencia SQL de consulta
	 * @return Resultado de la consulta
	 */
	public ResultSet consulta(String consulta)
	{
		Statement sentencia = this.crear_sentencia();
		ResultSet resultado = null;

		try
		{
			resultado = sentencia.executeQuery(consulta);
		}
		catch (SQLException e)
		{
			System.err.println("Ha ocurrido un error al hacer la consulta:");
			System.err.println(e.getMessage());
			System.err.println("Consulta: " + consulta);
			this.error = true;
			this.últimoError = e.getMessage();
		}

		return resultado;
	}

	/**
	 * @param consulta Actualización de la base de datos
	 * @return Resultado de la actualización
	 */
	public int actualizar(String consulta)
	{
		Statement sentencia = this.crear_sentencia();
		int resultado = 0;

		try
		{
			resultado = sentencia.executeUpdate(consulta);
		}
		catch (SQLException e)
		{
			System.err.println("Ha ocurrido un error al hacer la consulta:");
			System.err.println(e.getMessage());
			System.err.println("Consulta: " + consulta);
			this.error = true;
			this.últimoError = e.getMessage();
		}

		if ( ! this.error)
		{
			this.cambiada = true;
		}

		return resultado;
	}

	/**
	 * @param tabla Tabla que contar
	 * @param donde Cláusula WHERE
	 * @return Número contado
	 */
	public int contar(String tabla, String donde)
	{
		int número = 0;
		String where = donde == null ? "" : " WHERE " + donde;
		String consulta = "SELECT COUNT(*) as número FROM " + tabla + where + ";";

		ResultSet resultado = consulta(consulta);

		try
		{
			while (resultado.next())
			{
				número = resultado.getInt("número");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			Ventana.getInstance().dispose();
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error SQL: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE, new ImageIcon("img/error.png"));
		}

		return número;
	}

	/**
	 * @param tabla Tabla que contar
	 * @return Número contado
	 */
	public int contar(String tabla)
	{
		return contar(tabla, null);
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrar()
	{
		try
		{
			this.conexión.close();
		}
		catch (SQLException e)
		{
			System.err.println("Ha ocurrido un error al cerrar la conexión:");
			System.err.println(e.getMessage());
			this.error = true;
			this.últimoError = e.getMessage();
		}

		this.base = null;
		instancia = null;
	}

	/**
	 * @return ¿Ha sido la base de datos cambiada?
	 */
	public boolean isCambiada()
	{
		return cambiada;
	}

	/**
	 * @param cambiada Actualizar el estado de la base
	 * de datos, si ha sido cambiada o no.
	 */
	public void setCambiada(boolean cambiada)
	{
		this.cambiada = cambiada;
	}

	/**
	 * @return Último error
	 */
	public String getÚltimoError()
	{
		return últimoError;
	}

	/**
	 * @return boolean ¿Ha ocurrido algún error?
	 */
	public boolean hayError()
	{
		return error;
	}

	/**
	 * @return Instancia de la base de datos
	 */
	public static BD getInstance()
	{
		if(instancia == null)
		{
			try
			{
				instancia = new BD();
			}
			catch (NoDatabaseException e)
			{
				System.err.println(e.getMessage());
			}
		}

		return instancia;
	}

	/**
	 * @param args Argumentos
	 * @throws NoDatabaseException Si no existe la base de datos
	 * @throws SQLException Si ocurre un error SQL
	 */
	public static void main(String[] args) throws NoDatabaseException, SQLException
	{
		BD bd = new BD("test.sqlite3");

		ResultSet rs = bd.consulta("SELECT * FROM prueba");

		while (rs.next())
		{
			System.out.print("ID = " + rs.getInt("ID"));
			System.out.print(", nombre = " + rs.getString("nombre"));
			System.out.println(", apellido = " + rs.getString("apellido"));
		}

		System.out.println("Número de pruebas: " + bd.contar("prueba"));

		bd.cerrar();

		BD.getInstance().actualizar("INSERT INTO usuarios (dni, nombre, apellido, tipo) " +
				"VALUES (12345678, 'Iban', 'Eguia', 1);");
		BD.getInstance().cerrar();
	}
}