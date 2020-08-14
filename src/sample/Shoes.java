package sample;

public class Shoes {
    private String artic;
    private String name;
    private int count;
    private int prise;

    Shoes(String artic, String name, int count, int prise) {
        this.artic = artic;
        this.name = name;
        this.count = count;
        this.prise = prise;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrise() {
        return prise;
    }

    public String getArtic() {
        return artic;
    }

    public String getName() {
        return name;
    }
}

