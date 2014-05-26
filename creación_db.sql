PRAGMA encoding = "UTF-8";
PRAGMA foreign_keys = ON;

CREATE TABLE usuarios (
	"id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"dni" INTEGER(8) NOT NULL UNIQUE,
	"nombre" TEXT NOT NULL,
	"apellido" TEXT NOT NULL,
	"f_registro" INTEGER(10) NOT NULL DEFAULT (strftime('%s', 'now')),
	"tipo" INTEGER(1) NOT NULL DEFAULT 0
);

CREATE TABLE preguntas(
	"id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"tema" TEXT,
	"pregunta" TEXT NOT NULL,
	UNIQUE (tema, pregunta)
);

CREATE TABLE respuestas(
	"id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"respuesta" TEXT NOT NULL,
	"correcta" INTEGER(1) DEFAULT 0,
	"pregunta" INTEGER NOT NULL REFERENCES preguntas(id) ON DELETE CASCADE
);

CREATE TABLE p_falladas(
	"pregunta" INTEGER NOT NULL REFERENCES preguntas(id) ON DELETE CASCADE,
	"usuario" INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
	PRIMARY KEY (pregunta, usuario) ON CONFLICT IGNORE
 );

CREATE TABLE estadísticas(
	"usuario" INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
	"totales" INTEGER DEFAULT 0,
	"acertadas" INTEGER DEFAULT 0,
	"falladas" INTEGER DEFAULT 0,
	"fecha" INTEGER(1) NOT NULL DEFAULT (strftime('%s', 'now')),
	PRIMARY KEY (usuario, fecha),
	CHECK (falladas+acertadas <= totales)
 );
