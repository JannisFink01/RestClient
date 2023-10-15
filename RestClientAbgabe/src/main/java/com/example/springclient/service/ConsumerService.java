package com.example.springclient.service;

import com.example.springclient.entity.Adresse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class ConsumerService {

    @Setter
    private Adresse empfangeneAdresse;

    @KafkaListener(topics = "solarResult", groupId = "group")
    public void nachrichtEmpfangen(String nachricht){
        log.debug("Empfangen: {}", nachricht);
        empfangeneAdresse = Adresse.toAdresse(nachricht);
        log.debug("adresse: {}", empfangeneAdresse);
        write("\n " + "\n" + "Abfrageergebnis:" + "\n" + "Adresse: " + empfangeneAdresse.getStrasse()
                + " " + empfangeneAdresse.getHausnummer() + ", " + empfangeneAdresse.getPlz()
                + " " + empfangeneAdresse.getStadt() + "\n" + "Leistung Solaranlage am nächsten Tag: "
                + empfangeneAdresse.getWattNextDay() + " Wh");
    }

    public static void write(String ergebnis){
        String datei = "C:\\Users\\janni\\avgsolarapi\\LoggingErgebnis\\XY";
        try{
            FileWriter fileWriter = new FileWriter(datei, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(ergebnis);
            printWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
