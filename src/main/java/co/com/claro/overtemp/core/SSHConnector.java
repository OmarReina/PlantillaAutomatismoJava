/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.overtemp.core;

/**
 *
 * @author User
 */
import co.com.claro.overtemp.constans.Constans;
import co.com.claro.overtemp.constans.SSHConstantes;
import com.claro.logger.ClaroLogger;
import com.claro.remote.ClientException;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;

/**
 * Clase encargada de establecer conexión y ejecutar comandos SSH.
 */
public class SSHConnector extends SSHConstantes {

    /**
     * Sesión SSH establecida.
     */
    private Session session;
    protected InputStream in;
    private PrintStream out;
    private String msgError;

    /**
     * Se abre un canal SSH. Es como abrir una consola
     */
    private ChannelShell channel;

    /**
     * Establece una conexión SSH.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @param host Host a conectar.
     * @param port Puerto del Host.
     *
     * @throws JSchException Cualquier error al establecer conexión SSH.
     * @throws IllegalAccessException Indica que ya existe una conexión SSH
     * establecida.
     */
    public void connect(String username, String password, String host, int port)
            throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();

            this.session = jsch.getSession(username, host, port);
            this.session.setPassword(password);

            // Parametro para no validar key de conexion.
            this.session.setConfig("StrictHostKeyChecking", "no");

