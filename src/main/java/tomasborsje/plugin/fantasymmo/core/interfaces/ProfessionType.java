package tomasborsje.plugin.fantasymmo.core.interfaces;

public enum ProfessionType {
    HERBALISM("Herbalism"),
    MINING("Mining"),
    ALCHEMY("Alchemy"),
    BLACKSMITHING("Blacksmithing"),
    TAILORING("Tailoring"),
    COOKING("Cooking");

    private String titleCase;

    ProfessionType(String titleCase) {

        this.titleCase = titleCase;
    }

    public String getTitleCase() {
        return titleCase;
    }
}
