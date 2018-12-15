package client;

//enkel gebruikt om data voor te stellen en naar dB te sturen
public class User {
    private String naam;
    private String wachtwoord;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public User(User user) {
        naam = user.naam;
        wachtwoord = user.wachtwoord;
    }

    public String getWachtwoord() {
        return wachtwoord;

    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