            this.session.connect();
        } else {
            ClaroLogger.errorProgrammerLog("Sesion SSH ya iniciada.");
        }
    }

    /**
     * Se conecta a un elemento e interactura con el para extraer datos de
     * capacidad
     *
     * @param host Ip Elemento
     * @param user Usuario SSH
     * @param passw Pass SSH
     *
     * @return
     *
     * @throws IllegalAccessException Excepción lanzada cuando no hay conexión
     * establecida.
     * @throws JSchException Excepción lanzada por algún error en la ejecución
     * del comando SSH.
     * @throws IOException Excepción al leer el texto arrojado luego de la
     * ejecución del comando SSH.
     * @throws com.claro.remote.ClientException
     */
    public Boolean conectTo(String host, String user, String passw)
            throws IllegalAccessException, JSchException, IOException, ClientException {
        boolean success = Boolean.FALSE;
        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SSH. Es como abrir una consola.
            channel = (ChannelShell) session.openChannel("shell");
            channel.connect();

            // Get input and output stream references
            in = channel.getInputStream();
            out = new PrintStream(channel.getOutputStream());

            readUntil(END_PROMPT, false);
            /**
             * Se conecta al elemento por SSH
             */
            String comandConect = "ssh " + user + "@" + host;
            System.out.println("Ejecutando comando:" + comandConect);
            write(comandConect);

            /**
             * Se valida la salida de la conexion para introducir la clave
             */
            String rtaConect = readUntil(PASS_WAIT, true);
            //System.out.println(rtaConect);
            /**
             * Se valida la autenticacion
             */
            if (rtaConect.contains("Host key verification failed.")) {
                ClaroLogger.errorProgrammerLog(String.format("Error en conexion SSH %s. Host key verification failed", host));
            }

            if (rtaConect.contains("Connection reset by peer")) {
                ClaroLogger.errorProgrammerLog(String.format("Error en conexion SSH %s. Connection reset by peer", host));
            }

            //System.out.println(rtaConect);
            /**
             * Se escribe la clave de session
             */
            write(passw);
            String rtaAuten = readUntil(END_PROMPT_E, false);
            /**
             * Se valida la autenticacion
             */
            if (rtaAuten.contains("Authentication failed")) {
                ClaroLogger.errorProgrammerLog(String.format("Error en autenticacion SSH. User: %s", user));
            }

            if (rtaAuten.contains("Enter password")) {
                ClaroLogger.errorProgrammerLog(String.format("No se puede iniciar sesion por SSH. User: %s", user));
            }

            success = Boolean.TRUE;
        } else {
            ClaroLogger.errorProgrammerLog("No existe sesion SSH iniciada.");
        }
        return success;
    }

    /**
     * Ejecuta un comando en el elemento
     *
     * @param comand
     * @return
     * @throws ClientException
     * @throws IOException
     */
    public String sendComand(String comand) throws ClientException, IOException {
        String response;
        /**
         * Se escribe el comando
         */
        System.out.println("Command: " + comand);
        write(comand);
        /**
         * Se captura la respuesta del comando
         */
        response = readUntil(END_PROMPT_E, false);
        /**
         * Se valida la salida del comando
         */
        if (!validateCommandResponse(response)) {
            response = "";
        }
        // Retornamos el texto impreso en la consola.
        return response;
    }

    /**
     * Cierra el canal SSH.
     */
    public final void disconnectChannel() {
        if (this.channel != null && this.channel.isConnected()) {
            this.channel.disconnect();
        }
    }

    private String readUntil(String pattern, Boolean islogin) throws ClientException {
        StringBuilder response = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        char lastChar = pattern.charAt(pattern.length() - 1);
        char continueLastChar = CONTINUE_KEY_COMAND.charAt(CONTINUE_KEY_COMAND.length() - 1);
        char authFailedLastChar = AUTH_FAILED.charAt(AUTH_FAILED.length() - 1);
        char hostVerfiryFailedLastChar = HOST_KEY_VERIFY_FAILED.charAt(HOST_KEY_VERIFY_FAILED.length() - 1);
        char connectionResetByPeer = CONNECTION_RESET_BY_PEER.charAt(CONNECTION_RESET_BY_PEER.length() - 1);

        BufferedWriter writer = null;
        try {
            int readByte = in.read();
            while (readByte != 0xffffffff) {
                builder.append((char) readByte);
                if ((char) readByte == lastChar) {
                    if (fastEndsWith(builder, pattern)) {
                        break;
                    }
                }

                String cad_1 = builder.toString();
                if ((cad_1).endsWith(PASS_WAIT)) {
                    break;
                }

                if (islogin) {
                    String cad = builder.toString();
                    if ((cad).endsWith(PASS_WAIT)) {
                        break;
                    }

                    //Si el caracter es igual al último caracter del patron
                    if ((char) readByte == authFailedLastChar) {
                        if (fastEndsWith(builder, AUTH_FAILED)) {
                            break;
                        }
                    }

                    //Si el caracter es igual al último caracter del patron
                    if ((char) readByte == hostVerfiryFailedLastChar) {
                        if (fastEndsWith(builder, HOST_KEY_VERIFY_FAILED)) {
                            break;
                        }
                    }

                    if ((char) readByte == connectionResetByPeer) {
                        if (fastEndsWith(builder, CONNECTION_RESET_BY_PEER)) {
                            break;
                        }
                    }
                }
                //Si se requiere continuar con la consulta
                if ((char) readByte == continueLastChar) {
                    String cad = builder.toString();
                    if ((cad).endsWith(CONTINUE_KEY_COMAND)) {
                        if (cad.startsWith("--[16D")) {
                            cad = cad.substring(28, cad.length() - (CONTINUE_KEY_COMAND.length() + 2));
                        }
                        response.append(cad);
                        builder.setLength(0);
                        write(WIN_LF);
                        continue;
                    }
                }
                readByte = in.read();
                //System.out.println(builder.toString());
            }
        } catch (IOException ex) {
            System.out.println("Error consultado respuesta comandos");
            throw new ClientException("Error consultado respuesta SSH ", ex);
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ex) {
                System.out.println("Error consultado respuesta SSH " + pattern);
                throw new ClientException("Error consultado respuesta SSH " + pattern, ex);
            } finally {
            }
        }

        if (response.length() > 0) {
            builder = response.append(builder);
        }

        return builder.toString();
    }

    private boolean fastEndsWith(StringBuilder builder, String pattern) {
        return (builder.length() - pattern.length() > -1
                && builder.subSequence(builder.length() - pattern.length(), builder.length()).equals(pattern));
    }

    /**
     * Envia un comando telnet.
     *
     * @param value el comando telnet que se envia.
     */
    protected void write(String value) {
        out.print(value + WIN_LF);
        out.flush();
    }

    /**
     * Cierra la sesión SSH.
     */
    public final void disconnectSession() {
        this.session.disconnect();
    }

    /**
     * Metodo que valida si la ejecucion del comando trae un mensaje de error
     *
     * @param respuesta
     * @return
     * @throws IOException
     */
    private boolean validateCommandResponse(String respuesta) throws IOException {
        boolean validarRespuesta = Boolean.TRUE;
        if (respuesta.contains(SEMANTIC_ERROR)) {
            setMsgError("No se ejecuta Comando, se presenta Error: " + SEMANTIC_ERROR);
            validarRespuesta = Boolean.FALSE;
        } else if (respuesta.contains(SINTAX_ERROR)) {
            setMsgError(getMsgError() + "\nNo se ejecuta Comando, se presenta Error: " + SINTAX_ERROR);
            validarRespuesta = Boolean.FALSE;
        } else if (respuesta.contains(DELAY_ERROR)) {
            setMsgError(getMsgError() + "\nNo se ejecuta Comando, se presenta Error: " + DELAY_ERROR);
            validarRespuesta = Boolean.FALSE;
        } else if (respuesta.contains(AUTHORIZED_COMMAND_ERROR)) {
            setMsgError(getMsgError() + "\nNo se ejecuta Comando, se presenta Error: " + AUTHORIZED_COMMAND_ERROR);
            validarRespuesta = Boolean.FALSE;
        } else if (respuesta.contains(USER_AUTHORIZED_ERROR)) {
            setMsgError(getMsgError() + "\nNo se ejecuta Comando, se presenta Error: " + USER_AUTHORIZED_ERROR);
            validarRespuesta = Boolean.FALSE;
        } else if (respuesta.contains(CHANGE_PASSWORD_ERROR)) {
            setMsgError(getMsgError() + "\nNo se ejecuta Comando, se presenta Error: " + CHANGE_PASSWORD_ERROR);
            validarRespuesta = Boolean.FALSE;
        }
        return validarRespuesta;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    /**
     * Recupera un archivo de un servidor remoto usando SFTP y lo guarda
     * localmente.
     *
     * @param path La ruta al archivo en el servidor remoto.
     * @return El objeto File que representa el archivo descargado.
     * @throws JSchException si ocurre un error durante la conexión del canal
     * SSH.
     * @throws SftpException si se produce un error durante la recuperación del
     * archivo SFTP.
     * @throws FileNotFoundException si el archivo de destino no se puede
     * encontrar o crear.
     * @throws IOException si se produce un error de E/S durante la
     * transferencia de archivos.
     */
    public final File getFile(String path) throws JSchException, SftpException, FileNotFoundException, IOException {
        Channel channel = this.session.openChannel("sftp");
        channel.connect();

        ChannelSftp sftpChannel = (ChannelSftp) channel;
        // Leer el archivo XML desde el servidor remoto
        InputStream file = sftpChannel.get(path);
        File targetFile = new File("tmp/xml.tmp");
        FileOutputStream fos = new FileOutputStream(targetFile);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = file.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        file.close();
        fos.close();
        sftpChannel.disconnect();

        return targetFile;
    }

    /**
     *
     * Establece un archivo en el servidor remoto utilizando el protocolo SFTP.
     *
     * @param path la ruta del directorio en el servidor remoto donde se
     * colocará el archivo.
     * @param fileName el nombre del archivo que se colocará en el servidor
     * remoto.
     * @throws Exception si ocurre un error durante la transferencia del
     * archivo.
     */
    public final void setFile(String path, String fileName) throws Exception {
        ChannelSftp channelSftp = (ChannelSftp) this.session.openChannel("sftp");
        try {
            // Crear la carpeta de historico si no existe
            channelSftp.connect();
            createFolderIfNotExists(channelSftp, path + Constans.HISTORICO_PATH);

            // Crear la carpeta con la fecha de hoy si no existe
            String todayFolderPath = path + Constans.HISTORICO_PATH + "/" + Constans.HISTORICO_PATH_DAY;
            createFolderIfNotExists(channelSftp, todayFolderPath);

            File file = new File(fileName);
            FileInputStream fis = new FileInputStream("tmp/" + file);
            channelSftp.put(fis, todayFolderPath + "/" + fileName);
//            // Eliminar la carpeta del día anterior
//            String previousDayFolderPath = path + Constans.HISTORICO_PATH + "/" + Constans.HISTORICO_PATH_YESTERDAY;
//            deleteNonEmptyFolderIfExists(channelSftp, previousDayFolderPath);

            // Esperar hasta que se complete la transferencia
            channelSftp.getSession().sendKeepAliveMsg();
            channelSftp.disconnect();
            ClaroLogger.infoProgrammerLog("Termina cargue de archivo de texto: " + fileName);
        } catch (Exception ex) {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
        }
    }

    /**
     *
     * Crea una carpeta en el servidor remoto si no existe.
     *
     * @param channelSftp el canal SFTP utilizado para la conexión remota.
     * @param folderPath la ruta de la carpeta que se desea crear en el servidor
     * remoto.
     * @throws SftpException si ocurre un error durante la creación de la
     * carpeta en el servidor remoto.
     */
    private void createFolderIfNotExists(ChannelSftp channelSftp, String folderPath) throws SftpException {
        try {
            channelSftp.cd(folderPath);
        } catch (SftpException e) {
            // La carpeta no existe, se crea
            channelSftp.mkdir(folderPath);
            channelSftp.cd(folderPath);
        }
    }

    /**
     *
     * Elimina una carpeta vacía en el servidor remoto si existe.
     *
     * @param channelSftp el canal SFTP utilizado para la conexión remota.
     * @param folderPath la ruta de la carpeta que se desea eliminar en el
     * servidor remoto.
     * @throws SftpException si ocurre un error durante la eliminación de la
     * carpeta en el servidor remoto.
     */
    private void deleteEmptyFolderIfExists(ChannelSftp channelSftp, String folderPath) throws SftpException {
        try {
            channelSftp.cd(folderPath);
            channelSftp.rmdir(folderPath);
        } catch (SftpException e) {
            // La carpeta no existe, no es necesario borrarla
        }
    }

    /**
     *
     * Elimina una carpeta no vacía en el servidor remoto si existe.
     *
     * @param channelSftp el canal SFTP utilizado para la conexión remota.
     * @param path la ruta de la carpeta que se desea eliminar en el servidor
     * remoto.
     * @throws SftpException si ocurre un error durante la eliminación de la
     * carpeta en el servidor remoto.
     */
    private void deleteNonEmptyFolderIfExists(ChannelSftp channelSftp, String path) throws SftpException {
        try {
            // Verificar si la carpeta existe
            SftpATTRS attrs = channelSftp.stat(path);

            if (attrs.isDir()) {
                // Obtener la lista de archivos y subdirectorios en la carpeta
                Vector<ChannelSftp.LsEntry> entries = channelSftp.ls(path);
                for (ChannelSftp.LsEntry entry : entries) {
                    String entryName = entry.getFilename();
                    if (!entryName.equals(".") && !entryName.equals("..")) {
                        // Eliminar el archivo o subdirectorio
                        channelSftp.rm(path + "/" + entryName);
                    }
                }

                // Eliminar la carpeta ahora que está vacía
                channelSftp.rmdir(path);
            }
        } catch (SftpException e) {
            // La carpeta no existe, no es necesario borrarla
        }
    }

}
