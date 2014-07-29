package sfingsolutions.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Classe utilitária contendo funções relacionadas a objetos.
 * 
 * @author Eduardo Galego.
 * 
 */
public final class DatetimeUtils {

    private static final String FORMATO_COMPLETO = "dd/MM/yyyy HH:mm:ss";
    private static final String FORMATO_DATA = "dd/MM/yyyy";
    private static final String FORMATO_DATA_REDUZIDO = "d/M/yy";
    private static final String FORMATO_HORA_MINUTO = "HH:mm";
    private static final String FORMATO_DIA_SEMANA = "EEEE";
    private static final String FORMATO_DIA_SEMANA_REDUZIDO = "EEE";
    private static final String FORMATO_DATA_EXTENSO = "EEEE, d 'de' MMMM 'de' yyyy";
    private static final String FORMATO_DATA_INVERTIDO = "yyyy-MM-dd";
    private static final String FORMATO_DATAHORA_INVERTIDO = "yyyy-MM-dd HH:mm";

    private static final int ULTIMA_HORA = 23;
    private static final int ULTIMO_MINUTO = 59;
    private static final int ULTIMO_SEGUNDO = 59;
    private static final int ULTIMO_MILISEGUNDO = 999;

    private static final int D_PRIMEIRA_HORA = 0;

    private DatetimeUtils() {
        super();
    }

