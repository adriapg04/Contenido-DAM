use portfolio;
CREATE TABLE proyecto (
id_proyecto BIGINT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255),
descripcion TEXT,
imagen TEXT,
url TEXT
);
CREATE TABLE lenguaje (
id_lenguaje BIGINT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255),
imagen TEXT
);
CREATE TABLE proyecto_proyecto_lenguaje (
id_proyecto BIGINT,
id_lenguaje BIGINT,
PRIMARY KEY (id_proyecto, id_lenguaje),
FOREIGN KEY (id_proyecto) REFERENCES proyecto(id_proyecto),
FOREIGN KEY (id_lenguaje) REFERENCES lenguaje(id_lenguaje)
);



insert into proyecto(descripcion,imagen,nombre,url) values ( "Ejercicio de Trivial","https://m.media-amazon.com/images/I/81JZERKYs3L.jpg","Trivial","https://gitlab.com/adrian5834706/tareatrivial1.git");
insert into proyecto(descripcion,imagen,nombre,url) values ( "Ejercicio Unity","https://upload.wikimedia.org/wikipedia/commons/c/c4/Unity_2021.svg","JuegoUnity","https://gitlab.com/adrian5834706/api-spring-movies.git");
insert into proyecto(descripcion,imagen,nombre,url) values ( "Ejercicio Hilos y Procesos","https://i.ytimg.com/vi/n6IxKTS2zYs/maxresdefault.jpg","Hilos y procesos","https://gitlab.com/adrian5834706/ejercicio-hilos-y-procesos.git");
insert into proyecto(descripcion,imagen,nombre,url) values ( "Ejercicio Huerto Android","https://www.agrohuerto.com/wp-content/uploads/2015/08/huerto-en-bancales.jpg","Huerto de Android","https://gitlab.com/adrian5834706/ejercicio-botones.git");
insert into proyecto(descripcion,imagen,nombre,url) values ( "Ejercicio Intents, formularios y sharedPrefs","https://www.aulapc.es/paginas/ofimatica/paginas/word/formularios/imagenes/formusin.png","Intents formularios y sharedprefs","");


select * from proyecto;


use lenguaje;

insert into lenguaje (imagen,nombre) values ("https://www.muylinux.com/wp-content/uploads/2020/03/java.png","JAVA");
insert into lenguaje (imagen,nombre) values ("https://developer.android.com/static/codelabs/basic-android-kotlin-compose-first-program/img/840cee8b164c10b.png?hl=es-419","KOTLIN");

use proyecto_proyecto_lenguaje;
insert into proyecto_proyecto_lenguaje values(1,2);
insert into proyecto_proyecto_lenguaje values(2,1);
insert into proyecto_proyecto_lenguaje values(3,1);
insert into proyecto_proyecto_lenguaje values(4,2);
insert into proyecto_proyecto_lenguaje values(5,1);

