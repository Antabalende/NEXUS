package fr.nexus.erp;

public class main {

    public static void main(String[] args) {

        SystemBoot.verifierAcces(6);

        SystemBoot.afficherEtapes();

        DataEngine.runPipeline();


    }
}
