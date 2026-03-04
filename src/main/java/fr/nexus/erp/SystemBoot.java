package fr.nexus.erp;

public class SystemBoot {

    public static void verifierAcces(int niveau) {

        if (niveau < 5) {
            System.out.println("Accès refusé : niveau d'accréditation insuffisant.");
        } else {
            System.out.println("Accès autorisé. Démarrage du système.");
        }

    }

    public static void afficherEtapes() {

        String[] etapes = {
                "Initialisation",
                "Connexion DB",
                "Nettoyage",
                "Export"
        };

        for (String etape : etapes) {
            System.out.println("Étape : " + etape);
        }

    }

}