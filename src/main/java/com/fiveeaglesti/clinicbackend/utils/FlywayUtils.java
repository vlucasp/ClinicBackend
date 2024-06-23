package com.fiveeaglesti.clinicbackend.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class FlywayUtils {

    private static final String MIGRATION_DIRECTORY = "src/main/resources/db/migration/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o nome do script:");
        String scriptName = scanner.nextLine();

        if (isBlank(scriptName)
                || !scriptName.matches("[a-zA-Z0-9_]*")) {
            System.out.println("O nome do script deve conter apenas letras, números e sublinhados.");
            return;
        }

        String fileName = "V" + System.currentTimeMillis() + "__" + scriptName + ".sql";
        File file = new File(MIGRATION_DIRECTORY + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("Arquivo criado: " + file.getName());
            } else {
                System.out.println("O arquivo já existe.");
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro.");
            e.printStackTrace();
        }
    }

}
