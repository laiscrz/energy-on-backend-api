package com.taligado.energy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatData {

    // Método para formatar a data fornecida no formato 'yyyy-MM-dd' para 'dd/MM/yy' (esperado pelo Oracle)
    public static String formatarData(String data) {
        String dataFormatada = null;
        try {
            if (data != null && !data.isEmpty()) {
                // Define o formato esperado da data (yyyy-MM-dd)
                SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd");
                // Impede formatação flexível
                sdfEntrada.setLenient(false);
                // Converte a string para java.util.Date
                Date utilDate = sdfEntrada.parse(data);

                // Agora converte para o formato desejado dd/MM/yy
                SimpleDateFormat sdfSaida = new SimpleDateFormat("dd/MM/yy");
                dataFormatada = sdfSaida.format(utilDate);  // Formata para o formato desejado
            } else {
                // Se não for fornecida, usa a data atual sem horas
                SimpleDateFormat sdfSaida = new SimpleDateFormat("dd/MM/yy");
                dataFormatada = sdfSaida.format(new Date());  // Data atual formatada
            }
        } catch (ParseException e) {
            // Se a data fornecida for inválida, exibe erro e usa a data atual formatada
            System.out.println("Erro ao formatar a data: " + data + ". Usando a data atual.");
            SimpleDateFormat sdfSaida = new SimpleDateFormat("dd/MM/yy");
            dataFormatada = sdfSaida.format(new Date());  // Data atual formatada
        }
        return dataFormatada;
    }
}
