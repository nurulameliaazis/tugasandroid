package pnj.ac.id.tmjreg.model;

public class UserModel {
    int id;
    String nama;
    String email;
    String bod;
    String jamLahir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getJamLahir() {
        return jamLahir;
    }

    public void setJamLahir(String jamLahir) {
        this.jamLahir = jamLahir;
    }
}
