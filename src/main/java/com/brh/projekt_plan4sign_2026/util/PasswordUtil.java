package com.brh.projekt_plan4sign_2026.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Privater Konstruktor – diese Klasse soll nicht instanziiert werden
    // Utility-Klasse → keine Objekte erlaubt
    private PasswordUtil() {}

    // Erstellt einen sicheren Hash aus dem Klartext-Passwort
    // Hash erzeugen → Passwort wird sicher gespeichert (kein Klartext!)
    public static String hash(String plainPassword) {
        // workload 12 = Stärke des Hashing-Algorithmus (höher = sicherer, aber langsamer)
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Vergleicht ein Klartext-Passwort mit einem gespeicherten Hash
    // Passwort prüfen → Login-Verifikation
    public static boolean verify(String plainPassword, String hashedPassword) {
        // Gibt true zurück, wenn das Passwort übereinstimmt
        // vergleicht Klartext mit Hash (inkl. Salt automatisch)
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
