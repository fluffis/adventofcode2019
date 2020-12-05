package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-04.
 */
public class Day04 extends AocDay {

    public Day04() {

    }

    @Override
    public String a(Scanner in) {

        ArrayList<Passport> passports = new ArrayList<>();

        Passport passport = null;
        while(in.hasNext()) {
            if(passport == null)
                passport = new Passport();

            String data = in.nextLine();
            if(data.equals("")) {
                passports.add(passport);
                passport = new Passport();
            }

            String[] kvs = data.split(" ");
            for(String kv : kvs) {
                String[] a = kv.split(":");
                switch(a[0]) {
                    case "byr":
                        passport.setByr(Integer.parseInt(a[1]));
                        break;
                    case "iyr":
                        passport.setIyr(Integer.parseInt(a[1]));
                        break;
                    case "eyr":
                        passport.setEyr(Integer.parseInt(a[1]));
                        break;
                    case "hgt":
                        passport.setHgt(a[1]);
                        break;
                    case "hcl":
                        passport.setHcl(a[1]);
                        break;
                    case "ecl":
                        passport.setEcl(a[1]);
                        break;
                    case "pid":
                        passport.setPid(a[1]);
                        break;
                    case "cid":
                        passport.setCid(a[1]);
                        break;
                }
            }
        }

        int c = 0;
        for(Passport p : passports) {
            if(p.isValidHacked())
                c++;
        }

        return String.valueOf(c);
    }

    @Override
    public String b(Scanner in) {
        ArrayList<Passport> passports = new ArrayList<>();

        Passport passport = null;
        while(in.hasNext()) {
            if(passport == null)
                passport = new Passport();

            String data = in.nextLine();
            if(data.equals("")) {
                passports.add(passport);
                passport = new Passport();
            }

            String[] kvs = data.split(" ");
            for(String kv : kvs) {
                String[] a = kv.split(":");
                switch(a[0]) {
                    case "byr":
                        passport.setByr(Integer.parseInt(a[1]));
                        break;
                    case "iyr":
                        passport.setIyr(Integer.parseInt(a[1]));
                        break;
                    case "eyr":
                        passport.setEyr(Integer.parseInt(a[1]));
                        break;
                    case "hgt":
                        passport.setHgt(a[1]);
                        break;
                    case "hcl":
                        passport.setHcl(a[1]);
                        break;
                    case "ecl":
                        passport.setEcl(a[1]);
                        break;
                    case "pid":
                        passport.setPid(a[1]);
                        break;
                    case "cid":
                        passport.setCid(a[1]);
                        break;
                }
            }
        }

        passports.add(passport);

        int c = 0;
        for(Passport p : passports) {
            if(p.isValidValidated())
                c++;
        }

        return String.valueOf(c);
    }
}

class Passport {
    private int byr;
    private int iyr;
    private int eyr;
    private String hgt;
    private String hcl;
    private String ecl;
    private String pid;
    private String cid;

    public int getByr() {
        return byr;
    }

    public void setByr(int byr) {
        this.byr = byr;
    }

    public int getIyr() {
        return iyr;
    }

    public void setIyr(int iyr) {
        this.iyr = iyr;
    }

    public int getEyr() {
        return eyr;
    }

    public void setEyr(int eyr) {
        this.eyr = eyr;
    }

    public String getHgt() {
        return hgt;
    }

    public void setHgt(String hgt) {
        this.hgt = hgt;
    }

    public String getEcl() {
        return ecl;
    }

    public void setEcl(String ecl) {
        this.ecl = ecl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getHcl() {
        return hcl;
    }

    public void setHcl(String hcl) {
        this.hcl = hcl;
    }

    public boolean isValidHacked() {
        return
                this.getByr() != 0 &&
                this.getIyr() != 0 &&
                this.getEyr() != 0 &&
                this.getHgt() != null &&
                this.getEcl() != null &&
                this.getHcl() != null &&
                this.getPid() != null;

    }

    private boolean isValidHgt() {

        String hgt = this.getHgt();
        if(hgt == null) {
            System.out.println("HGT not valid");
            return false;
        }
        if(hgt.endsWith("cm")) {
            int h = Integer.parseInt(hgt.replace("cm", ""));
            if(h >= 150 && h <= 193) {
                return true;
            }
            else {
                System.out.println("HGT not valid");
                return false;
            }
        }
        else if(hgt.endsWith("in")) {
            int h = Integer.parseInt(hgt.replace("in", ""));
            if(h >= 59 && h <= 76) {
                return true;
            }
            else {
                System.out.println("HGT not valid");
                return false;
            }
        }
        else {
            System.out.println("HGT not valid");
            return false;
        }
    }

    private boolean isValidHcl() {

        if(this.getHcl() == null) {
            System.out.println("HCL not valid");
            return false;
        }
        if(this.getHcl().matches("^#[0-9a-f]{6}$")) {
            return true;
        }
        else {
            System.out.println("HCL not valid");
            return false;
        }
    }

    private boolean isValdiEcl() {
        String[] validColors = new String[] {
                "amb",
                "blu",
                "brn",
                "gry",
                "grn",
                "hzl",
                "oth"
        };

        if(Arrays.stream(validColors).anyMatch(p -> p.equals(this.getEcl()))) {
            return true;
        }
        else {
            System.out.println("ECL not valid");
            return false;
        }
    }

    private boolean isValidPid() {
        if(this.getPid() == null) {
            System.out.println("PID not valid");
            return false;
        }

        if(this.getPid().matches("^[0-9]{9}$")) {
            return true;
        }
        else {
            System.out.println("PID NOT valid");
            return false;
        }
    }

    public boolean isValidValidated() {

        if(this.getByr() < 1920 || this.getByr() > 2002) {
            System.out.println("BYR NOT valid");
            return false;
        }

        if(this.getIyr() < 2010 || this.getIyr() > 2020) {
            System.out.println("IYR NOT valid");
            return false;
        }

        if(this.getEyr() < 2020 || this.getEyr() > 2030) {
            System.out.println("EYR NOT valid");
            return false;
        }

        return this.isValidHgt() &&
                this.isValidHcl() &&
                this.isValdiEcl() &&
                this.isValidPid();
    }
}