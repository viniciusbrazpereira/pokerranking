package sfingsolutions.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Utilitário para conversões. PoupaFila Ver. 2.0
 * 
 * 
 */
public class Utils {

    /**
     * Fornece no formato hh:mm:ss a diferença de tempo entre a data informada e
     * a data atual sendo a data informada ANTERIOR à data atual.
     * 
     * @param data
     * @return
     */
    public static String formataDiferencaDataAtual(Date data) {
        Date atual = new Date();
        long dif = atual.getTime() - data.getTime();
        long horas = dif / (60 * 60000);
        long minutos = (dif % (60 * 60000)) / 60000;
        long segundos = ((dif % (60 * 60000)) % 60000) / 1000;
        StringBuffer resultado = new StringBuffer();
        if (horas < 10)
            resultado.append("0" + horas + ":");
        else
            resultado.append(horas + ":");

        if (minutos < 10)
            resultado.append("0" + minutos + ":");
        else
            resultado.append(minutos + ":");

        if (segundos < 10)
            resultado.append("0" + segundos);
        else
            resultado.append(segundos);

        return resultado.toString();
    }

    public static boolean isLetter(String value) {
        if ((value == null) || (value.trim().equals("")))
            return false;
        if (value.length() > 1)
            return false;
        if (!Character.isLetter(value.charAt(0)))
            return false;
        return true;
    }

    public static boolean isDigit(String value) {
        if ((value == null) || (value.trim().equals("")))
            return false;
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i)))
                return false;
        }

        return true;
    }

    public static String preencheVazioCupom(String linha) {
        if (linha == null)
            return "  ";
        int espacos = 38 - linha.length();
        espacos = espacos / 2;
        StringBuffer sb = new StringBuffer(" ");
        for (int i = 0; i < espacos; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static boolean isNull(String value) {
        if (value == null)
            return true;
        if (value.trim().equals(""))
            return true;
        if (value.trim().length() == 0)
            return true;
        return false;
    }

    public static String completaZerosEsquerda(String valor, int tamanho) {

        if (tamanho == valor.length()) {
            return valor;
        }
        int diff = tamanho - valor.length();
        for (int i = 0; i < diff; i++) {
            valor = "0" + valor;
        }
        return valor;
    }

    public static String getCaracteresDireita(String string, int tamanho) {
        if (string.length() < tamanho) {
            return preencheCaracterDireita(string, " ", tamanho);
        } else {
            return string.substring(0, tamanho);
        }
    }

    public static String centralizar(String string, int tamanho) {
        StringBuffer sb = new StringBuffer();
        int qtde = (tamanho - string.length()) / 2;
        sb.append(repeteCaracter(" ", qtde) + string + repeteCaracter(" ", qtde));
        return sb.toString();
    }

    public static String repeteCaracter(String string, int qtde) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < qtde; i++) {
            sb.append(string);
        }
        return sb.toString();
    }

    public static String preencheCaracterDireita(String texto, String caracter, int tamanhofinal) {
        if (caracter.length() < tamanhofinal) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < (tamanhofinal - texto.length()); i++) {
                sb.append(caracter);
            }
            return texto + sb.toString();
        }
        return texto;
    }

    /**
     * Verifica se o IP informado é alcancável na rede
     * 
     * @param ip
     * @return
     */
    public static boolean ipReachable(String ip) {
        boolean result = false;
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            result = inetAddress.isReachable(5000);
        } catch (UnknownHostException e) {
            System.err.println("IP [" + ip + "] nao encontrado na rede");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Converte um array de bytes para uma String
     * 
     * @param bs
     * @return
     */
    public static String byteToString(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bs.length; i++) {
            sb.append((char) bs[i]);
        }
        return sb.toString();
    }

    /**
     * Retorna o MAC Address da máquina no formato ex. 08-00-27-DC-4A-9E.
     * 
     * @return
     */
    public static String getMacAddress() {
        String macAddress = null;
        try {
            InetAddress address = InetAddress.getLocalHost();

            NetworkInterface ni = NetworkInterface.getByInetAddress(address);

            byte[] mac = ni.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            macAddress = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    /**
     * Retorna o MAC Address da máquina no formato ex. 08-00-27-DC-4A-9E de
     * acordo com o Host informado
     * @param host .
     * @return .
     */
    public static String getMacAddressByHost(String host) {
        String macAddress = null;
        try {
            InetAddress address = InetAddress.getByName(host);

            NetworkInterface ni = NetworkInterface.getByInetAddress(address);

            byte[] mac = ni.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            macAddress = sb.toString();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return macAddress;
    }
}