    /**
     * Obtém o horário de uma data no formato HH:mm.
     * 
     * @param data Data
     * @return Horário no formato HH:mm.
     */
    public static String getHorarioFormatado(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_HORA_MINUTO);
    }

    /**
     * Obtém a data no formato dd/MM/yyyy.
     * 
     * @param data Data
     * @return Data no formato dd/MM/yyyy.
     */
    public static String getDataFormatada(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DATA);
    }

    /**
     * Obtém a data no formato reduzido.
     * @param data Data
     * @return Data no formato d/M/yy.
     */
    public static String getDataFormatadaReduzida(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DATA_REDUZIDO);
    }

    /**
     * Obtém a data no formato dd/MM/yyyy HH:mm:ss
     * @param data Data
     * @return Data no formato dd/MM/yyyy HH:mm:ss.
     */
    public static String getDataFormatadaCompleta(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_COMPLETO);
    }

    /**
     * Obtém a data no formato por extenso.
     * @param data Data.
     * @return Data no formato por extenso.
     */
    public static String getDataFormatadaExtenso(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DATA_EXTENSO);
    }

    /**
     * Obtém a data no formato invertido.
     * @param data Data.
     * @return Data no formato invertido.
     */
    public static String getDataFormatadaInvertida(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DATA_INVERTIDO);
    }

    /**
     * Obtém a data e hora no formato invertido.
     * @param data Data.
     * @return Data e hora no formato invertido.
     */
    public static String getDataHoraFormatadaInvertida(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DATAHORA_INVERTIDO);
    }

    /**
     * Obtém o dia da semana.
     * @param data Data
     * @return Dia da semana.
     */
    public static String getDiaDaSemanaTexto(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DIA_SEMANA);
    }

    /**
     * Obtém o dia da semana (sem a palavra '-feira').
     * @param data Data
     * @return Dia da semana.
     */
    public static String getDiaDaSemanaTextoCustomizado(Date data) {
        String retorno = getDataFormatadaPadrao(data, FORMATO_DIA_SEMANA);
        int pos = retorno.toLowerCase().indexOf("-feira");
        if (pos > 0) {
            return retorno.substring(0, pos);
        }
        return retorno;
    }

    /**
     * Obtém o dia da semana (formato reduzido).
     * @param data Data
     * @return Dia da semana.
     */
    public static String getDiaDaSemanaReduzido(Date data) {
        return getDataFormatadaPadrao(data, FORMATO_DIA_SEMANA_REDUZIDO);
    }

    /**
     * Obtém o horário de uma data no formato HH:mm.
     * @param data Data.
     * @param formato Formato.
     * @return Horário no formato HH:mm.
     */
    public static String getDataFormatadaPadrao(Date data, String formato) {
        String retorno = "";
        if (ObjectUtils.isReferencia(data)) {
            retorno = (new SimpleDateFormat(formato)).format(data);
        }
        return retorno;
    }

    /**
     * Retira as horas de uma data.
     * @param data Data .
     * @return data Data sem as horas.
     */
    public static Date somenteData(Date data) {
        Date retorno = null;
        if (ObjectUtils.isReferencia(data)) {
            Calendar calendar = novoCalendar(new Date());
            calendar.set(Calendar.YEAR, getAno(data));
            calendar.set(Calendar.MONTH, getMes(data));
            calendar.set(Calendar.DAY_OF_MONTH, getDia(data));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            retorno = calendar.getTime();
        }
        return retorno;
    }

    /**
     * Retorna somente as horas de uma data.
     * @param data Data .
     * @return data Somente as horas de uma data.
     */
    public static Date somenteHora(Date data) {
        Date retorno = null;
        if (ObjectUtils.isReferencia(data)) {
            Calendar calendar = novoCalendar(getDataNula());
            calendar.set(Calendar.HOUR_OF_DAY, getHoras(data));
            calendar.set(Calendar.MINUTE, getMinutos(data));
            calendar.set(Calendar.SECOND, getSegundos(data));
            calendar.set(Calendar.MILLISECOND, getMilisegundos(data));
            retorno = calendar.getTime();
        }
        return retorno;
    }

    /**
     * Retira as horas de uma data.
     * @param data Data.
     * @param horario Horário.
     * @return data Data sem as horas.
     */
    public static Date juntarDataHorario(Date data, Date horario) {
        Date retorno = data;
        if (ObjectUtils.isReferencia(data, horario)) {
            Calendar calendar = novoCalendar(data);
            calendar.set(Calendar.HOUR_OF_DAY, getHoras(horario));
            calendar.set(Calendar.MINUTE, getMinutos(horario));
            calendar.set(Calendar.SECOND, getSegundos(horario));
            calendar.set(Calendar.MILLISECOND, getMilisegundos(horario));
            retorno = calendar.getTime();
        }
        return retorno;
    }

    /**
     * Retorna um novo objeto tipo Calendar.
     * @param date
     * @return
     */
    private static Calendar novoCalendar() {
        Calendar calendar = new GregorianCalendar(new Locale("pt", "BR"));
        return calendar;
    }

    /**
     * Retorna um novo objeto tipo Calendar (especificar uma data).
     * @param date
     * @return
     */
    private static Calendar novoCalendar(Date date) {
        Calendar calendar = novoCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Retorna o valor especificado no field (Calendar.YEAR, Calendar.DAY etc.)
     * de uma data.
     * @param data Data.
     * @param campo Campo.
     * @return Valor da data esperado..
     */
    public static int getCampoDaData(Date data, int campo) {
        Integer resultado = 0;
        if (ObjectUtils.isReferencia(data)) {
            Calendar calendar = novoCalendar(data);
            resultado = calendar.get(campo);
        }
        return resultado;
    }

    /**
     * Retorna o ano da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getAno(Date data) {
        return getCampoDaData(data, Calendar.YEAR);
    }

    /**
     * Retorna o mês da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getMes(Date data) {
        return getCampoDaData(data, Calendar.MONTH);
    }

    /**
     * Retorna o dia da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getDia(Date data) {
        return getCampoDaData(data, Calendar.DAY_OF_MONTH);
    }

    /**
     * Retorna o mês da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getHoras(Date data) {
        return getCampoDaData(data, Calendar.HOUR_OF_DAY);
    }

    /**
     * Retorna o dia da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getMinutos(Date data) {
        return getCampoDaData(data, Calendar.MINUTE);
    }

    /**
     * Retorna o dia da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getSegundos(Date data) {
        return getCampoDaData(data, Calendar.SECOND);
    }

    /**
     * Retorna o dia da data.
     * @param data Data
     * @return ano da data.
     */
    public static int getMilisegundos(Date data) {
        return getCampoDaData(data, Calendar.MILLISECOND);
    }

    /**
     * Efetua o parse de uma String para um Date.
     * @param string String que será convertida.
     * @param padrao Padrão usado na conversão.
     * @return date Data convertida.
     * @throws ParseException Exceção lançada.
     */
    public static Date parse(String string, String padrao) throws ParseException {
        Date resultado = null;
        if (!StringUtils.isVazio(string, padrao)) {
            SimpleDateFormat sdf = new SimpleDateFormat(padrao);
            sdf.setLenient(true);
            resultado = sdf.parse(string);
        }
        return resultado;
    }

    /**
     * Efetua o parse de uma String para um Date (no formato dd/MM/yyyy).
     * @param string String que será convertida.
     * @return date Data convertida.
     * @throws ParseException Exceção lançada.
     */
    public static Date parseData(String string) throws ParseException {
        return parse(string, FORMATO_DATA);
    }

    /**
     * Efetua o parse de uma String para um Date (no formato dd/MM/yyyy
     * HH:mm:ss).
     * @param string String que será convertida.
     * @return date Data convertida.
     * @throws ParseException Exceção lançada.
     */
    public static Date parseDataCompleta(String string) throws ParseException {
        return parse(string, FORMATO_COMPLETO);
    }

    /**
     * Efetua o parse de uma String para um Date (no formato yyyy-MM-dd).
     * @param string String que será convertida.
     * @return date Data convertida.
     * @throws ParseException Exceção lançada.
     */
    public static Date parseDataInvertido(String string) throws ParseException {
        return parse(string, FORMATO_DATA_INVERTIDO);
    }

    /**
     * Efetua o parse de uma String para um Date (no formato yyyy-MM-dd HH:mm).
     * @param string String que será convertida.
     * @return date Data convertida.
     * @throws ParseException Exceção lançada.
     */
    public static Date parseDataHoraInvertido(String string) throws ParseException {
        return parse(string, FORMATO_DATAHORA_INVERTIDO);
    }

    /**
     * Retorna o dia da semana. Exemplos: 1 para domingo, 2 para segunda, 7 para
     * sábado e 0 caso seja null.
     * @param data Data.
     * @return dia da semana.
     */
    public static int getDiaDaSemana(Date data) {
        int resultado = 0;
        if (ObjectUtils.isReferencia(data)) {
            Calendar calendar = novoCalendar(data);
            resultado = calendar.get(Calendar.DAY_OF_WEEK);
        }
        return resultado;
    }

    /**
     * Retorna a diferença (em dias) entre duas datas, ignorando as horas e a
     * grandeza entre as datas.
     * @param dt1 Primeira data.
     * @param dt2 Segunda data.
     * @return Diferença em dias.
     */
    public static long getDiferencaDatasEmDias(Date dt1, Date dt2) {
        long resultado = -1;
        if (ObjectUtils.isReferencia(dt1, dt2)) {
            Calendar inicio, fim;
            if (dt1.before(dt2)) {
                inicio = novoCalendar(somenteData(dt1));
                fim = novoCalendar(somenteData(dt2));
            } else {
                inicio = novoCalendar(somenteData(dt2));
                fim = novoCalendar(somenteData(dt1));
            }
            resultado = 0;
            while (inicio.before(fim)) {
                inicio.add(Calendar.DAY_OF_MONTH, 1);
                resultado++;
            }
        }
        return resultado;
    }

    /**
     * Verifica se a primeira data é maior que a segunda data.
     * @param dt1 Date.
     * @param dt2 Date.
     * @return boolean
     */
    public static boolean isFirstDataMore(Date dt1, Date dt2) {
        boolean resultado = false;
        if (ObjectUtils.isReferencia(dt1, dt2)) {
            long result = dt1.getTime() - dt2.getTime();
            return !(result <= 0);
        }
        return resultado;
    }

    /**
     * Verifica se a primeira data é maior ou igual a segunda data.
     * @param dt1 Date.
     * @param dt2 Date.
     * @return boolean
     */
    public static boolean isFirstDataMoreOrEqual(Date dt1, Date dt2) {
        boolean resultado = false;
        if (ObjectUtils.isReferencia(dt1, dt2)) {
            long result = dt1.getTime() - dt2.getTime();
            return result >= 0;
        }
        return resultado;
    }

    /**
     * Verifica se a primeira data é maior que a segunda data.
     * @param dt1 Date.
     * @param dt2 Date.
     * @return boolean
     */
    public static boolean isFirstDateLess(Date dt1, Date dt2) {
        if (ObjectUtils.isReferencia(dt1, dt2)) {
            long result = dt1.getTime() - dt2.getTime();
            return result > 0;
        }
        return false;
    }

    /**
     * Verifica se a primeira data é igual a segunda data.
     * @param dt1 Date.
     * @param dt2 Date.
     * @return boolean
     */
    public static boolean isEqualDate(Date dt1, Date dt2) {
        if (ObjectUtils.isReferencia(dt1, dt2)) {
            long result = dt1.getTime() - dt2.getTime();
            return result == 0;
        }
        return false;
    }

    /**
     * Acrescenta a quantidade de dias à data.
     * @param data Data.
     * @param dias Quantidade de dias.
     * @param campo Field do Calendar.
     * @return data com a quantidade de dias acrescida.
     */
    private static Date acrescentarPorCampo(Date data, int qtd, int campo) {
        Date retorno = data;
        if (ObjectUtils.isReferencia(data)) {
            Calendar calendar = novoCalendar(data);
            calendar.add(campo, qtd);
            retorno = calendar.getTime();
        }
        return retorno;
    }

    /**
     * Acrescenta a quantidade de dias à data.
     * @param data .
     * @param dias Quantidade de dias.
     * @return data com a quantidade de dias acrescida.
     */
    public static Date acrescentarDias(Date data, int dias) {
        return acrescentarPorCampo(data, dias, Calendar.DAY_OF_YEAR);
    }

    /**
     * Acrescenta a quantidade de dias à data de hoje.
     * @param dias Quantidade de dias.
     * @return data com a quantidade de dias acrescida.
     */
    public static Date acrescentarDias(int dias) {
        return acrescentarPorCampo(getHoje(), dias, Calendar.DAY_OF_YEAR);
    }

    /**
     * Acrescenta a quantidade de minutos à data.
     * @param data Data.
     * @param minutos Quantidade de minutos.
     * @return data com a quantidade de dias acrescida.
     */
    public static Date acrescentarMinutos(Date data, int minutos) {
        return acrescentarPorCampo(data, minutos, Calendar.MINUTE);
    }

    /**
     * Acrescenta a quantidade de minutos à data de hoje.
     * @param minutos Quantidade de minutos.
     * @return data com a quantidade de dias acrescida.
     */
    public static Date acrescentarMinutos(int minutos) {
        return acrescentarPorCampo(getAgora(), minutos, Calendar.MINUTE);
    }

    /**
     * Retorna uma data nula (sem milisegundos).
     * @return Data Nula
     */
    private static Date getDataNula() {
        Date data = new Date();
        data.setTime(0);
        return data;
    }

    /**
     * Retorna a data de hoje (sem horas).
     * @return Hoje.
     */
    public static Date getHoje() {
        return somenteData(new Date());
    }

    /**
     * Retorna o instante presente.
     * @return Data Nula
     */
    public static Date getAgora() {
        return new Date();
    }

    /**
     * Retorna a primeiro instante de uma data.
     * @param data Data.
     * @return Data.
     */
    public static Date getDataIni(Date data) {
        return somenteData(data);
    }

    /**
     * Retorna o último instante de uma data.
     * @param data Data.
     * @return Data.
     */
    public static Date getDataFin(Date data) {
        Date retorno = null;
        if (ObjectUtils.isReferencia(data)) {
            Calendar calendar = novoCalendar(new Date());
            calendar.set(Calendar.YEAR, getAno(data));
            calendar.set(Calendar.MONTH, getMes(data));
            calendar.set(Calendar.DAY_OF_MONTH, getDia(data));
            calendar.set(Calendar.HOUR_OF_DAY, ULTIMA_HORA);
            calendar.set(Calendar.MINUTE, ULTIMO_MINUTO);
            calendar.set(Calendar.SECOND, ULTIMO_SEGUNDO);
            calendar.set(Calendar.MILLISECOND, ULTIMO_MILISEGUNDO);
            retorno = calendar.getTime();
        }
        return retorno;
    }

    /**
     * Verifica se a data informada é hoje.
     * @param data Data.
     * @return Verdadeiro caso a data seja hoje.
     */
    public static boolean isHoje(Date data) {
        return (getDataIni(getAgora()).compareTo(data) <= 0) && (getDataFin(getAgora()).compareTo(data) >= 0);
    }

    /**
     * Retorna a data conforme os parametros.
     * @param year Integer.
     * @param month Integer.
     * @param dayOfMonth Integer.
     * @param hourOfDay Integer.
     * @param minute Integer.
     * @param second Integer.
     * @return Date.
     */
    public static Date getData(Integer year, Integer month, Integer dayOfMonth, Integer hourOfDay, Integer minute,
        Integer second) {
        GregorianCalendar calendarInicio = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
        return calendarInicio.getTime();
    }

    /**
     * Retorna a data conforme os parametros e com a primeira hora do dia.
     * @param year Integer.
     * @param month Integer.
     * @param dayOfMonth Integer.
     * @return Date.
     */
    public static Date getDataWithFirstHour(Integer year, Integer month, Integer dayOfMonth) {
        return getData(year, month, dayOfMonth, D_PRIMEIRA_HORA, D_PRIMEIRA_HORA, D_PRIMEIRA_HORA);
    }

    /**
     * Retorna a data conforme os parametros e com a ultima hora do dia.
     * @param year Integer.
     * @param month Integer.
     * @param dayOfMonth Integer.
     * @return Date.
     */
    public static Date getDataWithLastHour(Integer year, Integer month, Integer dayOfMonth) {
        return getData(year, month, dayOfMonth, ULTIMA_HORA, ULTIMO_MINUTO, ULTIMO_SEGUNDO);
    }

    /**
     * Acrescenta a quantidade de anos à data.
     * @param data Date.
     * @param anos Quantidade de anos.
     * @return data com a quantidade de anos acrescida.
     */
    public static Date acrescentarAnos(Date data, int anos) {
        return acrescentarPorCampo(data, anos, Calendar.YEAR);
    }

    /**
     * Acrescenta a quantidade de anos e dias à data.
     * @param data Date.
     * @param anos Quantidade de anos.
     * @param dias Quantidade de dias.
     * @return data com a quantidade de anos e dias acrescida.
     */
    public static Date acrescentarAnosEDias(Date data, int anos, int dias) {
        return acrescentarDias(acrescentarPorCampo(data, anos, Calendar.YEAR), dias);
    }
}
