package com.example.evahan;

public class model {

    public String getVehilceno() {
        return vehilceno;
    }

    public void setVehilceno(String vehilceno) {
        this.vehilceno = vehilceno;
    }

    public String getChasisno() {
        return chasisno;
    }

    public void setChasisno(String chasisno) {
        this.chasisno = chasisno;
    }

    public String getEngineno() {
        return engineno;
    }

    public void setEngineno(String engineno) {
        this.engineno = engineno;
    }

    String vehilceno;
    String chasisno;
    String engineno;

    public model(String vehilceno, String chasisno, String engineno) {
        this.vehilceno = vehilceno;
        this.chasisno = chasisno;
        this.engineno = engineno;
    }


}
