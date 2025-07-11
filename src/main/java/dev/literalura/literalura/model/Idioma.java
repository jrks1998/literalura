package dev.literalura.literalura.model;

public enum Idioma {
	EN("inglês", "EN"),
    PT("português", "PT"),
    ES("espanhol", "ES"),
    FR("francês", "FR"),
    DE("alemão", "DE"),
    IT("italiano", "IT"),
    JA("japonês", "JA"),
    ZH("chinês", "ZH"),
    KO("coreano", "KO"),
    RU("russo", "RU"),
    AR("árabe", "AR"),
    HI("hindi", "HI"),
    TR("turco", "TR"),
    NL("holandês", "NL"),
    PL("polonês", "PL"),
    SV("sueco", "SV"),
    FI("finlandês", "FI"),
    NO("norueguês", "NO"),
    DA("dinamarquês", "DA"),
    HE("hebraico", "HE"),
    EL("grego", "EL"),
    TH("tailandês", "TH"),
    ID("indonésio", "ID"),
    CS("tcheco", "CS"),
    RO("romeno", "RO"),
    HU("húngaro", "HU"),
    VI("vietnamita", "VI");
    private String idiomaPortugues;
    private String idiomaAbreviacao;
    
    Idioma(String idiomaPortuges, String idiomaAbreviacao) {
        this.idiomaPortugues = idiomaPortuges;
        this.idiomaAbreviacao = idiomaAbreviacao;
    }
    
    public static Idioma fromString(String texto) {
    	for (Idioma idioma : Idioma.values()) {
    		if (idioma.idiomaAbreviacao.equalsIgnoreCase(texto)) {
    			return idioma;
    		}
    	}
    	throw new IllegalArgumentException("Nenhuma categoria encontrada para o texto " + texto);
    }

    public String getIdiomaPortugues() {
        return idiomaPortugues;
    }
    
    @Override
    public String toString() {
    	return idiomaPortugues;
    }
}
