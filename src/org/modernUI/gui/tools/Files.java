package org.modernUI.gui.tools;

import org.yaml.snakeyaml.Yaml;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.Optional;

public class Files {
    /**
     * Lee y moldea una imagen
     *
     * @param image imagen con especificaciones
     * @return imagen moldeada
     */
    public static ImageIcon image(BufferedImage image) {
        return new ImageIcon(image);
    }

    /**
     * Lee y moldea una imagen
     *
     * @param path      ruta de la imagen en disco
     * @param dimension dimension a moldear la imagen
     * @return imagen moldeada
     */
    public static ImageIcon image(String path, Dimension dimension) {
        try {
            return image((BufferedImage) ImageIO.read(new File(path))
                    .getScaledInstance(
                            dimension.width,
                            dimension.height,
                            Image.SCALE_DEFAULT
                    ));
        } catch (IOException e) {
            throw new IllegalArgumentException(path + " NOT found");
        }
    }

    /**
     * Lee y moldea una imagen
     *
     * @param path      ruta de la imagen en disco
     * @param dimension dimension a moldear la imagen
     * @return imagen moldeada
     */
    public static ImageIcon image(URL path, Dimension dimension) {
        try {
            return image(path.toURI().getPath(), dimension);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(path + " NOT found");
        }
    }

    /**
     * Lee y moldea una imagen
     *
     * @param path      ruta de la imagen en disco
     * @param dimension dimension en ancho y alto para moldear la imagen
     * @return imagen moldeada
     */
    public static ImageIcon image(URL path, int dimension) {
        try {
            return image(path.toURI().getPath(), dimension);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(path + " NOT found");
        }
    }

    /**
     * Lee y moldea una imagen
     *
     * @param path      ruta de la imagen en disco
     * @param dimension dimension en ancho y alto para moldear la imagen
     * @return imagen moldeada
     */
    public static ImageIcon image(String path, int dimension) {
        return image(path, new Dimension(dimension, dimension));
    }

    /**
     * Lee y moldea una imagen
     *
     * @param path ruta de la imagen en disco
     * @return imagen moldeada
     */
    public static ImageIcon image(URL path) {
        try {
            return image(path.toURI().getPath());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(path + " NOT found");
        }
    }

    /**
     * Lee una imagen
     *
     * @param path ruta de la imagen
     * @return imagen con su tamaño original
     */
    public static ImageIcon image(String path) {
        return image(path, -1);
    }

    /**
     * Lee un archivo de texto plano y los convierte en un {@link String}
     *
     * @param path ruta del archivo de texto plano
     * @return contenido del archivo de texto plano
     */
    public static String textPlain(URL path) {
        try {
            return textPlain(path.toURI().getPath());
        } catch (URISyntaxException e) {
            return "";
        }
    }

    /**
     * Lee un archivo de texto plano y los convierte en un {@link String}
     *
     * @param path ruta del archivo de texto plano
     * @return contenido del archivo de texto plano
     */
    public static String textPlain(String path) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str;
            while (Optional.ofNullable((str = br.readLine())).isPresent()) text.append(str).append('\n');
            br.close();
        } catch (IOException e) {  // None
        }
        return text.toString();
    }

    /**
     * Exporta una imagen en una ruta especifica
     *
     * @param file  ruta de exportación
     * @param image imagen a exportar
     * @return confirmación de exportación
     */
    public static boolean exportImage(File file, RenderedImage image) {
        try {
            return ImageIO.write(image, getExtension(file.getName()), file);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Obtiene la extensión del archivo especificado
     *
     * @param filename archivo a consultar extensión
     * @return extensión del archivo especificado
     */
    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    /**
     * Escribe en un archivo especificado el texto definido en el parámetro
     *
     * @param file archivo a escribir
     * @param text contenido a escribir en el archivo
     * @return confirmación de escritura
     */
    public static boolean writeTextPlain(File file, String text) {
        if (file.exists()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(text);
                bw.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Crea una carpeta en un directorio especificado
     *
     * @param directory directorio donde será creada la nueva carpeta
     * @param folder    carpeta a crear
     * @return confirmación de creación
     */
    public static boolean createFolder(File directory, String folder) {
        return new File(directory.getAbsolutePath(), folder).mkdir();
    }

    /**
     * Copia el contenido de un archivo en otro ya existente
     *
     * @param fileOrigin archivo de origen
     * @param fileCopy   archivo de destino donde será copiado el contenido
     * @return confirmación de copia
     */
    public static boolean copyFile(File fileOrigin, File fileCopy) {
        if (Optional.ofNullable(fileCopy).isEmpty() || Optional.ofNullable(fileOrigin).isEmpty())
            throw new NullPointerException("Files not exist");
        if (fileOrigin.exists() &&
                fileOrigin.isFile() &&
                fileCopy.exists() &&
                fileCopy.isFile() &&
                !fileOrigin.equals(fileCopy)) {
            try {
                InputStream inputStream = new FileInputStream(fileOrigin);
                OutputStream outputStream = new FileOutputStream(fileCopy);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) outputStream.write(buffer, 0, length);
                inputStream.close();
                outputStream.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Lee un archivo YAML
     *
     * @param path ruta especifica del archivo YAML
     * @return contenido del archivo YAML
     */
    public static Object readYAML(String path) {
        return new Yaml().load(path);
    }

    /**
     * Lee un archivo YAML
     *
     * @param path ruta especifica del archivo YAML
     * @return contenido del archivo YAML
     */
    public static Object readYAML(InputStream path) {
        return new Yaml().load(path);
    }
}
