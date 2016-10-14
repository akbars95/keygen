package com.mtsmda.keygen.desktop.service;

import com.mtsmda.generator.GenerateRandom;
import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.UserKey;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by dminzat on 9/26/2016.
 */
@Service("generateKeyServiceImpl")
public class GenerateKeyServiceImpl implements GenerateKeyService {

    private static final Logger LOGGER = Logger.getLogger(GenerateKeyServiceImpl.class);

    private GenerateRandom generateRandom;

    @Override
    public UserKey generateKey() {
        LOGGER.info("generateKey");
        return generateProcess(null);
    }

    @Override
    public UserKey generateKey(int countDays) {
        LOGGER.info("generateKey(countDays)");
        return generateProcess(countDays);
    }

    private UserKey generateProcess(Integer count) {
        StringBuilder key = new StringBuilder();
        Integer generateNumber = null;
        LOGGER.info("input param is null: " + (ObjectHelper.objectIsNull(count)));
        if (ObjectHelper.objectIsNull(count)) {
            generateRandom = new GenerateRandom(false, false, true, false, false, false);
            do {
                String generate = generateRandom.generate(1);
                generateNumber = Integer.parseInt(generate);
            } while (generateNumber == 0);
        } else {
            generateNumber = count;
        }
        LOGGER.info("generated number of days is - " + generateNumber);
        LocalDateTime nowPlusDays = LocalDateTime.now().plusDays(generateNumber);
        LOGGER.info("key before - " + LocalDateTimeHelper.localDateTime(nowPlusDays, LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT));
        int randomNumber = generateRandom.generateNumberInRange(10, 84).intValue();
        LOGGER.info("randomNumber = " + randomNumber);
        generateRandom = new GenerateRandom(GenerateRandom.ONLY_NUMBERS);
        String convertedLDT = LocalDateTimeHelper.convertLocalDateTimeToString(nowPlusDays);
        generateRandom = new GenerateRandom(true);
        key.append(randomNumber).append(UUID.randomUUID()).append(generateRandom.generate(99 - key.length()))
                .insert(randomNumber, convertedLDT);

        System.out.println(key.length());
        key = new StringBuilder(key.substring(0, 99));
        LOGGER.info("key is - " + key.toString());
        return new UserKey(null, key.toString(), nowPlusDays);
    }

    /*public static void main(String[] args) {
        StringBuilder key = new StringBuilder();
        GenerateKeyServiceImpl generateKeyService = new GenerateKeyServiceImpl();
        Integer generateNumber = null;
        generateKeyService.generateRandom = new GenerateRandom(false, false, true, false, false, false);
            do {
                String generate = generateKeyService.generateRandom.generate(1);
                generateNumber = Integer.parseInt(generate);
            } while (generateNumber == 0);


        int randomNumber = generateKeyService.generateRandom.generateNumberInRange(10, 84).intValue();
        LocalDateTime nowPlusDays = LocalDateTime.now().plusDays(generateNumber);
        String convertedLDT = LocalDateTimeHelper.convertLocalDateTimeToString(nowPlusDays);
        generateKeyService.generateRandom = new GenerateRandom(true);
        key.append(randomNumber).append(UUID.randomUUID()).append(generateKeyService.generateRandom.generate(99 - key.length()))
                .insert(randomNumber, convertedLDT);

        System.out.println(key.length());
        key = new StringBuilder(key.substring(0, 99));
        System.out.println(key.length());
        System.out.println(key.toString().substring(randomNumber, randomNumber + 15).equals(convertedLDT));
    }*/

}